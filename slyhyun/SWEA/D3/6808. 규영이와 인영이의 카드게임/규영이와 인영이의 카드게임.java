import java.io.*;
import java.util.*;

public class Solution {
	static int[] kCards = new int[9];
	static int[] iCards = new int[9];
	static boolean[] visited = new boolean[9];
	static int win, lose;
	
	static final int[] FACTORIAL = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			boolean[] isSelected = new boolean[19];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 9; i++) {
				kCards[i] = Integer.parseInt(st.nextToken());
				isSelected[kCards[i]] = true; 
			}

			int idx = 0;
			for (int i = 1; i <= 18; i++) {
				if (!isSelected[i]) {
					iCards[idx++] = i;
				}
			}

			win = 0;
			lose = 0;
			permutation(0, 0, 0);

			sb.append("#").append(tc).append(" ").append(win).append(" ").append(lose).append("\n");
		}
		
		System.out.print(sb);
	}

	static void permutation(int round, int kScore, int iScore) {
		if (kScore > 85) {
			win += FACTORIAL[9 - round];
			return;
		}
		if (iScore > 85) {
			lose += FACTORIAL[9 - round];
			return;
		}

		if (round == 9) {
			if (kScore > iScore) win++;
			else if (kScore < iScore) lose++;
			return;
		}

		for (int i = 0; i < 9; i++) {
			if (!visited[i]) {
				visited[i] = true;
				int sum = kCards[round] + iCards[i];
				
				if (kCards[round] > iCards[i]) {
					permutation(round + 1, kScore + sum, iScore);
				}
				else {
					permutation(round + 1, kScore, iScore + sum);
				}
				
				visited[i] = false;
			}
		}
	}
}
