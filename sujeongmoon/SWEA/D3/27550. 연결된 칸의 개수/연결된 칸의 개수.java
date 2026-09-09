import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	static int[] dr = new int[] {1, 0, -1, 0};
	static int[] dc = new int[] {0, 1, 0, -1};
	
	static int R;
	static int C;
	static int answer;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken()); 
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken()); 
			answer = 0;
			
			char[][] arr = new char[R][C];
			boolean[][] isVisited = new boolean[R][C];
			
			for (int i = 0; i < R; i++) {
				String now = br.readLine();
				arr[i] = now.toCharArray();
			}
			
			dfs(r, c, arr, isVisited);
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
			
		}
		System.out.println(sb);

	}
	
	static void dfs(int r, int c, char[][] arr, boolean[][] isVisited) {
		if (r == R || c == C || r == -1 || c == -1) {
			return;
		}
		if (arr[r][c] == '#') {
			return;
		}
		if (isVisited[r][c]) {
			return;
		}
		
		isVisited[r][c] = true;
		answer++;
		
		for (int i = 0; i < 4; i++) {
			dfs(r+dr[i], c+dc[i], arr, isVisited);
		}
		
	}


}
