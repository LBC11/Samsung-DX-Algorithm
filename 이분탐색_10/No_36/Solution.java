package 이분탐색_10.No_36;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static int solution(int[] study, int p) {

        int ans = 0;

        int start = 0;
        int end = 0;

        while(true) {

            if()
        }

        return ans;
    }

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            int[] study = new int[n];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                study[i] = Integer.parseInt(st.nextToken());
            }

            sb.append("#").append(t).append(" ").append(solution(study, p)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
