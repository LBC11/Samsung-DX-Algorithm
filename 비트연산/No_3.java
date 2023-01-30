package 비트연산;

import java.io.*;

/*
삼성초등학교에는 영준, 준환, 동한, 하니 이렇게 네 명이 프로그래밍 동아리에 속해있다. 부원 네 명의 이름을 편의상 A, B, C, D라고 하자.
앞으로 N일간의 활동 일정을 정해야 한다.
각 부원은 하루의 활동에 참여를 할지 하지 않을지를 정해야 하며, 어떤 부원이 참여하는지의 경우의 수는 하루에 총 16가지이다.
그런데 아무도 활동에 참여하지 않으면 동아리가 폐부 될 수 있으므로 아무도 참여를 하지 않아서는 곤란하다.
즉 실제로는 15가지 경우가 있다.
동아리 실을 여는 열쇠는 하나밖에 없고 활동이 끝나면 동아리 실을 잠가야 하기 때문에 문을 열어주기 위해 열쇠를 가진 사람은 무조건 활동에 참여해야 한다.
오늘 활동에 참여하는 사람 중에 내일 활동에도 참여하는 사람이 있다면 열쇠를 넘겨줄 수 있다.
첫 번째 날에는 A가 열쇠를 가지고 있다.
모든 활동이 끝난 다음에는 열쇠를 누가 가지고 있어도 상관이 없다.

또한 N일 동안 각 날마다 활동의 책임자가 있어서 이 책임자는 무조건 활동에 참여해야 한다.
N일 동안의 동아리 활동을 할 수 있는 경우의 수를 출력하는 프로그램을 작성하라.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스마다 길이 10,000 이하인 하나의 문자열이 주어진다. 이 문자열은 A, B, C, D로 이루어져 있으며, i번째 문자는 i번째 날의 책임자가 누구인지를 나타낸다.

[출력]
각 테스트 케이스마다 N일 동안의 동아리 활동을 할 수 있는 경우의 수를 출력하는 프로그램을 작성하라.
이 수는 너무 커질 수 있으므로 1,000,000,007로 나눈 나머지를 출력한다.

해결방법: DP + 메모이제이션
 */
public class No_3 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static long divisor = 1000000007;
    static char[] peoples = {'A', 'B', 'C', 'D'};
    static String s;

    static int[][] cases;

    static long solution() {

        // 모든 날들에 대해서
        for(int i=0; i<s.length(); i++) {

            // 오늘 동아리방 활동 담당자
            int n = activity(i);

            // j는 current
            for(int j=1; j<(1<<4); j++) {

                // current 에 동아리방 활동 담당자가 포함되었을 때
                if(responsibility(n,j)) {

                    // k는 previous
                    for(int k=1; k<(1<<4); k++) {

                        // current 와 previous 사이에 교집합이 있을 경우
                        if(subset(j,k)) {

                            // 경우의 수 갱신
                            cases[i+1][j] += cases[i][k];

                            // 큰 수 방지
                            cases[i+1][j] %= divisor;
                        }
                    }
                }
            }
        }

        long ans = 0;

        for(int i=1; i<(1<<4); i++) {
            ans += cases[s.length()][i];
            ans %= divisor;
        }

        return ans;
    }

    // 오늘 동아리방 활동 담당이 몇번째 사람인지
    static int activity(int idx) {
        for (int i = 0; i < 4; i++) {
            if (peoples[i] == s.charAt(idx)) {
                return i;
            }
        }

        return 0;
    }

    // n번째 동아리원이 m에 포함되어 있는지
    static boolean responsibility(int n, int m) {

        // 포함되어있으면 true
        return (m & (1 << n)) >> n == 1;
    }

    // a 와 b 사이에 부분집합이 존재하는지
    static boolean subset(int a, int b) {
        for (int i = 0; i < 4; i++) {
            if (((a & (1 << i)) >> i) == 1 && ((b & (1 << i)) >> i) == 1) return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {

        // t번 반복
        int t = Integer.parseInt(br.readLine());

        for (int i = 1; i <= t; i++) {

            s = br.readLine();
            cases = new int[s.length()+1][16];

            // 첫날 열쇠를 A가 가지고 있음
            cases[0][1] = 1;

            sb.append("#").append(i).append(" ").append(solution()).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
