import java.io.*;
import java.util.*;

public class Solution {
    static int N, L;
    static int[] scores;
    static int[] calories;
    static int max;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            scores = new int[N];
            calories = new int[N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                
                scores[i] = Integer.parseInt(st.nextToken());
                calories[i] = Integer.parseInt(st.nextToken());
            }

            max = 0;
            
            dfs(0, 0, 0);

            sb.append("#").append(tc).append(" ").append(max).append("\n");
        }
        
        System.out.print(sb);
    }

    static void dfs(int index, int score, int calorie) {
        if (calorie > L) {
            return;
        }

        if (index == N) {
            max = Math.max(max, score);
            return;
        }

        dfs(index + 1, score + scores[index], calorie + calories[index]);
        dfs(index + 1, score, calorie);
    }
}
