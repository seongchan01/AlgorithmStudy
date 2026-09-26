import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * [문제 해결]
 * A도시에서 출발해서 B도시로 가는길이 존재하는지 조사.
 * 길의 중간 중간에는 최대 2개의 갈림길 존재 + 일방 통행만 가능
 * 
 * A와 B는 0과 99고정
 * 모든 길은 순서쌍: 2번에서 출발할 수 있는 길은 (2,5), (2,9)
 * 가는 길의 상관없이 한가지 길이라도 존재하면 길이 존재
 * 화살표 거슬러 갈 수 없음
 * 
 * [풀이]
 * 방향 그래프로 길을 저장한다
 * 0번 도시부터 BFS로 탐색하면서 99번 도시에 도착할 수 있는지 확인한다
 * 99번에 도착하면 1, 도착하지 못하면 0을 출력한다.
 */

public class Solution { // D4. 길찾기

	static ArrayList<Integer>[] graph;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int tc = 1; tc <= 10; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int T = Integer.parseInt(st.nextToken());
			int N = Integer.parseInt(st.nextToken());

			graph = new ArrayList[100];

			for (int i = 0; i < 100; i++) {
				graph[i] = new ArrayList<>();
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				graph[from].add(to);
			}

			int result = bfs();

			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}

		System.out.println(sb);
	}

	static int bfs() {
		boolean[] visit = new boolean[100];

		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(0);
		visit[0] = true;

		while (!queue.isEmpty()) {

			int now = queue.poll();

			if (now == 99) {
				return 1;
			}

			for (int next : graph[now]) {
				if (!visit[next]) {

					visit[next] = true;
					queue.offer(next);
				}
			}
		}

		return 0;
	}

}
