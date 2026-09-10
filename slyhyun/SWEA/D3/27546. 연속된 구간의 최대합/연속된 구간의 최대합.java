import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
						
			int[] arr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			int sum = 0;
			
			for (int i = 0; i < K; i++) {
				sum += arr[i];
			}
			
			int max = sum;
			
			for (int i = K; i < N; i++) {
				sum += arr[i] - arr[i - K];
				
				max = Math.max(max, sum);
			}
			
			sb.append("#").append(tc).append(" ").append(max).append("\n");
		}
		
		System.out.println(sb);
	}
}
