import java.io.*;

public class Solution {
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	static int result;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int tc = 1; tc <= 10; tc++) {
			br.readLine();
			
			int[][] maze = new int[16][16];
			int r = -1;
			int c = -1;
			
			for (int i = 0; i < 16; i++) {
                String line = br.readLine();
                for (int j = 0; j < 16; j++) {
                    maze[i][j] = line.charAt(j) - '0';
                    if (maze[i][j] == 2) {
                        r = i;
                        c = j;
                    }
                }
            }
			
			maze[r][c] = 1;
			
			result = 0;
			
			dfs(r, c, maze);
			
			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}
		
		System.out.print(sb);
	}
	
	static void dfs(int r, int c, int[][] maze) {
	    if (result == 1) return;
	    
	    if (maze[r][c] == 3) {
	        result = 1;
	        
	        return;
	    }
	    
	    maze[r][c] = 1;
	    
	    for (int d = 0; d < 4; d++) {
	        int nr = r + dr[d];
	        int nc = c + dc[d];
	        
	        if (maze[nr][nc] == 1) continue;
	        
	        dfs(nr, nc, maze);
	    }
	}

}
