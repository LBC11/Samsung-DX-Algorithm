package 이분탐색_10.No_39;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static int[][] ad;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            int L = Integer.parseInt(br.readLine());

            int N = Integer.parseInt(br.readLine());
            ad = new int[N][2];

            int min = Integer.MAX_VALUE;
            int max = 0;
            for (int i = 0; i < N; i++) {

                st = new StringTokenizer(br.readLine());
                int t1 = Integer.parseInt(st.nextToken());
                int t2 = Integer.parseInt(st.nextToken());

                min = Math.min(min, t1);
                max = Math.max(max, t2);

                ad[i][0] = t1;
                ad[i][1] = t2;
            }

            sb.append("#").append(t).append(" ").append(binarySearch(min, max, L)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static int binarySearch(int min, int max, int l) {

        int start = min;
        int end = max;

        while (start <= end) {

            int mid = (start + end) / 2;

        }

        return 0;
    }
}
