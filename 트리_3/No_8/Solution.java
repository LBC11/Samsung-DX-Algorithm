package 트리_3.No_8;

import java.io.*;
import java.util.StringTokenizer;
/*
No. 8 [S/W 문제해결 기본] 9일차 - 중위순회

[제약 사항]
트리는 완전 이진 트리 형식으로 주어지며, 노드당 하나의 문자만 저장할 수 있다.
노드는 아래 그림과 같은 순서로 주어진다.

[입력]
총 10개의 테스트 케이스가 주어진다.
각 테스트 케이스의 첫 줄에는 트리가 갖는 정점의 총 수 N(1≤N≤100)이 주어진다. 그 다음 N줄에 걸쳐 각각의 정점 정보가 주어진다.
정점 정보는 해당 정점의 문자, 해당 정점의 왼쪽 자식, 오른쪽 자식의 정점 번호 순서로 주어진다.
정점 번호는 1부터 N까지의 정수로 구분된다. 정점 번호를 매기는 규칙은 위 와 같으며, 루트 정점의 번호는 항상 1이다.
위의 예시에서,  알파벳 ‘F’가 2번 정점에 해당하고 두 자식이 각각 알파벳 ‘O’인 4번 정점과 알파벳 ‘T’인 5번 정점이므로 “2 F 4 5”로 주어진다.
알파벳 S는 8번 정점에 해당하므로 “8 S” 로 주어진다.

[출력]
#부호와 함께 테스트 케이스의 번호를 출력하고, 공백 문자 후 테스트 케이스의 답을 출력한다.

 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    public static void main(String[] args) throws IOException {


        CompleteBinaryTree completeBinaryTree = new CompleteBinaryTree();

        int T = 10;
        for (int i = 1; i <= T; i++) {

            // n개의 node 삽입
            int n = Integer.parseInt(br.readLine());
            completeBinaryTree.init(n);

            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());

                completeBinaryTree.insert(Integer.parseInt(st.nextToken()), st.nextToken().charAt(0));

                while(st.hasMoreTokens()) {
                    st.nextToken();
                }
            }

            sb.append("#").append(i).append(" ").append(completeBinaryTree.print()).append("\n");

        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}

class Node {

    char data;
    Node left;
    Node right;

    public Node(char data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class CompleteBinaryTree {

    private Node[] node;
    private int nodeCnt;
    private Node root;

    // data 값을 합치기 위한 공간
    StringBuffer s;

    public Node getNode(char data) {
        node[nodeCnt] = new Node(data);
        return node[nodeCnt++];
    }

    public void init(int length) {

        node = new Node[length + 1];

        // complete binary tree 를 이용하기 위해 1부터 시작
        nodeCnt = 1;
    }

    public void insert(int idx, char data) {

        if (idx == 1) {

            // root node 선언
            root = getNode(data);

            return;
        }

        // index 에서 left child node = parent node * 2
        if (idx % 2 == 0) {

            // node 생성 후 할당
            node[idx / 2].left = getNode(data);
        }

        // index 에서 right child node = parent node * 2 + 1
        else {

            // node 생성 후 할당
            node[idx / 2].right = getNode(data);
        }
    }

    public String print() {

        s = new StringBuffer();

        inOrder(root, s);

        return s.toString();
    }

    private void inOrder(Node root, StringBuffer s) {

        if (root != null) {
            inOrder(root.left, s);
            s.append(root.data);
            inOrder(root.right, s);
        }
    }


}