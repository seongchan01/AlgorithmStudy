import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Solution {
	
	static int N;
	static int K;

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			long answer = Long.MIN_VALUE;
			int[] arr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			long sum = 0;
			
			for (int i = 0; i < N; i++) {
				sum += arr[i];
				if (i < K-1) {
					continue;
				}
	
				if (sum > answer) {
					answer = sum;
				}
				
				sum -= arr[i-K+1];
			}
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
			
		}
		System.out.println(sb);
		
	}
	 
}
