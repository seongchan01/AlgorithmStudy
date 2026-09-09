import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Solution {

	public static void main(String[] args) throws Exception {
		
		// 괄호들의 짝이 모두 맞는지 판별하는 프로그램
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int tc = 1; tc <= 10; tc++) {
			int num = Integer.parseInt(br.readLine());
			String str = br.readLine();
			
			ArrayDeque<Character> stack = new ArrayDeque<>();
			int b = 1; // 1: 유효, 0: 유효x
			
			for (int i = 0; i < num; i++) {
				char now = str.charAt(i);
				
				if (now == ']' || now == ')' || now == '>' || now == '}') {
					if (now == ']' && (stack.isEmpty() || stack.peek() != '[')) {
						b = 0;
						break;
					} else if (now == '}' && (stack.isEmpty() || stack.peek() != '{')) {
						b = 0;
						break;
					} else if (now == '>' && (stack.isEmpty() || stack.peek() != '<')) {
						b = 0;
						break;
					} else if (now == ')' && (stack.isEmpty() || stack.peek() != '(')) {
						b = 0;
						break;
					}
					stack.pop();
				} else {
					stack.push(now);
					continue;
				}
					
			}
			
			if (!stack.isEmpty()) {
				b = 0;
			}
			
			sb.append("#").append(tc).append(" ").append(b).append("\n");
		}
		System.out.println(sb.toString());

	}

}