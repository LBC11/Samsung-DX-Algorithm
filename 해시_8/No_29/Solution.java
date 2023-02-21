package 해시_8.No_29;

import java.io.*;

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

        System.out.println(book.length());

        int j = 0;
        for (int i = 0; i < book.length(); i++) {

            System.out.println("i: "+i);
            System.out.println("j: "+j);

            // 비교
            if (book.charAt(i) == word.charAt(j)) {
                j++;
            } else {
                i += pi[j-1];
                j = pi[j-1];
            }

            if (j == word.length()) {

                ans++;
                j = pi[j - 1];
                i += pi[j];
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
