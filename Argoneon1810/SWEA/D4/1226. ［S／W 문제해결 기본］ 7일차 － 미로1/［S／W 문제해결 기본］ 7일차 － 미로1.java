import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Solution {
	static final int N = 16;
	static final int NSQ = N*N;
	static final int ditx[] = { -N, N, -1, 1 };
	static final int START = 2;
	static final int END = 3;
	static final int INF = Integer.MAX_VALUE;

	static boolean isOffboard(int c) {
		return isOffboard(c % N, c / N);
	}

	static boolean isOffboard(int cx, int cy) {
		return cx < 0 || cx >= N || cy < 0 || cy >= N;
	}

	static boolean isBlocked(int board[], int c) {
		int curr = board[c];
		return (curr != 0) && ((curr != START) && (curr != END));
	}

	void fill(int board[]) {
		// 정점 수-1 만큼 순회
		// 벨만 포드 알고리즘의 정의에 의해, 이보다 많은 루프가 필요하지 않다는 Least UB
		// 이번 경우는 모든 칸의 가중치가 같아 조기종료가 일어날 것이므로
		// 무한루프로 만들어도 된다.
		 for (int __ = 0; __ < NSQ - 1; ++__) {
			boolean updated = false;
			for (int c = 0; c < NSQ; ++c) {	// 모든 칸 방문
				if (board[NSQ+c] == INF)	// 거리를 전파할 수 있는 칸만 방문
					continue;
				for (int d = 0; d < 4; ++d) {	// 4방탐색
					int n = c + ditx[d];
					if (isOffboard(n))			// 경계조건
						continue;
					if (isBlocked(board, n))	// 4방이 방문 가능한 칸인지
						continue;
					if (board[NSQ+c] + 1 < board[NSQ+n]) {	// 더 나은
						board[NSQ+n] = board[NSQ+c] + 1;	// 거리 찾기
						updated = true;
					}
				}
			}
			if (!updated)
				break;
		}
		for (int c = 0; c < N * N; ++c)
			if (board[NSQ+c] != INF)
				board[c] = -1;
	}

	String solveInner(BufferedReader br) throws IOException {
		int board[] = new int[2 * NSQ];
		int start = 0, end = 0;
		br.readLine(); // 문제번호 줄 버리기
		for (int j = 0; j < N; ++j) {
			char carr[] = br.readLine().toCharArray();
			for (int i = 0; i < N; ++i) {
				int citx = j * N + i;
				board[citx] = carr[i] - '0';
				board[NSQ+citx] = INF;
				switch (board[citx]) {
				case 2:
					start = citx;
					break;
				case 3:
					end = citx;
					break;
				}
			}
		}
		board[NSQ+start] = 0;
		fill(board);
		return board[end] == -1 ? "1" : "0";
	}

	void solve() throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int T;
		T = 10;
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case)
					.append(' ').append(solveInner(br))
					.append('\n');
		System.out.print(sb);
	}

	public static void main(String args[]) throws Exception {
		new Solution().solve();
	}
}