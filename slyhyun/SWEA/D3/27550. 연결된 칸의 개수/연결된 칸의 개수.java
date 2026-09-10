import java.util.*;
import java.io.*;

public class Solution {
	static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    
    static int count;
    
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			
			int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            int startR = Integer.parseInt(st.nextToken());
            int startC = Integer.parseInt(st.nextToken());
            
            char[][] grid = new char[R][C];
            boolean[][] visited = new boolean[R][C];
            
            count = 0;
            
            for (int i = 0; i < R; i++) {
                grid[i] = br.readLine().toCharArray();
            }
            
            dfs(startR, startC, R, C, grid, visited);
            
            sb.append("#").append(tc).append(" ").append(count).append("\n");
        }
		
        System.out.print(sb);
	}
	
	static void dfs(int r, int c, int R, int C, char[][] grid, boolean[][] visited) {
        visited[r][c] = true;
        
        count++;
        
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            
            if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
            if (visited[nr][nc] || grid[nr][nc] == '#') continue;
            
            dfs(nr, nc, R, C, grid, visited);
        }
    }
}
