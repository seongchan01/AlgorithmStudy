import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int tc = 1; tc <= 10; tc++) {
			int T = Integer.parseInt(br.readLine());

			Queue<Integer> queue = new ArrayDeque<>();

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 8; i++) {
				queue.add(Integer.parseInt(st.nextToken()));
			}

			boolean finished = false;
			while (true) {

				for (int i = 1; i <= 5; i++) {
					int n = queue.poll() - i;

					if (n <= 0) {
						queue.add(0);
						finished = true;
						break;
					} else {
						queue.add(n);
					}
				}

				if (finished) {
					break;
				}

			}

			sb.append("#").append(T).append(" ");

			while (!queue.isEmpty()) {
				sb.append(queue.poll()).append(" ");
			}

			sb.append("\n");
		}

		System.out.println(sb);
	}
}
