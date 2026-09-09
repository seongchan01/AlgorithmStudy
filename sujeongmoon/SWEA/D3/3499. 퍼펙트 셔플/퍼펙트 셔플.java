import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		// 카드 덱을 정확히 절반으로 나누고, 나눈 것들에서 교대로 카드를 뽑아 새로운 덱을 만드는 거
		// 카드 덱을 절반으로 나누고 그걸 교차해서 넣는다

		
		// 테스트 케이스
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			
			ArrayDeque<String> queue1 = new ArrayDeque<>();
			ArrayDeque<String> queue2 = new ArrayDeque<>();
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				String now = st.nextToken();
				if (i <= N/2) {
					queue1.add(now);
				} else if ((N % 2 == 1) && (i == N/2 + 1)) {
					queue1.add(now);
				} else {
					queue2.add(now);
				}
			}
			
			sb.append("#").append(t).append(' ');
			
			while (!queue1.isEmpty() || !queue2.isEmpty()) {
				if(!queue1.isEmpty()) {
					sb.append(queue1.poll()).append(' ');
				}
				if(!queue2.isEmpty()) {
					sb.append(queue2.poll()).append(' ');
				}
			}
			sb.append("\n");

		}
		System.out.println(sb.toString());
		
	}

}
