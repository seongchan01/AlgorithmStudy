import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static int[] kyCard;
	static int[] iyCard;
	
	static int kyWin;
	static int kyLose;

	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			
			kyCard = new int[9];
			iyCard = new int[9];
			
			kyWin = 0;
			kyLose = 0;
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < 9; i++) {
				kyCard[i] = Integer.parseInt(st.nextToken());
			}
			int iyIdx = 0;
			for (int i = 1; i <= 18; i++) {
				boolean isKY = false;
				for (int j = 0; j < 9; j++) {
					if (i == kyCard[j]) {
						isKY = true;
						break;
					}
				}
				if(!isKY) { // 인영이가 가지고 있는 카드 목록에 넣어줌
					iyCard[iyIdx++] = i;
				}
			}
			
			dfs(0, 0, 0); 
			
			sb.append("#").append(t).append(" ").append(kyWin).append(" ").append(kyLose).append("\n");
		}
		System.out.println(sb);
	}
	
	static void dfs(int depth, int kySum, int iySum) {
		if (depth == 9) { // 끝까지 돌았다면 둘의 값 비교하고 승패 가리기
			if (kySum > iySum) {
				kyWin++;
			} else if (kySum < iySum) {
				kyLose++;
			}
			return;
		}
			
		for(int i = 0; i < 9; i++) {
			// 이미 방문한 경우는 continue
			if (iyCard[i] == -1) continue;

			int nowIY = iyCard[i]; // 값 저장
			iyCard[i] = -1; // 방문처리
			
			// 이긴경우
			if (kyCard[depth] > nowIY) {
				dfs(depth+1, kySum+kyCard[depth]+nowIY, iySum);
			} else if (kyCard[depth] < nowIY) {
				// 진 경우
				dfs(depth+1, kySum, iySum+kyCard[depth]+nowIY);
			}
			// 원복
			iyCard[i] = nowIY;
		
		
		}
	}

}


