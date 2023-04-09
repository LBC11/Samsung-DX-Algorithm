package BinarySearch_10.No_38;

import java.io.*;
import java.util.StringTokenizer;

/*
11446. 사탕 가방

N종류의 사탕이 있고, 각 종류마다 A1,A2,…,AN개의 사탕이 있다. 이 사탕을 가방에 잘 나눠 넣고 싶은데 다음과 같은 조건을 만족해야 한다.
- 가방 안에 정확히 M개의 사탕이 들어 있어야 한다.
- 모든 가방에 들어 있는 사탕 종류의 구성이 같아야 한다.
최대 몇 개의 사탕 가방을 만들 수 있는지 구하는 프로그램을 작성하라.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 두 정수 N, M (1 ≤ N ≤ 100, 1 ≤ M ≤ 1018)가 공백 하나로 구분되어 주어진다.
두 번째 줄에는 N개의 정수 A1,A2,…,AN (1 ≤ Ai ≤ 1018)이 공백 하나로 구분되어 주어진다.

[출력]
각 테스트 케이스마다 최대 몇 개의 사탕 가방을 만들 수 있는지 출력한다.

주요 아이디어
1. 이분 탐색
2. 조건을 만족하는 수 중에서도 최댓값을 구하기 위해서는 멈추면 않고 start = mid + 1 해줘야 한다.
3. 시작점을 0으로 할지 1로 할지에 따라 0 division 이 뜨냐 안 뜨냐가 결정된다.

+ 36, 37, 38 은 같은 문제처럼 보여도 detail 에서 언제 어떻게 반복문을 끝내고 정답을 도출하는지가
  매우 다르다. 이를 중요하게 여기고 공부해라
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static long[] candy;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            long M = Long.parseLong(st.nextToken());

            candy = new long[N];

            // max(end) 를 계산하기 위한 sum
            long max = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                long temp = Long.parseLong(st.nextToken());
                candy[i] = temp;
                max = Math.max(temp, max);
            }

            sb.append("#").append(t).append(" ").append(binarySearch(max, M)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static long binarySearch(long max, long m) {

        long start = 1;
        long end = max;

        while (start <= end) {

            long mid = (start + end) / 2;

            long n = cal(mid);

            // mid 개의 가방을 채우지 못할 경우
            if (n < m) {
                end = mid - 1;
            }

            // 그 외의 경우
            else {
                start = mid + 1;
            }
        }

        // end 를 return 하는 이유
        // 반복문이 종료되는 시점이 start 가 end 보다 커지는 시점인데
        // 이를 만족시키려면 start 와 end 둘다 정답이 되는 수에 있는 상황에서
        // else 문을 만족하여 start = (start + end) / 2 + 1 이 되는 것이다.
        // 그래서 정답에 그대로 위치해있는 end 를 return 해야 한다.
        return end;
    }

    private static long cal(long mid) {

        // 가방이 mid 개일 때 각 candy 를 최대 몇개씩
        // 똑같이 넣을 수 있는지를 더한 개수
        long num = 0;

        for (long l : candy) {
            num += (l / mid);
        }

        return num;
    }
}
