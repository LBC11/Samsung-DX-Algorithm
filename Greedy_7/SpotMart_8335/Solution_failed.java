package Greedy_7.SpotMart_8335;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
스팟마트

인재개발원 스팟마트에서 과자를 무료로 나눠주는 행사를 진행하고 있다!
스팟마트에는 N 봉지의 과자가 좌에서 우로 나열되어 있으며, 이 중 i번째 봉지는 Ai 개의 조각을 가지고 있다.
추가적으로 M개의 봉지가 더 제공되며, 이 중 i번째 봉지는 Bi 개의 조각을 가지고 있다.
당신은 좌에서 우로 나열되어 있는 N 봉지의 과자 사이에 M개의 봉지를 아무 곳에나 (시작점, 끝점, 봉지 사이) 끼워 넣을 수 있다.
이렇게 되면 N+M 개의 봉지가 좌에서 우로 나열되며, 그 중 초기의 N 봉지는 상대적 순서를 유지하는 형태가 될 것이다.
당신은 이렇게 만들어 놓은 리스트를 좌에서 우로 순서대로 걸어가면서 뽑아간다.
리스트에 있는 과자를 고를 수도 있고, 안 고를 수도 있지만, 행사의 규칙에 의하면 과자 한 봉지를 가져갔다면 그 다음 과자 봉지는 절대 가져갈 수 없다.
다른 말로 하면, 리스트에서 연속된 과자를 고를 수 없다.
가장 많은 과자 조각을 가져갈 수 있는 방법은 무엇일까?

[입력]
첫 번째 줄에 테스트 케이스의 수 TC가 주어진다.
이후 TC 개의 테스트 케이스가 새 줄로 구분되어 주어진다.

각 테스트 케이스는 다음과 같이 구성되었다.
첫 번째 줄에는 N이 주어진다. (1 ≤ N ≤ 3,000)
이후 N개의 줄에 Ai가 주어진다. (1 ≤ Ai  ≤ 100,000)
N+2번째 줄에는 M이 주어진다. (0 ≤ M ≤ 100)
이후 M개의 줄에 Bi가 주어진다. (1 ≤ Bi  ≤ 100,000)

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
각 테스트 케이스마다 한 줄씩, 최대로 가져갈 수 있는 과자의 개수를 출력하라.

실패원인 분석
1. 사용, 아직, 사용의 경우 가운데의 것을 사용하면 m 을 두개 사용해야 하는데
   현재의 logic 에서는 이것을 counting 하는 것이 없다.
   -> array 를 boolean[] 에서 int[] 로 바꿔서 해봤지만 결과는 같았다... 다른 방법 강구하자
 */
public class Solution_failed {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            PriorityQueue<Node> pq_n = new PriorityQueue<>(Comparator.comparingInt(o -> -o.value));

            int n = Integer.parseInt(br.readLine());
            for (int i = 0; i < n; i++) {
                pq_n.add(new Node(i, Integer.parseInt(br.readLine())));
            }

            // 양 옆에 얼마나 사용한 수가 있는지
            int[] array = new int[n];

            PriorityQueue<Integer> pq_m = new PriorityQueue<>(Comparator.comparingInt(o -> -o));

            int m = Integer.parseInt(br.readLine());
            for (int i = 0; i < m; i++) {
                pq_m.add(Integer.parseInt(br.readLine()));
            }

            int cnt = 0;
            int num;
            if ((n + m) % 2 == 0) num = (n + m) / 2;
            else num = (n + m) / 2 + 1;

            Node max_node = getMaxN(pq_n);
            int max_m = getMaxM(pq_m);

            int sum = 0;
            while (cnt < num) {

                // max_node 의 value 를 더하는 경우
                if (max_node.value <= max_m) {

                    // sum 갱신
                    sum += max_m;

                    // m에서 다음으로 큰 수
                    max_m = getMaxM(pq_m);

                    // m에서 남은 수 갱신
                    m--;
                }

                // max_m 의 value 를 더하는 경우
                else {
                    int idx = max_node.idx;

                    // 해당 idx value 아직 더한 sum 에 더한적이 없다면
                    if (array[idx] == 0) {

                        // sum 갱신
                        sum += max_node.value;

                        // n에서 다음으로 큰 수
                        max_node = getMaxN(pq_n);

                        // 해당 idx 와 얖 옆의 idx 사용 불가 표시
                        if(idx > 0) array[idx - 1]++;
                        array[idx]++;
                        if(idx < n-1) array[idx + 1]++;
                    }

                    // 해당 idx 가 false 라도 m 이 0보다 크다면
                    // 중간에 m의 수 1개를 넣는 것으로 해당 idx value 사용 가능
                    // 각 idx 는 한 번씩만 pq 에 저장되어 있으므로
                    // idx 가 중복되어서 사용될 일은 없다
                    else if(m > array[idx]) {

                        // m이 하나밖에 안 남은 경우는 현재의 max_m 을 사용해야
                        // 하므로 값을 갱신한다.
                        if(m == 1) max_m = getMaxM(pq_m);

                        // 남은 m 개수 갱신
                        m -= array[idx];

                        // sum 갱신
                        sum += max_node.value;

                        // n에서 다음으로 큰 수
                        max_node = getMaxN(pq_n);

                        // 해당 idx 와 얖 옆의 idx 사용 불가 표시
                        if(idx > 0) array[idx - 1]++;
                        array[idx]++;
                        if(idx < n-1) array[idx + 1]++;
                    }

                    // m도 0이고 해당 idx 도 사용할 수 없다면 건너뛴다.
                    else {

                        max_node = getMaxN(pq_n);
                        continue;
                    }
                }
                cnt++;
            }

            sb.append("#").append(t).append(" ").append(sum).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static Node getMaxN(PriorityQueue<Node> pq) {
        if (pq.isEmpty()) return new Node(-1, -1);
        else return pq.poll();
    }

    static int getMaxM(PriorityQueue<Integer> pq) {
        if (pq.isEmpty()) return -1;
        else return pq.poll();
    }
}

class Node {

    int idx;
    int value;

    public Node(int idx, int value) {
        this.idx = idx;
        this.value = value;
    }
}