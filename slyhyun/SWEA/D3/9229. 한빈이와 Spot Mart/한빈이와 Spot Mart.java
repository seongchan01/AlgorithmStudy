import java.io.*;
import java.util.*;

public class Solution {
    static int N, M;
    static int[] snacks;
    static int max;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            snacks = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                snacks[i] = Integer.parseInt(st.nextToken());
            }

            max = -1;
            
            combination(0, 0, 0);

            sb.append('#').append(tc).append(' ').append(max).append('\n');
        }
        
        System.out.print(sb);
    }

    static void combination(int cnt, int start, int weight) {
        if (weight > M) return;

        if (cnt == 2) {
            max = Math.max(max, weight);
            return;
        }

        for (int i = start; i < N; i++) {
            combination(cnt + 1, i + 1, weight + snacks[i]);
        }
    }
}
