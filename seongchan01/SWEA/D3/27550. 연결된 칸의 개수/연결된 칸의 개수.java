import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	static String[][] arr;
	static int R, C, r, c;
	static boolean[][] visit;
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());

			arr = new String[R][C];

			for (int i = 0; i < R; i++) {
				String line = br.readLine();

				for (int j = 0; j < C; j++) {
					arr[i][j] = String.valueOf(line.charAt(j));
				}
			}

			visit = new boolean[R][C];
			int answer = bfs();

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static int bfs() {

		Queue<int[]> queue = new ArrayDeque<>();

		queue.offer(new int[] { r, c });

		visit[r][c] = true;
		int count = 1;

		while (!queue.isEmpty()) {
			int[] now = queue.poll();

			int x = now[0];
			int y = now[1];

			for (int i = 0; i < 4; i++) {
				int nx = x + dr[i];
				int ny = y + dc[i];

				if (nx < 0 || nx >= R || ny < 0 || ny >= C) {
					continue;
				}

				if (arr[nx][ny].equals("#")) {
					continue;
				}

				if (visit[nx][ny]) {
					continue;
				}

				visit[nx][ny] = true;
				queue.offer(new int[] { nx, ny });

				count++;
			}
		}

		return count;
	}
}
