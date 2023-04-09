package BitCalculation_1.No_1;

import java.io.*;
import java.util.HashSet;

/*
새로운 불면증 치료제

민석이는 불면증에 걸렸다. 그래서 잠이 안 올 때의 민간요법 중 하나인 양 세기를 하려고 한다.
민석이는 1번 양부터 순서대로 세는 것이 재미없을 것 같아서 N의 배수 번호인 양을 세기로 하였다.
즉, 첫 번째에는 N번 양을 세고, 두 번째에는 2N번 양, … , k 번째에는 kN번 양을 센다.
이렇게 숫자를 세던 민석이에게 잠은 더 오지 않고 다음과 같은 궁금증이 생겼다.
이전에 셌던 번호들의 각 자리수에서 0에서 9까지의 모든 숫자를 보는 것은 최소 몇 번 양을 센 시점일까?

예를 들어 N = 1295이라고 하자.
첫 번째로 N = 1295번 양을 센다. 현재 본 숫자는 1, 2, 5, 9이다.
두 번째로 2N = 2590번 양을 센다. 현재 본 숫자는 0, 2, 5, 9이다.
현재까지 본 숫자는 0, 1, 2, 5, 9이다.
세 번째로 3N = 3885번 양을 센다. 현재 본 숫자는 3, 5, 8이다.
현재까지 본 숫자는 0, 1, 2, 3, 5, 8, 9이다.
네 번째로 4N = 5180번 양을 센다. 현재 본 숫자는 0, 1, 5, 8이다.
현재까지 본 숫자는 0, 1, 2, 3, 5, 8, 9이다.
다섯 번째로 5N = 6475번 양을 센다. 현재 본 숫자는 4, 5, 6, 7이다.
현재까지 본 숫자는 0, 1, 2, 3, 4, 5, 6, 7, 8, 9이다.
5N번 양을 세면 0에서 9까지 모든 숫자를 보게 되므로 민석이는 양 세기를 멈춘다.

주요 아이디어
1. 각 자리의 숫자는 % 연산자를 이용해서 구한다.
2. 중복은 set 을 이용해 해결한다.
3. set 에 0~9 까지 각 숫자가 모두 채워질떄까지 계속한다.
 */
public class No_1 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static int solution(int n) {

        // 숫자를 저장할 공간
        // 중복 되는 값이 삽입되지 않기에 set 사용
        HashSet<Integer> numbers = new HashSet<>();

        // 몇 번 양을 셌는지 count
        int result = 0;

        int i = 1;

        // set 에 저장된 숫자가 10개(0~9)가 될 때까지 반복
        while (numbers.size() < 10) {
            int temp = n * i++;

            // temp 가 0이 될 때까지 반복
            while (temp > 0) {

                // temp 의 각 자릿수 set 에 추가
                numbers.add(temp % 10);

                // temp 갱신
                temp /= 10;
            }

            result++;
        }

        return result * n;
    }

    public static void main(String[] args) throws IOException {

        // t번 반복
        int t = Integer.parseInt(br.readLine());
        for (int i = 1; i <= t; i++) {
            int n = Integer.parseInt(br.readLine());
            sb.append("#").append(i).append(" ").append(solution(n)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
