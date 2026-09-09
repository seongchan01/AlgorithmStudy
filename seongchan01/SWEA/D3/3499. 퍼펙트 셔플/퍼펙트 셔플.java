import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			int N = Integer.parseInt(br.readLine());

			int center = (N + 1) / 2;

			Queue<String> queue1 = new ArrayDeque<>();
			Queue<String> queue2 = new ArrayDeque<>();

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				if (i < center) {
					queue1.add(st.nextToken());
				} else {
					queue2.add(st.nextToken());
				}
			}

			sb.append("#").append(tc).append(" ");

			while (!queue1.isEmpty()) {

				sb.append(queue1.poll()).append(" ");

				if (!queue2.isEmpty()) {
					sb.append(queue2.poll()).append(" ");
				}
			}

			sb.append("\n");
		}

		System.out.println(sb);
	}
}
