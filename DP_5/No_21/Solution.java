package DP_5.No_21;

import java.io.*;
import java.util.StringTokenizer;

/*

 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static int[][] item;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            item = new int[n][2];
            for(int i=0; i<n; i++) {

                st = new StringTokenizer(br.readLine());

                item[i][0] = Integer.parseInt(st.nextToken());
                item[i][1] = Integer.parseInt(st.nextToken());
            }


        }


        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}
