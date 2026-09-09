import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws Exception {

		// 8개의 숫자를 입력받는다
		// 첫 번째 숫자를 1 감소시키고 맨 뒤로 보낸다
		// 다음 수는 2, 3씩 점점 감소시킴...
		// 숫자가 0보다 작아지면 0으로 유지되며, 프로그램 종료
		// -> 8자리의 숫자 값이 암호가 됨
		// 사이클이 다 돌아가면 다시 1로 리셋
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int tc = 1; tc <= 10; tc++) {
			br.readLine(); // 한 줄 넘기기
			ArrayDeque<Integer> queue = new ArrayDeque<>();
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 8; i++) {
				queue.add(Integer.parseInt(st.nextToken()));
			}
			
			int count = 1; // 뺄 수
			
			while (true) {
				int now = queue.poll();
				now -= count;
				
				if (now <= 0) {
					queue.add(0);
					break;
				}
				
				count++;
				if (count == 6) {
					count = 1;
				}
				queue.add(now);
			}
			
			// 출력
			sb.append("#").append(tc);
			while (!queue.isEmpty()) {
				sb.append(' ').append(queue.poll());
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

}
