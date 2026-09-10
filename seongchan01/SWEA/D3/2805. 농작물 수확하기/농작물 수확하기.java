import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());

			int[][] arr = new int[N][N];

			for (int i = 0; i < N; i++) {
				String str = br.readLine();

				for (int j = 0; j < N; j++) {
					arr[i][j] = str.charAt(j) - '0';
				}
			}

			int center = N / 2;
			int sum = 0;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {

					int distance = Math.abs(i - center) + Math.abs(j - center);

					if (distance <= center) {
						sum += arr[i][j];
					}
				}
			}

			sb.append("#").append(tc).append(" ").append(sum).append("\n");
		}

		System.out.println(sb);
	}
}
