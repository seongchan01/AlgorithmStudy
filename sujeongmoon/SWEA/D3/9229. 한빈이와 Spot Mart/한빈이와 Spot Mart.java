import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static int N; // 과자 봉지 개수
	static int M; // 무게 합 제한
	static int[] arr; // 과자 봉지 배열

	public static void main(String[] args) throws Exception{
		// 스팟마트에는 n개의 과자 봉지가 있으며, 각 과자 봉지는 ai 그램의 무게를 가진다.
		// 배가 많이 고픈 한빈이는 무게가 많이 나가는 과자 봉지. 
		// 두 봉지의 무게가 M그랭믈 초과하며 ㅇ 안돼.
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			arr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			int answer = -1;
			int sum = 0;
			
			for (int i = 0; i < N-1; i++) {
				for(int j = i+1; j < N; j++) {
					if (arr[i]+arr[j] > M) {
						continue;
					}
					answer = Math.max(answer,  arr[i]+arr[j]);
				}
				 
			}
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		System.out.print(sb); 
	}

}
