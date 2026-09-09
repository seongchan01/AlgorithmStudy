import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.util.StringTokenizer;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;

class Solution
{
	static Character openings[];
	static Character closings[];
	static Map<Character, Character> pairOpeningClosing;
	static {
		openings = new Character[] {'(', '{', '[', '<'};
		closings = new Character[] {')', '}', ']', '>'};
		pairOpeningClosing = new HashMap<>();
		pairOpeningClosing.put(')', '(');
		pairOpeningClosing.put('}', '{');
		pairOpeningClosing.put(']', '[');
		pairOpeningClosing.put('>', '<');
	}
	
	@SafeVarargs
	public static <T> boolean isAnyOf(T target, T... candidates) {
	    if (target == null) {
	        for (T candidate : candidates) {
	            if (candidate == null) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    for (T candidate : candidates) {
	        if (target.equals(candidate)) {
	            return true;
	        }
	    }
	    return false;
	}

	boolean isOpening(char c) { return isAnyOf((Character) c, openings); }
	boolean isClosing(char c) { return isAnyOf((Character) c, closings); }
	
	String solveInner(BufferedReader br) throws IOException {
		br.readLine(); // 숫자 안중요함
		String current = br.readLine().trim();
		Deque<Character> s = new ArrayDeque<Character>();
		for (char c : current.toCharArray()) {
			if (isOpening(c)) // 열릴 땐 그냥 스택하면 된다
				s.push(c);
			else {
				// 닫힐 때 두가지 경우 가능
				// 1. 닫혔는데 스택이 비어 있어 쌍을 이룰 수 없음
				// 2. 닫혔는데 스택의 제일 위가 닫힌 괄호와 쌍을 이루지 않음
				if (s.isEmpty())
					return Integer.toString(0);
				char opening = s.pop();
				if (pairOpeningClosing.get(c) != opening)
					return Integer.toString(0);
			}
		}
		// 모든 문자열을 돌렸는데 스택이 비어 있지 않다면 열리기만 한 괄호가 있음을 의미
		if (!s.isEmpty())
			return Integer.toString(0);
		return Integer.toString(1);
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
