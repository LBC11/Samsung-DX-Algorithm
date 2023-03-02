package 세그먼트_트리_13.No_45;

import java.io.*;
import java.util.StringTokenizer;
/*
14733. Segment Tree 연습 - 2

길이가 n인 수열 a0, a1, ⋯, an-1 (0 ≤ ai ≤ 109) 에서 아래 두 가지 쿼리를 처리하는 프로그램을 작성하라
•  0 i x    :    ai 를 x로 바꾼다. (0 ≤ i ≤ n - 1, 0 ≤ x ≤ 10^9)
•  1 l r    :    ai (l ≤ i < r) 를 번갈아가며 더하고 뺀 값을 출력한다. (0 ≤ l < r ≤ n)
                   범위를 만족하는 i의 개수가 홀수일 경우 al - al+1 + al+2  - ... - ar-2 + ar-1 를 출력하고
                   범위를 만족하는 i의 개수가 짝수일 경우 al - al+1 + al+2  - ... + ar-2 - ar-1 를 출력하라.
[입력]
첫 번째 줄에 테스트 케이스의 수 T 가 주어진다.

각 테스트 케이스의 첫 번째 줄에는 배열의 길이 n(1 ≤ n ≤ 10^5)과 쿼리의 개수 q(1 ≤ q ≤ 10^5)가 주어진다.
두 번째 줄에는 배열 a가 주어진다.
세 번째 줄부터 q개 줄에 걸쳐 쿼리가 주어진다.

[출력]
각 테스트 케이스마다 1번 쿼리의 결과를 공백으로 구분하여 출력한다.

주요 아이디어
1. segment tree 이용
2. arr 을 segment tree 에 옮길 때 홀수면 *-1 을 한다.
3. query 계산시 left 가 홀수면 결과에 *-1 을 한다.
 */
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

        int left_temp = left;

        long result = 0;

        for (left += n, right += n; left != right; left >>= 1, right >>= 1) {

            if ((left & 1) == 1) result += segment_tree[left++];
            if ((right & 1) == 1) result += segment_tree[--right];

        }

        // 시작이 홀수 idx 일 때 result 에 -1 을 곱한다.
        if (left_temp % 2 == 1) result *= -1;

        sb.append(" ").append(result);
    }

    private static void update_tree(int idx, long num, int n) {

        // 홀수 idx 일 때 -1 을 곱해서 할당한다.
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

            // 홀수 idx 일 때 -1 을 곱해서 할당한다.
            if (i % 2 == 0) segment_tree[arr.length + i] = arr[i];
            else segment_tree[arr.length + i] = -arr[i];
        }

        for (int i = arr.length - 1; i > 0; i--) {
            segment_tree[i] = segment_tree[i << 1] + segment_tree[i << 1 | 1];
        }
    }
}
