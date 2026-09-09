import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

// 메모리:  
// 실행시간: 
public class Solution {
	
	static int[] dr = new int[] {1, 0, -1, 0};
	static int[] dc = new int[] {0, 1, 0, -1};
	
	static int[][] arr;
	static boolean[][] isVisited;
	static int answer;

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int t = 1; t <= 10; t++) {
			
			br.readLine();
			
			arr = new int[16][16];
			isVisited = new boolean[16][16];
			
			int[] start = new int[2];
			answer = 0;
			
			for (int i = 0; i < 16; i++) {
				String row = br.readLine();
				for (int j = 0; j < 16; j++) {
					arr[i][j] = row.charAt(j) - '0';
					if (arr[i][j] == 2) {
						start[0] = i;
						start[1] = j;
					} 
				}
			}
			
			bfs(start[0], start[1]);
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		
		System.out.println(sb);
		
	}

	private static void bfs(int r, int c) {
		
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] {r, c});
		isVisited[r][c] = true;
		
		while (!queue.isEmpty()) {
			int[] now = queue.poll();
			int nowR = now[0];
			int nowC = now[1];
			
			
			if (arr[nowR][nowC] == 3) {
				answer = 1;
				break;
			}
			
			for (int i = 0; i < 4; i++) {
				int nextR = nowR + dr[i];
				int nextC = nowC + dc[i];
				
				if (nextR < 0 || nextC < 0 || nextR >= 16 || nextC >= 16) {
					continue;
				}
				if (isVisited[nextR][nextC] || arr[nextR][nextC] == 1) {
					continue;
				}
				
				isVisited[nextR][nextC] = true;
				queue.offer(new int[] {nextR, nextC});
			}
			
			
		}
	}

}
