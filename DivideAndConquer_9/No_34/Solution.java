package DivideAndConquer_9.No_34;

import java.io.*;
import java.util.StringTokenizer;
/*
13736. 사탕 분배

나연이는 A개의 사탕을, 다현이는 B개의 사탕을 갖고 있다. 두 사람은 아래와 같은 작업을 정확히 K번 반복하려고 한다.
- 둘 중 사탕의 개수가 더 적은 사람을 X, 더 많은 사람을 Y라고 하자. 단, 두 사람이 같은 개수의 사탕을 갖고 있다면 나연이가 X, 다현이가 Y이다.
- X가 P개의 사탕을, Y가 Q개의 사탕을 갖고 있을 때, Y는 X에게 자신의 사탕 P개를 준다. 결과적으로 X가 가진 사탕은 2P개, Y가 가진 사탕은 Q-P개가 된다.
  작업이 끝나고 난 후, 두 사람이 각각 가지고 있는 사탕의 개수를 A',B'라고 하자. min(A',B')의 값은 얼마일까?

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스는 하나의 줄로 이루어지며, 각 줄에는 세 개의 정수 A,B,K (1≤A,B≤109, 1≤K≤2⋅109)가 공백 하나를 사이로 두고 주어진다.

[출력]
각 테스트 케이스마다, K번의 반복 작업이 끝나고 난 후, 두 사람이 각각 가지고 있는 사탕의 개수를 A',B'라고 할 때, min(A',B' )의 값을 한 줄에 하나씩 출력한다.

풀이
S = X + Y 라고 두면, S는 변하지 않는 상수값이 됩니다.

이를 통해 맨 처음 상태를 (X, S - X) 로 나타냅시다.
i) X <= S - X
(X, S - X) -> (2X, S - 2X)
ii) X > S - X
(X, S - X) -> (2X - S, 2S - 2X)

상태를 (x, y) 라고 했을 때 x값의 변화만 관찰합시다.
i)번째 케이스는 2X가 S를 넘지 않으므로, 2X가 그대로 나온다고 볼 수 있습니다.
ii)번째 케이스는 2X가 S를 넘으므로, 2X에 S를 빼준다고 볼 수 있습니다.

따라서 x값은 X -> 2X (mod S) 라고 볼 수 있습니다.
이를 통해 위 연산을 K번 반복하면 x값은 X -> X * 2^K (mod S) 라고 볼 수 있습니다.

따라서 (x, y)에서 x를 log S 만에 구할 수 있고 이를 통해 y값도 구해낼 수 있습니다.

+
1. modulo 함수 안 logic 에서 되도록이면 재귀적으로 함수 호출을 지양해야 stackOverFlow 를 피할수 있다.
2. int 로는 범위를 넘어갈 수 있으닌 long 을 이용하자.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static long modulo(long s, long k) {

        if(k == 0) return 1;

        long m = modulo(s, k/2);

        long ret = (m * m) % s;

        if(k % 2 == 0) {
            return ret;
        }

        else {
            return (ret * 2) % s;
        }
    }
    static long cal(long x, long s, long k) {

        return (x * modulo(s,k)) % s;
    }

    public static void main(String[] args) throws IOException {

        long T = Integer.parseInt(br.readLine());
        for (long t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());
            long A = Integer.parseInt(st.nextToken());
            long B = Integer.parseInt(st.nextToken());
            long K = Integer.parseInt(st.nextToken());

            long s = A + B;
            long x = Math.min(A,B);

            long x_ = cal(x, s, K);
            long ans = Math.min(s - x_, x_);

            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
