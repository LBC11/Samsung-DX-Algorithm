package 해시_8.No_28;

import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;
/*
2948. 문자열 교집합

문자열 집합은 알파멧 소문자로 이루어진 문자열들로 구성된 집합을 의미한다.
예를 들어 {"aba", "cdefasad", "wefawef"}은 문자열 3개로 구성된 한 개의 문자열 집합이다.
입력으로 2개의 문자열 집합이 주어졌을 때에, 두 집합에 모두 속하는 문자열 원소의 개수를 출력하는 프로그램을 작성하시오.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스마다 첫 번째 줄에 두 집합의 원소의 갯수를 나타내는 두 자연수 N M(1≤N, M≤105)이 주어진다.
둘째 줄에는 첫 번째 집합의 원소 문자열들이 공백을 사이에 두고 주어진다.
셋째 줄에는 두 번째 집합의 원소 문자열들이 공백을 사이에 두고 주어진다.
각 문자열은 소문자 알파벳으로만 구성되며, 길이가 1 이상 50 이하임이 보장된다.
한 집합에 같은 문자열이 두 번 이상 등장하지 않음이 보장된다.

[출력]
각 테스트 케이스마다 첫째 줄에 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력한 다음, 공백을 하나 사이에 두고 해당 테스트 케이스의 답을 출력한다.

주요 아이디어
1. HashSet 을 이용하면 간단하게 풀린다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        int T= Integer.parseInt(br.readLine());
        for(int t=1; t<=T; t++) {

            int ans = 0;

            st = new StringTokenizer(br.readLine());

            // 첫번째 집합 문자열 개수
            int n = Integer.parseInt(st.nextToken());

            // 두번째 집합 문자열 개수
            int m = Integer.parseInt(st.nextToken());

            HashSet<String> first = new HashSet<>();

            st = new StringTokenizer(br.readLine());
            for(int i=0; i<n; i++) {
                first.add(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for(int i=0; i<m; i++) {
                if(first.contains(st.nextToken())) ans++;
            }

            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
