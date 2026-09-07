import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution {
	
	// 
	static int[] dr = new int[] {-1, 1, 0, 0};
	static int[] dc = new int[] {0, 0, -1, 1};
	
	static int H;
	static int W;
	static char[][] arr;
	static String command;
	
	static class Tank {
		int r;
		int c;

		Tank(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t  <= T; t++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			
			arr = new char[H][W];
			Tank tank = null;
			
			for (int i = 0; i < H; i++) {
				String now = br.readLine();
				for (int j = 0; j < W; j++) {
					arr[i][j] = now.charAt(j);
					if (arr[i][j] == '^' || arr[i][j] == 'v' || arr[i][j] == '<' || arr[i][j] == '>') {
						tank = new Tank(i, j);

					}
				}
				 
			}
			
			br.readLine(); //넘기기
			command = br.readLine();
			
			for (int i = 0; i < command.length(); i++) {
				moveTank(tank, command.charAt(i));
			}
			
			sb.append("#").append(t).append(" ");
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					sb.append(arr[i][j]);
				}
				sb.append("\n");
			}
		}
		System.out.println(sb);
	}
	
	static void moveTank(Tank tank, char c) {
		 
		// 방향
		if (c == 'U') {
			arr[tank.r][tank.c] = '.'; // 우선 평지화
			int nextR = tank.r - 1;
			if (nextR >= 0 && nextR < H && arr[nextR][tank.c] == '.' ) {
				tank.r = nextR;
			}
			arr[tank.r][tank.c] = '^';
		} else if (c == 'D') {
			arr[tank.r][tank.c] = '.'; // 우선 평지화
			int nextR = tank.r + 1;
			if (nextR >= 0 && nextR < H && arr[nextR][tank.c] == '.' ) {
				tank.r = nextR;
			}
			arr[tank.r][tank.c] = 'v';
		} else if (c == 'L') {
			arr[tank.r][tank.c] = '.'; // 우선 평지화
			int nextC = tank.c - 1;
			if (nextC >= 0 && nextC < W && arr[tank.r][nextC] == '.' ) {
				tank.c = nextC;
			}
			arr[tank.r][tank.c] = '<';
		} else if (c == 'R') {
			arr[tank.r][tank.c] = '.'; // 우선 평지화
			int nextC = tank.c + 1;
			if (nextC >= 0 && nextC < W && arr[tank.r][nextC] == '.' ) {
				tank.c = nextC;
			}
			arr[tank.r][tank.c] = '>';
		} else if (c == 'S') {
			// 포탄 쏘는 경우
			int idx = 0;
			// 위
			if (arr[tank.r][tank.c] == '^') {
				idx = 0;
			} else if (arr[tank.r][tank.c] == 'v') {
				idx = 1;
			} else if (arr[tank.r][tank.c] == '<') {
			
				idx = 2;
			} else if (arr[tank.r][tank.c] == '>') {
				idx = 3;
			} 
			
			int nowR = tank.r + dr[idx];
			int nowC = tank.c + dc[idx];
			while(nowR >= 0 && nowR < H && nowC >= 0 && nowC < W) {
				if (arr[nowR][nowC] == '*') { // 벽돌
					arr[nowR][nowC] = '.';
					break;
				} else if (arr[nowR][nowC] == '#') { // 강철
					break;
				}
				nowR += dr[idx];
				nowC += dc[idx];
			}
		}
	}

}

