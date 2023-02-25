package 이분탐색_10.No_38;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static long[] candy;

    public static void main(String[] args) throws IOException {

        int T= Integer.parseInt(br.readLine());
        for(int t=1; t<=T; t++) {

            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            long M = Long.parseLong(st.nextToken());

            candy = new long[N];

            // max(end) 를 계산하기 위한 sum
            long sum = 0;

            st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++) {
                long temp = Long.parseLong(st.nextToken());
                candy[i] = temp;
                sum += temp;
            }

            long max = sum / M;

            sb.append("#").append(t).append(" ").append(binarySearch(max, M)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static long binarySearch(long max, long m) {

        long start = 0;
        long end = max;

        while(start <= end) {

            long mid = (start + end) / 2;
            if(check(mid, m) > 0)
        }
    }

    private static int check(long mid, long m) {

        long num = 0;

        for (long l : candy) {
            num += (l / mid);
        }

        // temp 가 작을 경우 -
        // temp == candle 0
        // temp 가 클 경우 +
        return Long.compare(num, m);
    }
}
