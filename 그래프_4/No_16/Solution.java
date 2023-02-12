package 그래프_4.No_16;

import java.io.*;
import java.util.StringTokenizer;
/*
3304. 최장 공통 부분 수열

주어진 두 문자열의 최대 공통 부분 수열(Longest Common Sequence)의 길이를 계산하는 프로그램을 작성하시오.
예를 들어 "acaykp"와 "capcak"의 경우, 두 문자열의 최대 공통 부분 수열은 "acak"로 길이가 4이다.
최장 공통 부분문자열(Longest Common Substring)을 계산하는 것이 아님에 주의한다.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫째 줄에 두 문자열이 공백을 사이에 두고 주어진다.
각 문자열은 알파벳 소문자로만 구성되어 있음이 보장된다.
각 문자열의 길이는 1,000 이하의 자연수이다.

[출력]
각 테스트 케이스마다 ‘#T’(T는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고, 최대 공통 부분 수열의 길이를 출력한다.

주요 아이디어
점화식을 세워보면
둘의 해당 idx 의 char 가 같다면
if(a[i-1] == b[j-1]) LCS[i,j] = LCS[i-1, j-1] +1

다르다면
LCS[i,j] = Math.max(LCS[i-1,j], LCS[i][j-1]) 라는 것을 알 수 있다.

위의 점화식을 dp + memoization 을 통해 계산하면 된다.
*/

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    // 각 idx 는 s1, s2에서 의 길이를 의미한다.
    static int[][] dp;

    static int LCS(String s1, String s2) {

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {

                // 둘의 문자가 같다면
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {

                    // 각 문자에서 -1 번째 단계까지의 LCS + 1 한 값과 같다.
                    dp[i][j] = dp[i-1][j-1] + 1;
                }

                // 둘의 문자가 다르다면
                else {

                    // 각 문자 중 하나만 -1 번째 단계까지의 LCS 2 개중 가장 큰 값과 같다.
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());

            // 각 문자열의 길이가 최대 1000 이라서
            dp = new int[1001][1001];

            sb.append("#").append(t).append(" ").append(LCS(st.nextToken(), st.nextToken())).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}
