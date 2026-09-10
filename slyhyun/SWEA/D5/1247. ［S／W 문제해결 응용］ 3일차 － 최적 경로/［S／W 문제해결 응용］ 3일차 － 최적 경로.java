import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static int homeR, homeC;
    static int[][] customers;
    static boolean[] visited;
    static int min;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int comR = Integer.parseInt(st.nextToken());
            int comC = Integer.parseInt(st.nextToken());
            homeR = Integer.parseInt(st.nextToken());
            homeC = Integer.parseInt(st.nextToken());

            customers = new int[N][2];
            visited = new boolean[N];
            
            for (int i = 0; i < N; i++) {
                customers[i][0] = Integer.parseInt(st.nextToken());
                customers[i][1] = Integer.parseInt(st.nextToken());
            }
            
            min = Integer.MAX_VALUE;

            permutation(0, comR, comC, 0);

            sb.append('#').append(tc).append(' ').append(min).append('\n');
        }
        
        System.out.print(sb);
    }

    static void permutation(int cnt, int R, int C, int dist) {
        if (dist >= min) return;

        if (cnt == N) {
            int finalDist = dist + Math.abs(R - homeR) + Math.abs(C - homeC);
            
            min = Math.min(min, finalDist);
            
            return;
        }

        for (int i = 0; i < N; i++) {
            if (visited[i]) continue;

            visited[i] = true;
            
            int nextDist = Math.abs(R - customers[i][0]) + Math.abs(C - customers[i][1]);
            
            permutation(cnt + 1, customers[i][0], customers[i][1], dist + nextDist);
            
            visited[i] = false;
        }
    }
}
