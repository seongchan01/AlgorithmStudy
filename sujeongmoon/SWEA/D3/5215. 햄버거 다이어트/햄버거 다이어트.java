import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static int N; // 재료의 수
	static int L; // 제한 칼로리
	static int answer;
	static int[][] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			answer = 0;
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			// 맛은 유지하면서 정해진 칼로리를 넘지 않는 햄버거
			// 정해진 칼로리 이하의 조합 중에서 민기가 가장 선호하는 햄버거를 조합
			arr = new int[N][2];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				arr[i][0] = Integer.parseInt(st.nextToken()); // 점수
				arr[i][1] = Integer.parseInt(st.nextToken());  //칼로리
			}
			
			find(0, 0, 0);
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		System.out.println(sb);
	}
	
	static void find(int count,int scoreSum, int calSum) {
		if (calSum > L) {
			return;
		}
		answer = Math.max(answer, scoreSum);
		if (count == N) {
			return;
		}
		// 들어가는 경우
		find(count+1, scoreSum+arr[count][0], calSum+arr[count][1]);
		// 안 들어가는 경우
		find(count+1, scoreSum, calSum);
	}

}
