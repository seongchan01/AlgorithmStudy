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
			int M = Integer.parseInt(st.nextToken());
			
			int[] arr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			Arrays.sort(arr);
			
			int left = 0;
			int right = N - 1;
			int max = -1;
			
			while (left < right) {
				int sum = arr[left] + arr[right];
				
				if (sum <= M) {
					max = Math.max(max, sum);
					left++;
				}
				else {
					right--;
				}
			}
			
			sb.append("#").append(tc).append(" ").append(max).append("\n");
		}
		
		System.out.println(sb);
	}
}
