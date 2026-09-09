import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int tc = 1; tc <= 10; tc++) {

			int N = Integer.parseInt(br.readLine());

			Deque<Character> stack = new ArrayDeque<>();

			String str = br.readLine();

			for (int i = 0; i < N; i++) {
				char c = str.charAt(i);

				if (!stack.isEmpty()) {
					char now = stack.peek();

					if ((now == '(' && c == ')') || (now == '[' && c == ']') || (now == '{' && c == '}')
							|| (now == '<' && c == '>')) {
						stack.pop();
						continue;
					}
				}

				stack.push(c);
			}

			int answer;
			if (stack.isEmpty()) {
				answer = 1;
			} else {
				answer = 0;
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}
}
