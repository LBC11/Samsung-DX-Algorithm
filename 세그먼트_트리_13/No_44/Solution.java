package 세그먼트_트리_13.No_44;

import java.io.*;
import java.util.StringTokenizer;
/*
14726. Segment Tree 연습 - 1

길이가 n인 수열 a0, a1, ⋯, an-1 (0 ≤ ai ≤ 109) 에서 아래 두 가지 쿼리를 처리하는 프로그램을 작성하라

•  0 i x    :    ai 를 x로 바꾼다. (0 ≤ i ≤ n - 1, 0 ≤ x ≤ 109)
•  1 l r    :    max(al, al+1, ⋯, ar-1) - min(al, al+1, ⋯, ar-1)를 출력한다. (0 ≤ l < r ≤ n)

[입력]
첫 번째 줄에 테스트 케이스의 수 T 가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 배열의 길이 n(1 ≤ n ≤ 105)과 쿼리의 개수 q(1 ≤ q ≤ 105)가 주어진다.
두 번째 줄에는 배열 a가 주어진다.
세 번째 줄부터 q개 줄에 걸쳐 쿼리가 주어진다.

[출력]
각 테스트 케이스마다 1번 쿼리의 결과를 공백으로 구분하여 출력한다.

주요 아이디어
1. segment tree 이용
2. init 당시 leaf node 의 parent node 를 구할 때 최대값 혹은 최소값을 할당하면 된다

-> 아직도 query 부분 logic 이 어떤 방식으로 이뤄지는지 이해가 안되었다... 공부 더하자!!!
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    private final static int swap = 0;
    private final static int cal = 1;

    // segment tree 의 root node idx 는 1이다.
//    static long[] segment_tree;

    static long[] max_segment_tree;
    static long[] min_segment_tree;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            int q = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());

            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Long.parseLong(st.nextToken());
            }

            init(arr);

            sb.append("#").append(t);

            for (int i = 0; i < q; i++) {

                st = new StringTokenizer(br.readLine());

                switch (Integer.parseInt(st.nextToken())) {

                    case swap:
                        update(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), n);
                        break;

                    case cal:
                        query(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), n);
                        break;
                }
            }

            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static void init(long[] arr) {

//        segment_tree = new long[arr.length * 2];
        max_segment_tree = new long[arr.length * 2];
        min_segment_tree = new long[arr.length * 2];

        for (int i = 0; i < arr.length; i++) {

//            segment_tree[i + arr.length] = arr[i];
            max_segment_tree[i + arr.length] = arr[i];
            min_segment_tree[i + arr.length] = arr[i];
        }

        for (int i = arr.length - 1; i > 0; i--) {
//            segment_tree[i] = segment_tree[i << 1] + segment_tree[i << 1 | 1];

            max_segment_tree[i] = Math.max(max_segment_tree[i << 1], max_segment_tree[i << 1 | 1]);
            min_segment_tree[i] = Math.min(min_segment_tree[i << 1], min_segment_tree[i << 1 | 1]);
        }
    }

    private static void query(int left, int right, int n) {

        long min = Integer.MAX_VALUE;
        long max = 0;

//        long result = 0;

        for (left += n, right += n; left != right; left >>= 1, right >>= 1) {

            // 여기 logic 은 아무리 생각해도 이해가 안된다... 공부가 더 필요해 보인다.
            if ((left & 1) == 1) {

//                result += segment_tree[l++];

                long max_temp = max_segment_tree[left];
                long min_temp = min_segment_tree[left];

                min = Math.min(min_temp, min);
                max = Math.max(max_temp, max);

                left++;
            }
            if ((right & 1) == 1) {

//                result += segment_tree[--r];

                --right;

                long max_temp = max_segment_tree[right];
                long min_temp = min_segment_tree[right];

                min = Math.min(min_temp, min);
                max = Math.max(max_temp, max);
            }
        }

        sb.append(" ").append(max - min);
    }

    private static void update(int idx, int num, int n) {

        idx += n;

        // 해당 idx 의 value 갱신
//        segment_tree[idx] = num;
        max_segment_tree[idx] = num;
        min_segment_tree[idx] = num;

        do {

            // idx 갱신
            idx >>= 1;

            // 해당 idx 의 parent node 도 value 갱신
//            segment_tree[idx] = segment_tree[idx << 1] + segment_tree[idx << 1 | 1];
            max_segment_tree[idx] = Math.max(max_segment_tree[idx << 1],max_segment_tree[idx << 1 | 1]);
            min_segment_tree[idx] = Math.min(min_segment_tree[idx << 1], min_segment_tree[idx << 1 | 1]);

        } while (idx > 0);
    }
}
