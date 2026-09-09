import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
class Solution
{
    String solveInner(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        boolean isOdd = N%2!=0;
        String arr[] = new String[N];
        st = new StringTokenizer(br.readLine());
        int b = N/2;
        for (int i = 0; i < N; ++i) {
            if (isOdd ? (i<=b) : (i<b))   // pair A
                arr[i*2] = st.nextToken();
            else // pair B
                arr[(isOdd ? i-b-1 : i-b)*2+1] = st.nextToken();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; ++i) {
            if (i!=0) sb.append(' ');
            sb.append(arr[i]);
        }
        return sb.toString();
    }
     
    void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T;
        T = Integer.parseInt(br.readLine().trim());
        for(int test_case = 1; test_case <= T; test_case++)
            System.out.println(String.format("#%d %s", test_case, solveInner(br)));
    }
     
    public static void main(String args[]) throws Exception
    {
        new Solution().solve();
    }
}