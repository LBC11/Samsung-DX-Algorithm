package Graph_4.No_15;

import java.io.*;
import java.util.*;

/*
1855. 영준이의 진짜 BFS

영준이는 루트가 있는 트리에서 BFS(Breadth First Search)를 하려고 한다.
트리는 1에서 N까지의 번호가 붙은 N개의 노드로 이루어져 있으며, 1이 루트이자 동시에 탐색 시작점이다.
BFS는 큐를 이용하여 탐색을 하는데, 큐의 가장 앞에 있는 노드를 뽑아 탐색을 하고, 탐색을 하는 노드의 자식들을 작은 번호부터 순서대로 큐의 뒤쪽에 넣는 방식으로 탐색이 진행된다.
이것은 컴퓨터에서 실제로 진행되는 방식이 아니고 영준이가 직접 노드를 방문해야 하기 때문에, BFS를 한다면 노드를 방문하는 순서가 정해질 것이고 영준이는 그 순서를 따라 최단거리로 트리를 이동하여 모든 노드를 탐색한다.
영준이는 과연 몇 개의 간선을 지나고 나서야 탐색을 끝 마칠 수 있을까?

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 자연수 N(1 ≤ N ≤ 105)이 주어진다.
두 번째 줄에는 각 노드의 부모정점을 의미하는 N-1개의 자연수가 공백으로 구분되어 주어진다. 1번 노드는 루트이므로 부모가 없어 생략된다.
i-1(2 ≤ i ≤ N)번째로 주어지는 수는 i번 노드의 부모 pi (1 ≤ pi < i)이다. 즉 i번 노드의 부모의 번호는 i보다 작다.

[출력]
각 테스트 케이스마다 ‘#x ’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
직접 BFS를 하였을 때 지나게 되는 총 간선의 개수를 출력한다.

주요 아이디어
1. 최단 경로는 공통조상을 찾아 거쳐가는 방법이다. -> a 의 높이 + b 의 높이 - 2 * 공통 조상의 높이 = 최단 거리
2. 잘 생각해보면 많은 경로가 반복되어서 계산되므로 dp를 사용해야 제시간안에 풀어낼 수 있다.
3. 경로의 크기가 꽤 크기 때문에 long 으로 결과값을 저장해야 한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static Node[] nodes;

    static Queue<Node> queue;
    static HashMap<Long, Integer> cache;

    // 초기 node 생성 담당
    static void init(int n) {
        nodes = new Node[n + 1];

        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        queue = new LinkedList<>();
        cache = new HashMap<>();
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

        // 둘의 높이가 같다면 나머지는 dp를 통해 해결
        // 찾은 공통 조상의 높이 return
        return dp(high, low);
    }

    static int dp(Node high, Node low) {

        // high, low 둘 다 root 일 때가 cache 에 입력되는 초기값이다.
        if(high == low) return high.h;

        // n 의 범위가 1~100000 이라서 10^6 을 곱한 값을 더해주었다.
        // 그에 따라서 key 도 long 으로
        long key = high.idx * 1000000L + low.idx;
        if(cache.containsKey(key)) return cache.get(key);

        // high, low 갱신
        high = high.parent;
        low = low.parent;

        int result = dp(high, low);

        // 둘의 공통 조상이 root 일 때 바로 위의 parent 가 root 가 아니더라도
        // 재귀를 통해 결국 cache 에 저장되었던 가장 최근의 root 값과 근접한
        // high, low 가 되어 공통조상을 찾게 된다.
        cache.put(key, result);

        return result;
    }

    static long bfs() {

        // 이동 거리
        long distance = 0;

        Node root = nodes[1];
        queue.add(root);

        Node before = root;

        while (!queue.isEmpty()) {

            Node after = queue.poll();

            int h = find_ancestor_height(before, after);

            distance += (after.h + before.h - 2L * h);

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