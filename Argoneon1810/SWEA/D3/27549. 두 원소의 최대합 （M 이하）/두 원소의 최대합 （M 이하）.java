import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

class Solution
{
	int getBestCombination(int arr[], int N, int M) {
		int from=0, to=N-1;
		int possibleBest=-1;
		while(from<to) {
			int temp = arr[from] + arr[to];
			if (temp > M) --to;			
			else {
				possibleBest = Integer.max(possibleBest, temp);
				++from;
			}
		}
		return possibleBest;
	}
	
	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int arr1[] = new int[N];
		for (int i=0;i<N;++i)
			arr1[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(arr1);
		return Integer.toString(getBestCombination(arr1, N, M));
	}
	
	void solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		for(int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case)
			  .append(' ').append(solveInner(br))
			  .append('\n');
		System.out.print(sb);
	}
	
	public static void main(String args[]) throws Exception {
		new Solution().solve();
	}
}
