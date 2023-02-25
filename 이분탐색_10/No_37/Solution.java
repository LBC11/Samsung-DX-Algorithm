package 이분탐색_10.No_37;

import java.io.*;

/*
9843. 촛불 이벤트

당신은 프로포즈를 위해 촛불을 삼각형으로 배치하고 있다. 촛불을 K단 크기로 배치하면, 1단에는 K개의 양초, 2단에는 K-1개의 양초, …, K단에는 1개의 양초를 배치해서 총 (K(K+1))/2개의 양초가 필요하다.
당신이 사용할 양초의 개수 N이 주어질 때, 이 양초를 모두 사용하면 몇 단 크기의 촛불 삼각형을 만들 수 있는지 구하여라.

[입력]
첫 번째 줄에 테스트 케이스의 수 TC가 주어진다. 이후 TC개의 테스트 케이스가 새 줄로 구분되어 주어진다. 각 테스트 케이스는 다음과 같이 구성되었다.
첫 번째 줄에 양초 개수가 주어진다. (1≤N≤10^18)

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
각 테스트 케이스 마다 주어진 양초 N개를 모두 사용하여 만들 수 있는 촛불 삼각형의 단수를 출력한다. 만약 삼각형을 만드는 것이 불가능하면 -1을 출력한다.

주요 아이디어
1. 이분 탐색
2. 숫자의 범위 -> long 으로 해야한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            long candle = Long.parseLong(br.readLine());

            sb.append("#").append(t).append(" ").append(binarySearch(candle)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static long binarySearch(long candle) {

        // i*(i+1) 이므로 i*i 보다 커야 하므로
        // 계산을 빠르게 하기 위해 start 지점으로 잡음
        long start = (long) Math.sqrt(candle);

        // 또한 결과가 2*i*i 보다 작다.
        long end = start * 2;

        // = 인지 갱신할 때 mid 인지 아니면 mid -1 인지에 따라
        // 무한 loop 도는 게 결정되니 잘 기억해둬라.
        while(start <= end) {

            long mid  = (start + end) / 2;

            int c = check(mid, candle);
            if(c > 0) {
                end = mid - 1;
            }

            else if(c == 0) {
                return mid;
            }

            else {
                start = mid + 1;
            }

        }

        return -1;
    }

    private static int check(long num, long candle) {

        // temp 가 작을 경우 -
        // temp == candle 0
        // temp 가 클 경우 +
        return Long.compare(num * (num + 1), candle * 2);
    }
}
