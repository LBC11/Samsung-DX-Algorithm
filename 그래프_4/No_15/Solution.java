package 그래프_4.No_15;

import java.io.*;
import java.util.*;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static Node[] nodes;

    static Queue<Node> queue;

    // 초기 node 생성 담당
    static void init(int n) {
        nodes = new Node[n + 1];

        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        queue = new LinkedList<>();
    }

    // parent, child 연결
    static void connect(int n, String s) {
        st = new StringTokenizer(s);

        for (int i = 2; i <= n; i++) {

            // i번째 node 의 parent node idx
            int parent_idx = Integer.parseInt(st.nextToken());

            // parent, child 연결
            nodes[parent_idx].child.add(i);
            nodes[i].parent = nodes[parent_idx];

            // child node 의 높이는 parent node 의 +1 이다.
            nodes[i].h = nodes[parent_idx].h + 1;

        }
    }

    // 두 노드의 공통 조상의 높이 찾기
    static int find_ancestor_height(Node before, Node after) {

        Node high;
        Node low;

        if (before.h >= after.h) {
            high = before;
            low = after;
        } else {
            low = before;
            high = after;
        }

        // 서로의 높이가 같을 때까지 갱신
        // 둘의 조상이 같은 사람이려면 높이가 같아야 한다.
        while (high.h > low.h) {
            high = high.parent;
        }

        // 둘의 공통 조상이 나올 때까지 둘다 갱신
        while (high != low) {
            high = high.parent;
            low = low.parent;
        }

        // 찾은 공통 조상의 높이 return
        return high.h;
    }

    static int bfs() {

        // 이동 거리
        int distance = 0;

        Node root = nodes[1];
        queue.add(root);

        Node before = root;

        while (!queue.isEmpty()) {

            Node after = queue.poll();

            int h = find_ancestor_height(before, after);

            distance += (after.h + before.h - 2 * h);

            if (!after.child.isEmpty()) {
                for (Integer i : after.child) {

                    queue.add(nodes[i]);
                }
            }

            before = after;
        }

        return distance;
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            int n = Integer.parseInt(br.readLine());

            init(n);

            connect(n, br.readLine());

            sb.append("#").append(t).append(" ").append(bfs()).append("\n");

        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}

class Node {

    int idx;

    // 이 node 가 tree 에서 몇층인지 저장 장소
    int h;
    Node parent;
    ArrayList<Integer> child;

    public Node(int idx) {
        this.idx = idx;
        this.h = 0;
        this.parent = null;
        this.child = new ArrayList<>();
    }
}