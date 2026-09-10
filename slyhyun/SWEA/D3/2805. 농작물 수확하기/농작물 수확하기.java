import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			int[][] farm = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				
				for (int j = 0; j < N; j++) {
					farm[i][j] = line.charAt(j) - '0';
				}
			}
			
			int answer = 0;
			int mid = N / 2;
			
			for (int i = 0; i < N ; i++) {
				for (int j = 0; j < N; j++) {
					if (Math.abs(mid - i) + Math.abs(mid - j) > mid) continue;
					
					answer += farm[i][j];
				}
			}
			
			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}
	
		System.out.print(sb);
	}
}
