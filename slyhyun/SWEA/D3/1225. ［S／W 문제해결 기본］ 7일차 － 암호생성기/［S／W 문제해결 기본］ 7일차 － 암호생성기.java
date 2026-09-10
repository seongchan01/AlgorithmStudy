import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		for (int tc = 1; tc <= 10; tc++) {
			br.readLine();
			
			Queue<Integer> q = new ArrayDeque<>();
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 8; i++) {
				q.offer(Integer.parseInt(st.nextToken()));
			}
			
			int minus = 1;
			
			while(true) {
				int num = q.poll() - minus;
				
				if (num < 1) {
					q.offer(0);
					break;	
				}
				
				q.offer(num);
				
				minus = (minus % 5) + 1; 
			}
						
			sb.append("#").append(tc);
			for (int n : q) {
				sb.append(" ").append(n);
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
}
