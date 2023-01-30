package 모의테스트;

import java.io.*;
import java.util.StringTokenizer;

/*
문제 요약
    주어진 N보다 작으면서 X, Y 두개의 숫자로 이루어진 수 중 가장 큰 수를 구하시오.
    1 <= N <= 10^100000, 0<= X < Y <=9

1. 실패이유 분석
    N의 범위가 1~10^100000 이므로 for 문으로 일일이 뒤에 오는 y를
    더하는 것에 너무 많은 시간을 소모한다. 문자열을 빠르게 repeat
    할 수 있는 방법이 필요하다.

    repeat() 함수는 java 11 version 부터 가능해서 불가능
    new String(new char[n]).replace("\0", "반복할 단어") 사용

    case: 1200 0 4 에서 반례가 만들어짐. Generator 에서
    result 를 return 하기 전에 index 0의 value 가 0이면 삭제

    case: 1202 3 9 에서 반례가 만들어짐. result 의 자릿수를 N보다
    1 작게 해야하는데 뺴놓음.

 */

public class test2_Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static String Generator(String N, int x, int y) {

        // 선물할 수가 없는 경우
        if (N.length() == 1) {
            if(x > Integer.parseInt(N) || (x == 0 && y > Integer.parseInt(N))) {
                return "-1";
            }
        }

        int length = N.length();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int num = N.charAt(i) - '0';

            if (num > y) {

                // 남은 모든 자릿수가 y이다.
                result.append(new String(new char[length - i]).replace("\0", String.valueOf(y)));

                break;
            }

            else if (num == y) {

                // 현재 위치의 자릿수가 y이다.
                result.append(y);
            }

            // num 이 x와 y 사이의 수일 경우
            else if (num > x) {

                // 현재 위치의 자릿수가 x이고
                result.append(x);

                // 남은 모든 자릿수가 모두 y이다.
                result.append(new String(new char[length - i - 1]).replace("\0", String.valueOf(y)));

                break;
            }

            else if (num == x) {

                // 현재 위치의 자릿수가 x이다.
                result.append(x);
            }

            // x가 num 보다 클 경우
            else {

                // 지금까지 완성한 result 의 index
                int idx = i;

                // idx 가 0이상이어야 한다.
                while(true) {

                    // 지금의 위치가 제일 큰 자리일 경우
                    if (idx == 0) {

                        // result 의 길이가 N의 길이보다 1 작아짐
                        // 모든 자릿수가 y이다.
                        result.replace(0, result.length(), new String(new char[length - 1]).replace("\0", String.valueOf(y)));

                        break;
                    }

                    // 그렇지 않을 경우 이전 idx 의 value 확인
                    else idx--;

                    // 이전 idx 의 value 가 y일 경우
                    if (result.charAt(idx) - '0' == y) {

                        // 기존의 y와 그 뒤의 자릿수를 "x"로 교체
                        result.replace(idx, result.length(), x + new String(new char[length - idx - 1]).replace("\0", String.valueOf(y)));

                        break;
                    }

                    // 이전 idx 의 value 가 x일 경우
                    if (result.charAt(idx) - '0' == x) {

                        // 반복문 재 실행
                        continue;
                    }
                }

                //
                break;
            }
        }

        if(result.charAt(0) == '0') result.deleteCharAt(0);

        return result.toString();
    }



    public static void main(String[] args) throws IOException {

        // t번 반복
        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            // N은 Int 나 Long 의 범위를 넘을 수 있기에 String 으로 받는다.
            String N = st.nextToken();
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            sb.append("#").append(i + 1).append(" ").append(Generator(N, x, y)).append("\n");

        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
