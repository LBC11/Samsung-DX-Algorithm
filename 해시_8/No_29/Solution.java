package 해시_8.No_29;

import java.io.*;

/*
4038. [Professional] 단어가 등장하는 횟수

독서광 동철이는 책을 정말 꼼꼼히 읽는다. 그 증거로, 책에서 어떤 단어가 몇 번 등장하는지 물어보면 정확하게 그 답을 맞춰내는 신기한 능력이 있다.
그런데, 특출난 능력이 있으면 누군가는 시샘을 하게 마련이다.
동철이의 친구 영수는 동철이의 이런 능력을 의심하고 있었지만, 도저히 그 답이 맞는지 세어볼 수가 없어 당신에게 도움을 요청하였다.
영수의 궁금증을 해소해주기 위하여, 책의 내용 B가 주어질 때 특정 단어 S가 등장하는 횟수를 알아내어라.
책의 내용에서 특정 단어가 등장하는 부분이 중첩될 수도 있음에 유의하여라.
예를 들어, B="ABABA"이고 S="ABA"이면 2번 등장하는 것으로 간주한다.

[입력]
첫 줄에 테스트케이스의 개수 T가 주어진다. (1 ≤ T ≤ 20)
각 테스트 케이스의 첫 번째 줄에 책의 내용 B가 주어진다.
책의 내용은 알파벳 소문자와 대문자, 그리고 숫자로만 이루어지고, 길이는 1 이상 500,000 이하이다.
각 테스트 케이스의 두 번째 줄에 찾고자 하는 단어 S가 주어진다.
찾고자 하는 단어는 알파벳 소문자와 대문자, 그리고 숫자로만 이루어지고, 길이는 1 이상 100,000 이하이다.

[출력]
각 테스트케이스마다 한 줄에 걸쳐, 테스트케이스 수 “#(TC) “를 출력하고, 단어가 등장하는 횟수를 출력한다.

주요 아이디어
1. kmp 알고리즘 이용
2. pi 배열 -> pi[i]는 주어진 문자열의 0~i 까지의 부분 문자열 중에서 prefix == suffix가 될 수 있는 부분 문자열 중에서 가장 긴 것의 길이
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static int[] pi;

    static void getPI(String s) {

        pi = new int[s.length()];

        for (int i = 1; i < s.length(); i++) {

            // 전 단계에 일치한 idx 의 다음 idx
            int j = pi[i - 1];
            while (j > 0) {

                // i 번째 와 j 가 일치한다면
                if (s.charAt(i) == s.charAt(j)) break;

                // 그 전 단계의 idx 로 j 갱신
                j = pi[j - 1];
            }

            // 값 갱신
            if (s.charAt(i) == s.charAt(j)) pi[i] = j + 1;

                // p[i-1]~0 까지의 idx 중 같은게 없다면 일치하는 suffix prefix 가 없다는 의미
            else pi[i] = 0;
        }
    }

    static int kmp(String book, String word) {

        getPI(word);

        int ans = 0;

        // s 를 순회할 idx
        int j = 0;
        for (int i = 0; i < book.length(); i++) {

            // 비교
            if (book.charAt(i) == word.charAt(j)) {

                // 일치할시 j 또한 +1. i는 위 for 문 조건식에서 이뤄진다.
                j++;
            } else {

                // j = 0 일 때 j-1 로 인해 outOfIndex error 방지
                if (j > 0) {

                    // 이미 일치하는 부분이 있으면 pi 배열을 통해 이미 일치한 부분을 제외한다.
                    j = pi[j - 1];

                    // 현재의 i번째 char 를 다시 비교해야 해서
                    i--;
                }
            }

            if (j == word.length()) {

                ans++;

                // pi 배열을 통해 이미 일치한 부분을 제외한다.
                j = pi[j - 1];
            }
        }

        return ans;
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            String book = br.readLine();
            String word = br.readLine();

            sb.append("#").append(t).append(" ").append(kmp(book, word)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
