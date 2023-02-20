package 힙_6.No_26;

import java.io.*;
import java.util.StringTokenizer;

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
