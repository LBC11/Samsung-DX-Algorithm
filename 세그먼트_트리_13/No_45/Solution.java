package 세그먼트_트리_13.No_45;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    private static final int update = 0;
    private static final int query = 1;

    static long[] segment_tree;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            long[] arr = new long[n];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                arr[i] = Long.parseLong(st.nextToken());
            }

            init(arr);

            sb.append("#").append(t);

            for (int i = 0; i < q; i++) {

                st = new StringTokenizer(br.readLine());

                switch (Integer.parseInt(st.nextToken())) {

                    case update:
                        update_tree(Integer.parseInt(st.nextToken()), Long.parseLong(st.nextToken()), n);
                        break;

                    case query:
                        query_tree(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), n);
                }
            }

            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static void query_tree(int left, int right, int n) {

        long result = 0;

        for (left += n, right += n; left != right; left >>= 1, right >>= 1) {

            if ((left & 1) == 1) result += segment_tree[left++];
            if ((right & 1) == 1) result += segment_tree[--right];
        }

        if (left % 2 == 1) result *= -1;

        sb.append(" ").append(result);
    }

    private static void update_tree(int idx, long num, int n) {

        if (idx % 2 == 1) num *= -1;

        idx += n;

        segment_tree[idx] = num;

        do {
            idx >>= 1;

            segment_tree[idx] = segment_tree[idx << 1] + segment_tree[idx << 1 | 1];
        } while (idx > 0);
    }

    private static void init(long[] arr) {

        segment_tree = new long[arr.length * 2];

        for (int i = 0; i < arr.length; i++) {

            if (i % 2 == 0) segment_tree[arr.length + i] = arr[i];
            else segment_tree[arr.length + i] = -arr[i];

        }

        for (int i = arr.length - 1; i > 0; i--) {
            segment_tree[i] = segment_tree[i << 1] + segment_tree[i << 1 | 1];
        }
    }


}
