package DivideAndConquer_9.No_35;

import java.io.*;
import java.util.StringTokenizer;

/*
12818. Inversion Counting

[Restrictions]
Execution Time
5 sec (C/C++) / 7 sec (Java) / 14 sec (Python) for 20 test cases combined

Memory Limit
Maximum 256MB is available for heap and stack combined (Note: Maximum 1 MB can be used for stack.)

You are given a permutation A in N length. Suppose elements of A are A[1], A[2], ... , A[N]. If i < j and A[i]> A[j], you say (A[i], A[j]) is inversed. (But, 1 ≤ i < j ≤ N)
Write a program that finds the number of inversed pairs in the given permutation.

[Input]
The first line contains the number of test cases T. (T ≤ 20)
Input consists of different test cases, and their number is given in the first line. Each test case is made of two lines. The first line has N(2 ≤ N ≤ 100,000) denoting permutation length.
The subsequent lines have space-separated N numbers that represent a permutation. Simply put, numbers from 1 to N will be given only once.

[Output]
For each test case, on a single line, print the number of test cases “#(TC)” and the number of inversed pairs.

주요 아이디어
1. 병합 정렬을 통해서 merge 할 때 right 의 num 을 temp 에 넣을 때 mid +1 - l 만큼 inversion 이 있다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static int[] temp;

    static long ans;

    private static void mergeSort(int[] num, int left, int right) {

        // 원소의 숫자가 0 또는 1개이면 정렬할 필요가 없다.
        if (left >= right) return;

        int middle = (left + right) / 2;

        mergeSort(num, left, middle);
        mergeSort(num, middle + 1, right);

        merge(num, left, middle, right);
    }

    private static void merge(int[] num, int left, int middle, int right) {

        int l = left;
        int r = middle + 1;
        int idx = left;

        // l, r 에서 아직 temp 에 넣지 않는 것이 있는 경우
        while (l <= middle || r <= right) {

            // 이미 r 을 모두 집어넣었거나 l 이 r 보다 작은 경우
            if (r > right || (l <= middle && num[l] < num[r])) {
                temp[idx++] = num[l++];
            }

            // 이미 l 을 모두 집어넣었거나 r이 l 보다 작은 경우
            else {

                // 이 경우는 뒤의 idx 가 앞보다 작으므로 inversion 되어있다.
                ans += (middle - l + 1);
                temp[idx++] = num[r++];
            }
        }

        // 정렬된 부분을 다시 num 로 옮겨 담는다.
        for (int i = left; i <= right; i++) {
            num[i] = temp[i];
        }
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            int N = Integer.parseInt(br.readLine());
            int[] num = new int[N];
            temp = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                num[i] = Integer.parseInt(st.nextToken());
            }

            ans = 0;

            mergeSort(num, 0, num.length - 1);

            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
