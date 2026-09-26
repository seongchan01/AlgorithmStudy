import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * [문제 해석]
 * 해야 할 작업이 V개, 이들 중 어떤 작업은 특정 작업이 끝내야만 시작할 수 있는 선행관계이다.
 * 각 작업은 하나의 정점으로 표시, 선행 관계는 방향 간선으로 표현
 * 이 그래프에서 사이클은 존재X
 * 일을 끝낼 수 있는 작업 순서를 찾아라.
 * 
 * [풀이]
 * 각 작업의 선행 작업 개수를 저장하고, 선행 작업이 없는 작업부터 Queue에 넣는다
 * 작업을 하나씩 수행하면서 다음 작업의 선행 작업 개수를 줄인
 * 선행 작업 개수가 0이 되면 Queue에 넣어 순서대로 처리한.
 */

public class Solution { // D6. 작업 순서

	static int V;
	static int E;

	static ArrayList<Integer>[] graph;
	static int[] arr;

	static StringBuilder sb;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		for (int tc = 1; tc <= 10; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			graph = new ArrayList[V + 1];
			arr = new int[V + 1];

			for (int i = 1; i <= V; i++) {
				graph[i] = new ArrayList<>();
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				// from 작업이 끝나면 갈 수 있는 다음 작업
				graph[from].add(to);

				// to 작업의 선행 작업 개수
				arr[to]++;
			}

			sb.append("#").append(tc);

			bfs();

			sb.append("\n");
		}

		System.out.println(sb);
	}

	static void bfs() {
		Queue<Integer> queue = new ArrayDeque<>();

		// 선행 작업이 없는 작업을 먼저 Queue에 넣는다
		for (int i = 1; i <= V; i++) {
			if (arr[i] == 0) {
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()) {
			int now = queue.poll();

			sb.append(" ").append(now);

			// 현재 작업이 끝나면 영향을 받는 다음 작업 확인
			for (int next : graph[now]) {

				// 선행 작업 하나가 끝났으므로 감소
				arr[next]--;

				// 모든 선행 작업이 끝났다면 수행 가능
				if (arr[next] == 0) {
					queue.offer(next);
				}
			}
		}
	}
}
