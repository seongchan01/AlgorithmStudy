import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


class Solution
{
	static int dr[] = {-1,0,0,1};
	static int dc[] = {0,-1,1,0};
	static boolean isOffboard(int R, int C, int cr, int cc) {
		return cr >= R || cr < 0 || cc >= C || cc < 0; 
	}
	int dfs(char board[], boolean visited[], int R, int C, int cr, int cc) {
		if (isOffboard(R, C, cr, cc)) return 0;
		int ctx=cr*R+cc;
		if (visited[ctx]) return 0;
		if (board[ctx]=='#') return 0;
		int sum=1;
		visited[ctx] = true;
		for (int i=0;i<4;++i)
			sum+=dfs(board, visited, R, C, cr+dr[i], cc+dc[i]);
		return sum;
	}
	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int R, C, r, c;
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		char board[] = new char[R*C];
		boolean visited[] = new boolean[R*C];
		for (int j=0; j<R; ++j) {
			char s[] = new StringTokenizer(br.readLine()).nextToken().toCharArray();
			for (int i=0; i<C; ++i) {
				int ctx=j*R+i;
				board[ctx] = s[i];
			}
		}
		return Integer.toString(dfs(board, visited, R, C, r, c));
	}
	
	void solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T;
		T = Integer.parseInt(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		for(int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case).append(' ').append(solveInner(br)).append('\n');
		System.out.print(sb);
	}
	
	public static void main(String args[]) throws Exception
	{
		new Solution().solve();
	}
}
