import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	static int N;
	static int companyX, companyY, homeX, homeY;
	static int[][] arr;
	static boolean[] visit;
	static int answer;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			N = Integer.parseInt(br.readLine());
			arr = new int[N][2];

			StringTokenizer st = new StringTokenizer(br.readLine());

			companyX = Integer.parseInt(st.nextToken());
			companyY = Integer.parseInt(st.nextToken());

			homeX = Integer.parseInt(st.nextToken());
			homeY = Integer.parseInt(st.nextToken());

			for (int i = 0; i < N; i++) {
				arr[i][0] = Integer.parseInt(st.nextToken());
				arr[i][1] = Integer.parseInt(st.nextToken());
			}

			visit = new boolean[N];

			answer = Integer.MAX_VALUE;

			dfs(0, companyX, companyY, 0);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);

	}

	static void dfs(int cnt, int x, int y, int sum) {

		if (cnt == N) {
			sum += Math.abs(x - homeX) + Math.abs(y - homeY);
			answer = Math.min(answer, sum);

			return;
		}

		for (int i = 0; i < N; i++) {

			if (visit[i]) {
				continue;
			}

			visit[i] = true;

			int nx = arr[i][0];
			int ny = arr[i][1];

			int distance = Math.abs(x - nx) + Math.abs(y - ny);

			dfs(cnt + 1, nx, ny, sum + distance);

			visit[i] = false;
		}
	}
}
