import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

class UserSolution {

	int N;
	int MaxStamina;
	int[][] map;

	// 각 gate마다 위치 정보 저장
	int[][] gate;

	// 어떤 위치에 어떤 ID인지*
	int[][] gateID;

	// 게이트 간 거리
	int[][] gateDist;

	int[] dx = { 0, 0, 1, -1 };
	int[] dy = { 1, -1, 0, 0 };

	void init(int N, int mMaxStamina, int mMap[][]) {

		this.N = N;
		this.MaxStamina = mMaxStamina;
		this.map = mMap;

		gate = new int[201][];
		gateID = new int[N][N];
		gateDist = new int[201][201];
	}

	void addGate(int mGateID, int mRow, int mCol) {

		gate[mGateID] = new int[] { mRow, mCol };
		gateID[mRow][mCol] = mGateID;

		bfs(mGateID, mRow, mCol);
	}

	void bfs(int startGateID, int startR, int startC) {
		boolean[][] visit = new boolean[N][N];

		Queue<int[]> queue = new ArrayDeque<>();

		visit[startR][startC] = true;

		queue.offer(new int[] { startR, startC, 0 });

		while (!queue.isEmpty()) {
			int[] now = queue.poll();

			int r = now[0];
			int c = now[1];
			int dist = now[2];

			if (gateID[r][c] != 0 && gateID[r][c] != startGateID) {
				int nextGate = gateID[r][c];

				gateDist[startGateID][nextGate] = dist;
				gateDist[nextGate][startGateID] = dist;
			}

			if (dist >= MaxStamina) {
				continue;
			}

			for (int d = 0; d < 4; d++) {

				int nr = r + dx[d];
				int nc = c + dy[d];

				if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
					continue;
				}

				if (map[nr][nc] == 1) {
					continue;
				}

				if (visit[nr][nc]) {
					continue;
				}

				visit[nr][nc] = true;

				queue.offer(new int[] { nr, nc, dist + 1 });
			}
		}
	}

	void removeGate(int mGateID) {

		int r = gate[mGateID][0];
		int c = gate[mGateID][1];

		gate[mGateID] = null;

		gateID[r][c] = 0;

		for (int i = 0; i <= 200; i++) {

			gateDist[mGateID][i] = 0;
			gateDist[i][mGateID] = 0;
		}
	}

	int getMinTime(int mStartGateID, int mEndGateID) {

		int[] dist = new int[201];
		Arrays.fill(dist, Integer.MAX_VALUE);

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

		pq.offer(new int[] { mStartGateID, 0 });

		dist[mStartGateID] = 0;

		while (!pq.isEmpty()) {
			int[] now = pq.poll();

			int nowGate = now[0];
			int nowDist = now[1];

			if (nowDist > dist[nowGate]) {
				continue;
			}

			if (nowGate == mEndGateID) {
				return nowDist;
			}

			for (int nextGate = 1; nextGate <= 200; nextGate++) {
				if (gate[nextGate] == null) {
					continue;
				}

				if (nextGate == nowGate) {
					continue;
				}

				int moveDist = gateDist[nowGate][nextGate];

				if (moveDist == 0) {
					continue;
				}

				if (moveDist > MaxStamina) {
					continue;
				}

				int nextDist = nowDist + moveDist;

				if (nextDist < dist[nextGate]) {

					dist[nextGate] = nextDist;

					pq.offer(new int[] { nextGate, nextDist });
				}
			}
		}

		return -1;
	}
}
