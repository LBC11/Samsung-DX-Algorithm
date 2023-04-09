package Tree_3.No_11;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
[S/W 문제해결 응용] 3일차 - 공통조상

이진 트리에서 임의의 두 정점의 가장 가까운 공통 조상을 찾고, 그 정점을 루트로 하는 서브 트리의 크기를 알아내는 프로그램을 작성하라.
예를 들어, 위의 이진 트리에서 정점 8과 13의 공통 조상은 정점 3과 1 두 개가 있다.
이 중 8, 13에 가장 가까운 것은 정점 3이고, 정점 3을 루트로 하는 서브 트리의 크기(서브 트리에 포함된 정점의 수)는 8이다.

[입력]
가장 첫 번째 줄에 테스트케이스의 수가 주어진다.
각 케이스의 첫 번째 줄에는 정점의 개수 V(10 ≤ V ≤ 10000)와 간선의 개수 E, 공통 조상을 찾는 두 개의 정점 번호가 주어진다.
각 케이스의 두 번째 줄에는 E개 간선이 나열된다. 간선은 항상 “부모 자식” 순서로 표기된다.
위에서 예로 든 트리에서 정점 5와 8을 잇는 간선은 “5 8”로 표기된다.
정점의 번호는 1부터 V까지의 정수이며, 루트 정점은 항상 1번이다.

[출력]
각 테스트케이스마다 '#t'(t는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고, 가장 가까운 공통 조상의 번호와 그것을 루트로 하는 서브 트리의 크기를 공백으로 구분하여 출력하라.

idea
기존의 tree 구조는 left, right child node 를 두고 위에서 아래로 탐색하는 데 이번 문제에서
 공통 조상을 찾을 때 아래에서 위로 올라가는게 편하기 때문에 parent 주소를 link field 에 추가했다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        Tree tree = new Tree();

        int T = Integer.parseInt(br.readLine());
        for (int i = 1; i <= T; i++) {

            st = new StringTokenizer(br.readLine());

            int vertex_num = Integer.parseInt(st.nextToken());
            int edge_num = Integer.parseInt(st.nextToken());

            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken());

            // 정점 선언
            tree.init(vertex_num);

            st = new StringTokenizer(br.readLine());
            // 간선 연결
            for (int j = 0; j < edge_num; j++) {

                int parent_idx = Integer.parseInt(st.nextToken());
                int child_idx = Integer.parseInt(st.nextToken());

                tree.connect(parent_idx, child_idx);
            }

            int ancestor = tree.find_ancestor(num1, num2);
            int size = tree.getSize(ancestor);

            sb.append("#").append(i).append(" ").append(ancestor).append(" ").append(size).append("\n");
        }


        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}

class Node {

    int data;
    Node parent;
    Node left;
    Node right;

    public Node(int data) {
        this.data = data;
        parent = null;
        left = null;
        right = null;
    }
}

class Tree {

    private Node[] node;

    private int size;

    public void init(int vertex_num) {

        node = new Node[vertex_num + 1];

        // vertex 선언
        for(int i=1; i<=vertex_num; i++) {
            node[i] = new Node(i);
        }
    }

    public void connect(int parent_idx, int child_idx) {

        // parent node 의 left 가 null 이라면
        if(node[parent_idx].left == null) {

            // left 에 child node 주소 저장
            node[parent_idx].left = node[child_idx];
        }

        // 그렇지 않다면
        else {

            // right 에 child node 주소 저장
            node[parent_idx].right = node[child_idx];
        }

        // parent node 와 child node 를 간선으로 연결
        node[child_idx].parent = node[parent_idx];
    }

    public int find_ancestor(int idx1, int idx2) {

        Node n = node[idx1];

        ArrayList<Integer> ancestors = new ArrayList<>();

        // node[1]이 root node
        // null 나올 때까지 idx1 node 의 조상들 저장
        while(n.parent != null) {

            n = n.parent;
            ancestors.add(n.data);
        }

        int ancestor_idx = 0;

        n = node[idx2];
        while(n != null) {

            n = n.parent;
            if(ancestors.contains(n.data)) {
                ancestor_idx = n.data;
                break;
            }
        }

        return ancestor_idx;
    }

    public int getSize(int idx) {

        // size init
        size = 0;

        recursive_size(node[idx]);

        return size;
    }

    private void recursive_size(Node n) {
        size++;
        if(n.left != null) recursive_size(n.left);
        if(n.right != null) recursive_size(n.right);
    }

}