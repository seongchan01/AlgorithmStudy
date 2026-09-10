import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			String[] arr = new String[N];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = st.nextToken();
			}
			
			int half = (N + 1) / 2;
			
			int left = 0;
			int right = half;
			
			sb.append("#").append(tc);
			while (left < half) {
				sb.append(" ").append(arr[left++]);
				
				if (right < N) {
					sb.append(" ").append(arr[right++]);
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
}
