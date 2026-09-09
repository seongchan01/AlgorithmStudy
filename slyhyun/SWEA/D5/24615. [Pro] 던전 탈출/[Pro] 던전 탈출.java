import java.util.*;

// 핵심 아이디어
// 게이트를 밟으면 체력이 가득 차므로, 사실상 주유소
// 노드 = 게이트 / 간선 = 두 게이트 사이의 격자 최단거리(체력 이내일 때만)
// addGate()는 200번, getMinTime()은 800번 호출되므로
// 무거운 격자 BFS를 addGate()에 몰아넣고
// getMinTime()은 미리 만들어 둔 거리표 위에서 다익스트라만 돌린다.
class UserSolution {
    int N, mMaxStamina;
    int[][] dungeon;
    int[] gRow;
    int[] gCol;
    int[][] gVal;
    int[][] dist;
    boolean[] alive;

    int[] dr = {-1, 0, 1, 0};
    int[] dc = {0, 1, 0, -1};
		
    void init(int N, int mMaxStamina, int mMap[][]) {
        this.N = N;
        this.mMaxStamina = mMaxStamina;

        dungeon = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dungeon[i][j] = mMap[i][j];
            }
        }

        gRow = new int[201];
        gCol = new int[201];
        gVal = new int[N][N];
        dist = new int[201][201];
        alive = new boolean[201];
				
				// dist[A][B] = 게이트 A~B의 격자 최단거리
        for (int i = 0; i < 201; i++) {
            Arrays.fill(dist[i], -1);
        }
    }
    
		// 이 게이트에서 격자 BFS를 돌려 다른 게이트까지의 거리를 미리 구해 둔다.
		// 한 번에 갈 수 있는 최대 거리가 곧 체력이다.
		// 체력을 상태로 들고 다니지 않고, BFS 범위를 mMaxStamina칸으로 자르는 것으로 대신한다.
    void addGate(int mGateID, int mRow, int mCol) {
		    // 게이트 ID -> 게이트 좌표
        gRow[mGateID] = mRow;
        gCol[mGateID] = mCol;
        // 게이트 좌표 -> 게이트 ID
        gVal[mRow][mCol] = mGateID;
        alive[mGateID] = true;

				// d[행][열] = 이 게이트로부터의 거리, -1은 미방문
        int[][] d = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(d[i], -1);
        }

        Queue<int[]> q = new ArrayDeque<>();

        d[mRow][mCol] = 0;
        q.offer(new int[]{mRow, mCol});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];

            // 여기까지 오는 데 체력을 다 썼다면 더 뻗어나갈 수 없다
            if (d[r][c] == mMaxStamina) continue;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
                if (dungeon[nr][nc] == 1) continue;   // 기둥
                if (d[nr][nc] != -1) continue;        // 이미 방문(먼저 온 쪽이 더 짧다)

                d[nr][nc] = d[r][c] + 1;
                q.offer(new int[]{nr, nc});

                // 도착한 칸에 살아 있는 게이트가 있으면 거리표에 기록한다.
                // 상대 게이트는 이미 addGate가 끝나 자기 쪽 값을 채울 기회가 없다.
                // 따라서 양방향 모두 적어준다.
                int g = gVal[nr][nc];
                if (g != 0 && alive[g]) {
                    dist[mGateID][g] = d[nr][nc];
                    dist[g][mGateID] = d[nr][nc];
                }
                // 게이트를 만나도 멈추지 않는다.
                // 게이트는 벽이 아니라 지나갈 수 있는 길이다.
            }
        }
    }

    void removeGate(int mGateID) {
        alive[mGateID] = false;
    }

    // 여기서는 격자를 전혀 보지 않고, addGate가 만들어 둔 dist 테이블만 사용한다.
    // 간선 비용이 게이트마다 제각각이므로 BFS가 아니라 다익스트라가 필요하다.
    int getMinTime(int mStartGateID, int mEndGateID) {
			  // best[게이트] = 시작 게이트로부터의 최단 시간
        int[] best = new int[201];
        Arrays.fill(best, Integer.MAX_VALUE);

        best[mStartGateID] = 0;

        // 거리가 작은 것부터 꺼내지는 우선순위 큐. {거리, 게이트ID} 형태로 담는다.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[]{0, mStartGateID});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0];
            int u = cur[1];

            // 같은 게이트가 갱신될 때마다 큐에 새로 들어가므로 낡은 항목이 섞여 있다.
            // 이미 더 짧은 경로로 처리한 게이트라면 버린다.
            if (d > best[u]) continue;
            // 큐가 거리 순으로 꺼내 주므로, 도착 게이트가 처음 나온 순간이 곧 최단이다.
            if (u == mEndGateID) return d;

            // u를 거쳐 가면 더 빨라지는 게이트가 있는지 확인한다.
            for (int v = 1; v <= 200; v++) {
                if (!alive[v]) continue;          // 제거된 게이트
                if (dist[u][v] == -1) continue;   // 체력 부족

                // 시작 -> u -> v 로 가는 총 거리
                int nd = d + dist[u][v];
                if (nd < best[v]) {
                    best[v] = nd;
                    pq.offer(new int[]{nd, v});
                }
            }
        }
				
        return -1;
    }
}
