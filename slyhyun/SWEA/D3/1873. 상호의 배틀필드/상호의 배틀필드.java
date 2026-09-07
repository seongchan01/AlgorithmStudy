import java.util.*;
import java.io.*;

public class Solution {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static char[] tank = {'^', '>', 'v', '<'};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
            
            char[][] map = new char[H][W];
            int r = 0, c = 0, d = 0;
            
            for (int i = 0; i < H; i++) {
                String line = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = line.charAt(j);
                    
                    if (map[i][j] == '^') { r = i; c = j; d = 0; }
                    else if (map[i][j] == '>') { r = i; c = j; d = 1; }
                    else if (map[i][j] == 'v') { r = i; c = j; d = 2; }
                    else if (map[i][j] == '<') { r = i; c = j; d = 3; }
                }
            }
            
            int N = Integer.parseInt(br.readLine());
            String commands = br.readLine();
            
            for (int i = 0; i < N; i++) {
                char cmd = commands.charAt(i);
                
                if (cmd == 'S') {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    
                    while (nr >= 0 && nr < H && nc >= 0 && nc < W) {
                        if (map[nr][nc] == '*') {
                            map[nr][nc] = '.';
                            break;
                        }
                        else if (map[nr][nc] == '#') {
                            break;
                        }
                        nr += dr[d];
                        nc += dc[d];
                    }
                }
                else {
                    if (cmd == 'U') d = 0;
                    else if (cmd == 'R') d = 1;
                    else if (cmd == 'D') d = 2;
                    else if (cmd == 'L') d = 3;
                    
                    map[r][c] = tank[d];
                    
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    
                    if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] == '.') {
                        map[r][c] = '.';
                        r = nr;
                        c = nc;
                        map[r][c] = tank[d];
                    }
                }
            }
            
            sb.append("#").append(tc).append(" ");
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }
        }
        
        System.out.print(sb);
    }
}
