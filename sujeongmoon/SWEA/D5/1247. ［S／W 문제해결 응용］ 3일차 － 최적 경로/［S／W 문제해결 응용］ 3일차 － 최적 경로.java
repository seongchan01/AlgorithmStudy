import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static int N; // 고객
	
	static int[] houseArr;
	static int[][] customerArr;
	static boolean isVisited[];
	static int answer;

	public static void main(String[] args) throws Exception {

		// N명의 고객을 방문하고 자신의 집에 돌아가야한다
		// 회사와 집의 위치, 각 고객의 위치는 이차원 정수 좌표로 주어짐
		// 회사에서 출발해서 N명의 고객을 모두 방문하고 집으로 돌아오는 경로 중 가장 짧은 것
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
	
		for (int t = 1; t <= T; t++) {
			
			N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int[] comArr = new int[2];
			houseArr = new int[2];
			customerArr = new int[N][2];
			isVisited = new boolean[N];
			
			comArr[0] = Integer.parseInt(st.nextToken());
			comArr[1] = Integer.parseInt(st.nextToken());
			houseArr[0] = Integer.parseInt(st.nextToken());
			houseArr[1] = Integer.parseInt(st.nextToken());
			
			for (int i = 0; i < N; i++) {
				customerArr[i][0] = Integer.parseInt(st.nextToken());
				customerArr[i][1] = Integer.parseInt(st.nextToken());
			}
			
			answer = Integer.MAX_VALUE;
			
			recur(0, 0, comArr[0], comArr[1]);
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");

		}
		System.out.print(sb);
		
	}
	
	static void recur(int count, int sum, int r, int c) {
		if (count == N) {
			answer = Math.min(Math.abs(r - houseArr[0]) + Math.abs(c - houseArr[1]) + sum,  answer);
			return;
		}
		for (int i = 0; i < N; i++) {
			if (isVisited[i]) {
				continue;
			}
			isVisited[i] = true;
			recur(count+1, Math.abs(r - customerArr[i][0]) + Math.abs(c - customerArr[i][1]) + sum, customerArr[i][0], customerArr[i][1]);
			isVisited[i] = false;
		}
	}

}
