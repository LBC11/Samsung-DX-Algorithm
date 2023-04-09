package Greedy_7.SpotMart_8335;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;

/*
8935. 스팟마트

인재개발원 스팟마트에서 과자를 무료로 나눠주는 행사를 진행하고 있다!
스팟마트에는 N 봉지의 과자가 좌에서 우로 나열되어 있으며, 이 중 i번째 봉지는 Ai 개의 조각을 가지고 있다.
추가적으로 M개의 봉지가 더 제공되며, 이 중 i번째 봉지는 Bi 개의 조각을 가지고 있다.
당신은 좌에서 우로 나열되어 있는 N 봉지의 과자 사이에 M개의 봉지를 아무 곳에나 (시작점, 끝점, 봉지 사이) 끼워 넣을 수 있다.
이렇게 되면 N+M 개의 봉지가 좌에서 우로 나열되며, 그 중 초기의 N 봉지는 상대적 순서를 유지하는 형태가 될 것이다.
당신은 이렇게 만들어 놓은 리스트를 좌에서 우로 순서대로 걸어가면서 뽑아간다.
리스트에 있는 과자를 고를 수도 있고, 안 고를 수도 있지만, 행사의 규칙에 의하면 과자 한 봉지를 가져갔다면 그 다음 과자 봉지는 절대 가져갈 수 없다.
다른 말로 하면, 리스트에서 연속된 과자를 고를 수 없다.
가장 많은 과자 조각을 가져갈 수 있는 방법은 무엇일까?

[입력]
첫 번째 줄에 테스트 케이스의 수 TC가 주어진다.
이후 TC 개의 테스트 케이스가 새 줄로 구분되어 주어진다.

각 테스트 케이스는 다음과 같이 구성되었다.
첫 번째 줄에는 N이 주어진다. (1 ≤ N ≤ 3,000)
이후 N개의 줄에 Ai가 주어진다. (1 ≤ Ai  ≤ 100,000)
N+2번째 줄에는 M이 주어진다. (0 ≤ M ≤ 100)
이후 M개의 줄에 Bi가 주어진다. (1 ≤ Bi  ≤ 100,000)

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
각 테스트 케이스마다 한 줄씩, 최대로 가져갈 수 있는 과자의 개수를 출력하라.

주요 아이디어
1. m개의 과자 봉지에서 버릴 때는 제일 적은 것을 가져갈 때는 제일 많은 것을 얻는 방식으로
   greedy 하게 선택한다.
2. dp 를 이용해서 중복 계산 되는 경우의 수를 제거한다.
 */

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static int[][][] snacks = new int[3002][102][102];
    static Integer[] N;
    static Integer[] M;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            int n = Integer.parseInt(br.readLine());

            N = new Integer[n];

            for (int i = 0; i < n; i++) {
                N[i] = Integer.parseInt(br.readLine());
            }

            int m = Integer.parseInt(br.readLine());

            M = new Integer[m];

            for (int i = 0; i < m; i++) {
                M[i] = Integer.parseInt(br.readLine());
            }

            Arrays.sort(M, Collections.reverseOrder());

            for(int i = 0; i <= n; i++) {
                for(int j = 0; j <= m; j++) {
                    Arrays.fill(snacks[i][j], -1);
                }
            }

            sb.append("#").append(t).append(" ").append(dp(0, 0, 0)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static int dp(int n, int m, int skip) {

        if(n > N.length || m + skip > M.length) return 0;
        if(snacks[n][m][skip] != -1) return snacks[n][m][skip];

        int ret = 0;

        // n에서 하나를 pick 하고 skip 할 것도 n에서 고르는 경우
        if(n < N.length) ret = Math.max(ret, dp(n + 2, m, skip) + N[n]);

        // n에서 1개를 skip 만 하는 경우
        if(n < N.length) ret = Math.max(ret, dp(n + 1, m, skip));

        // n에서 하나를 pick 하고 skip 은 m에서 고르는 경우
        if(n < N.length && m + skip < M.length) ret = Math.max(ret, dp(n + 1, m, skip + 1) + N[n]);

        // m에서 1개를 skip 만 하는 경우
        if(m + skip < M.length) ret = Math.max(ret, dp(n, m, skip + 1));

        // m에서 하나를 pick 하고 skip 은 n에서 고르는 경우
        if(n < N.length && m + skip < M.length) ret = Math.max(ret, dp(n + 1, m + 1, skip) + M[m]);

        // m에서 하나를 pick 하고 skip 도 m에서 고르는 경우
        if(m + skip + 1 < M.length) ret = Math.max(ret, dp(n, m + 1, skip + 1) + M[m]);

        return snacks[n][m][skip] = ret;
    }
}