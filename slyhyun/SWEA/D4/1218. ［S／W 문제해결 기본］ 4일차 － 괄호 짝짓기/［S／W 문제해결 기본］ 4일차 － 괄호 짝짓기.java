import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int tc = 1; tc <= 10; tc++) {
			int n = Integer.parseInt(br.readLine());
			String str = br.readLine();
			
			Stack<Character> stack = new Stack<>();
			
			int answer = 1;
			
			for (int i = 0; i < n; i++) {
				char ch = str.charAt(i);
				
				if (ch == '(' || ch == '[' || ch == '{' || ch == '<') {
					stack.push(ch);
				}
				else {
					if (stack.isEmpty()) {
						answer = 0;
						break;
					}
					
					char top = stack.pop();
					
					if ((ch == ')' && top != '(') ||
						(ch == ']' && top != '[') ||
						(ch == '}' && top != '{') ||
						(ch == '>' && top != '<')) {
						answer = 0;
						break;
					}
				}
			}
			
			if (!stack.isEmpty()) {
				answer = 0;
			}
			
			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}
		
		System.out.println(sb);
	}
}
