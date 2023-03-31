package DP_5.No_21;

import java.io.*;
import java.util.StringTokenizer;

/*
3282. 0/1 Knapsack

민수에게는 1번부터 N 번까지의 번호가 부여된 N(1≤N≤100)개의 물건과 최대 K(1≤K≤1000) 부피만큼을 넣을 수 있는 가방이 있다.
1번 물건부터 N번 물건 각각은 부피  Vi와 가치 Ci 를 가지고 있다. (1≤Vi, Ci≤100)
민수는 물건들 중 몇 개를 선택하여 가방에 넣어서 그 가치의 합을 최대화하려고 한다.
단, 선택한 물건들의 부피 합이 K 이하여야 한다.
민수가 가방에 담을 수 있는 최대 가치를 계산하자.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫째 줄에 물건의 개수와 가방의 부피인 N K가 주어진다.
다음 N개의 줄에 걸쳐서 i번 물건의 정보를 나타내는 부피  Vi와 가치 Ci가 주어진다.

[출력]
각 테스트 케이스마다 가방에 담을 수 있는 최대 가치를 출력한다.

주요 아이디어
1. 그 전 단계의 값이 다음 단계를 계산하는 데 사용된다면
   dp 가 이용된다는 사실을 눈치채야 한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static int[][] item;

    static int dp(int n, int k) {

        int[][] table = new int[n + 1][k + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {

                // i번쨰 item 의 부피를 충족시키지 못해 그 전 단계의 item 만 넣는 것이 가능한 경우
                table[i][j] = table[i - 1][j];

                // i번쨰 item 의 부피를 충족시킬 수 있는 j의 경우
                // item 의 부피만큼 뺐을 때의 i-1, j-item[i][0] 단계의 가치 + item[i][1] 을 합한 경우를
                // 그 전 단계와 비교하여 더 큰 값을 저장한다.
                if (j >= item[i][0]) {
                    table[i][j] = Math.max(table[i - 1][j], table[i - 1][j - item[i][0]] + item[i][1]);
                }
            }
        }

        return table[n][k];
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            item = new int[n + 1][2];
            for (int i = 1; i <= n; i++) {

                st = new StringTokenizer(br.readLine());

                item[i][0] = Integer.parseInt(st.nextToken());
                item[i][1] = Integer.parseInt(st.nextToken());
            }

            sb.append("#").append(t).append(" ").append(dp(n, k)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
