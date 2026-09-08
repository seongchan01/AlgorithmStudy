import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static int[] gyu;
	static int[] in;
	static boolean[] visit;
	static int win, lose;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			gyu = new int[9];
			in = new int[9];

			boolean[] used = new boolean[19];

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 9; i++) {
				gyu[i] = Integer.parseInt(st.nextToken());
				used[gyu[i]] = true;
			}

			int idx = 0;
			for (int i = 1; i <= 18; i++) {
				if (!used[i]) {
					in[idx++] = i;
				}
			}

			visit = new boolean[9];
			win = 0;
			lose = 0;

			dfs(0, 0, 0);

			sb.append("#").append(tc).append(" ").append(win).append(" ").append(lose).append("\n");
		}

		System.out.println(sb);
	}

	static void dfs(int depth, int gyuScore, int inScore) {

		if (depth == 9) {
			if (gyuScore > inScore) {
				win++;
			} else if (gyuScore < inScore) {
				lose++;
			}
			return;
		}

		for (int i = 0; i < 9; i++) {
			if (!visit[i]) {

				visit[i] = true;

				int sum = gyu[depth] + in[i];

				if (gyu[depth] > in[i]) {
					dfs(depth + 1, gyuScore + sum, inScore);
				} else {
					dfs(depth + 1, gyuScore, inScore + sum);
				}

				visit[i] = false;
			}
		}
	}
}
