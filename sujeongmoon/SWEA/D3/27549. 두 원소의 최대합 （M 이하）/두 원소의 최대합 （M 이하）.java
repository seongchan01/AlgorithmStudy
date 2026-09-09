import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	static int N;
	static int M;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken()); // M을 넘지 않으면서 최대값
			
			int answer = -1;
			int[] arr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
				
			Arrays.sort(arr);
			
			int minIdx = 0;
			int maxIdx = arr.length - 1;
			
			
			while (minIdx < maxIdx) {
				int sum = arr[minIdx] + arr[maxIdx];
				if (sum > M) {
					maxIdx--;
				} else {
					answer = Math.max(answer, sum);
					minIdx++;
				}
			}
			
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
			
		}
		System.out.println(sb);

	}

}
