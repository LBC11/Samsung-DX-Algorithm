package 해시_8.No_30;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            int[][] student = new int[h][w];
            int[][] teacher = new int[n][m];

            for (int i = 0; i < h; i++) {

                String s = br.readLine();
                for (int j = 0; j < w; j++) {

                    if (s.charAt(j) == 'o') student[i][j] = 0;
                    else student[i][j] = 1;
                }
            }

            for (int i = 0; i < n; i++) {

                String s = br.readLine();
                for (int j = 0; j < m; j++) {

                    if (s.charAt(j) == 'o') teacher[i][j] = 0;
                    else teacher[i][j] = 1;
                }
            }


            sb.append("#").append(t).append(" ").append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
