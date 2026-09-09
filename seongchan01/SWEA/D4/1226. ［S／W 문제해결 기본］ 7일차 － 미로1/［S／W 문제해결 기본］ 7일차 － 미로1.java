import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
	static int N = 16;
	static int[][] arr;
	static boolean[][] visit;
	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { 1, 0, -1, 0 };
	static int result;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int t = 1; t <= 10; t++) {
			int tc = Integer.parseInt(br.readLine());

			arr = new int[N][N];
			visit = new boolean[N][N];

			int startX = 0;
			int startY = 0;

			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < N; j++) {
					arr[i][j] = line.charAt(j) - '0';

					if (arr[i][j] == 2) {
						startX = i;
						startY = j;
					}
				}
			}

			result = 0;

			dfs(startX, startY);

			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}

		System.out.println(sb);
	}

	static void dfs(int x, int y) {
		if (result == 1)
			return;

		visit[x][y] = true;

		for (int next = 0; next < 4; next++) {
			int nx = x + dx[next];
			int ny = y + dy[next];

			if (nx < 0 || nx >= N || ny < 0 || ny >= N || visit[nx][ny] || arr[nx][ny] == 1) {
				continue;
			}

			if (arr[nx][ny] == 3) {
				result = 1;
				return;
			}

			dfs(nx, ny);
		}
	}
}
