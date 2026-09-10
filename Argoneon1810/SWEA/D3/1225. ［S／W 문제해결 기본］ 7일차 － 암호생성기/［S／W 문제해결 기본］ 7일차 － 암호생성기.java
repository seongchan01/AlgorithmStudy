import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Iterator;

class Solution
{	
	String solveInner(BufferedReader br) throws IOException {
		br.readLine(); // 숫자 의미 없음
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = 8; // given
		
		Queue<Integer> q = new ArrayDeque<Integer>();
		int minValue = Integer.MAX_VALUE;
		for (int i=0; i<N; ++i) {
			int t = Integer.parseInt(st.nextToken());
			minValue = Integer.min(minValue, t);
			q.add(t);
		}
		
		// fastforward
		int fastforward = minValue - (minValue % 15);
		if (fastforward == minValue)
			// can be refined but im not diligent enough
			fastforward-=15;
		for (int i=0; i<N; ++i)
			q.add(q.poll() - fastforward);
		
		// actual run
		while(true) {
			boolean breakLoop = false;
			for (int i=1; i<=5; ++i) {
				int t = q.poll()-i;
				if (t > 0) q.add(t);
				else {
					q.add(0);
					breakLoop = true;
					break;
				}
			}
			if (breakLoop) break;
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Integer> it = q.iterator();
		sb.append(it.next());
		while (it.hasNext())
			sb.append(' ').append(it.next());
		return sb.toString();
	}
	
	void solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T;
//		T = Integer.parseInt(br.readLine().trim());
		T = 10;
		for(int test_case = 1; test_case <= T; test_case++)
			System.out.println(String.format("#%d %s", test_case, solveInner(br)));
	}
	
	public static void main(String args[]) throws Exception
	{
		new Solution().solve();
	}
}
