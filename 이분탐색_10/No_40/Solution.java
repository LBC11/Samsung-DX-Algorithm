package 이분탐색_10.No_40;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        int T= Integer.parseInt(br.readLine());
        for(int t=1; t<=T; t++) {

            sb.append("#").append(t).append(" ").append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
