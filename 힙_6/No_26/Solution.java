package 힙_6.No_26;

import java.io.*;
import java.util.StringTokenizer;

/*
No. 26 수 만들기

N개의 수 A1,A2,…,AN 과 K가 주어진다. 처음에 X=0, D=1이고, 다음과 같은 작업을 몇 번 반복해서, X의 값을 K로 만들어야 한다.

1. X에 D를 더한다.
2. D에 A1,A2,…,AN 중 하나를 곱한다.

예를 들어, N=2, A=[2,3], K=7 이면, 다음과 같은 방식으로 X=K로 만들 수 있다.
1. X=0, D=1이고 X에 D를 더해 X=1이 된다.
2. D에 A1=2를 곱해 D=2이 된다.
3. D에 A2=3을 곱해 D=6이 된다.
4. X=1, D=6이고 X에 D를 더해 X=7이 된다.

이 때, D에 수를 몇 번 곱하는지는 관계없이, X에 D를 더하는 횟수를 최소화하면 몇 번이 되는지 구하는 프로그램을 작성하라.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 하나의 정수 N(1 <= N <=10)이 주어진다.
두 번째 줄에는 N개의 정수 A1,A2,…,AN (2 <= Ai <= 109)이 공백 하나로 구분되어 주어진다.
세 번째 줄에는 하나의 정수 K(1 <= K <= 109)가 주어진다.

[출력]
각 테스트 케이스마다 X의 값을 K로 만들기 위해 X에 D를 더하는 횟수의 최솟값을 출력한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static int[] num;

    static Node[] pq;
    static int last_idx;

    static void insert(Node node) {

        pq[++last_idx] = node;

        int loc = last_idx;

        // parent idx 가 1이상이고 parent 보다 cnt 가 작다면
        while (loc / 2 > 0 && pq[loc / 2].cnt > pq[loc].cnt) {

            // value swap
            swap(loc / 2, loc);

            // 현재 위치를 parent idx 로 갱신
            loc /= 2;
        }
    }

    static Node peek() {
        return pq[1];
    }

    static Node pop() {

        // return 할 root node
        Node ret = pq[1];

        pq[1] = pq[last_idx--];

        int parent_idx = 1;
        while (parent_idx * 2 <= last_idx) {

            int left_child_idx = parent_idx * 2;
            int right_child_idx = parent_idx * 2 + 1;
            int smallest_idx = parent_idx;

            if (left_child_idx <= last_idx) {

                // cnt 가 더 작거나 cnt 가 같아도 left 가 더 작을 경우 smallest_idx 를 갱신한다.
                if (pq[left_child_idx].cnt < pq[smallest_idx].cnt
                        || (pq[left_child_idx].cnt == pq[smallest_idx].cnt && pq[left_child_idx].left < pq[smallest_idx].left)) {

                    smallest_idx = left_child_idx;
                }
            }

            if (right_child_idx <= last_idx) {

                if(pq[right_child_idx].cnt < pq[smallest_idx].cnt
                        || (pq[right_child_idx].cnt == pq[smallest_idx].cnt && pq[right_child_idx].left < pq[smallest_idx].left))

                    smallest_idx = right_child_idx;

            }

            if (parent_idx != smallest_idx) {

                swap(parent_idx, smallest_idx);

                parent_idx = smallest_idx;
            } else break;
        }

        return ret;
    }

    static void swap(int a, int b) {
        Node temp = pq[a];
        pq[a] = pq[b];
        pq[b] = temp;
    }


    static int solution(int left) {

        insert(new Node(0, left));

        while (true) {

            Node n = pop();

            if (n.left == 0) return n.cnt;

            // 남은 모든 수를 1로 처리하는 경우
            insert(new Node(n.cnt + n.left, 0));

            for (int i = 0; i < num.length; i++) {

                // num 에 들어있는 수를 한번 곱해서 더하는 경우
                insert(new Node(n.cnt + n.left % num[i], n.left / num[i]));
            }
        }
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            int N = Integer.parseInt(br.readLine());
            num = new int[N];

            pq = new Node[100000];

            // root idx = 1 로 설정한다.
            last_idx = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                num[i] = Integer.parseInt(st.nextToken());
            }

            int K = Integer.parseInt(br.readLine());

            sb.append("#").append(t).append(" ").append(solution(K)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}

class Node {

    int cnt;
    int left;

    public Node(int cnt, int left) {
        this.cnt = cnt;
        this.left = left;
    }
}
