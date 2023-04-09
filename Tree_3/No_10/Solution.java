package Tree_3.No_10;

import java.io.*;
import java.util.StringTokenizer;

/*
[S/W 문제해결 기본] 9일차 - 사칙연산

사칙연산으로 구성되어 있는 식은 이진 트리로 표현할 수 있다. 아래는 식 “(9/(6-4))*3”을 이진 트리로 표현한 것이다.
임의의 정점에 연산자가 있으면 해당 연산자의 왼쪽 서브 트리의 결과와 오른쪽 서브 트리의 결과에 해당 연산자를 적용한다.
사칙연산 “+, -, *, /”와 양의 정수로만 구성된 임의의 이진 트리가 주어질 때, 이를 계산한 결과를 출력하는 프로그램을 작성하라.
계산 중간 과정에서의 연산은 모두 실수 연산으로 한다.

[입력]
총 10개의 테스트 케이스가 주어진다.
각 테스트 케이스의 첫 줄에는 정점의 개수 N(1≤N≤1000)이 주어진다. 그다음 N 줄에 걸쳐 각 정점의 정보가 주어진다.
정점이 정수면 정점 번호와 양의 정수가 주어지고, 정점이 연산자이면 정점 번호, 연산자, 해당 정점의 왼쪽 자식, 오른쪽 자식의 정점 번호가 차례대로 주어진다.
정점 번호는 1부터 N 까지의 정수로 구분된고 루트 정점의 번호는 항상 1이다.
위의 예시에서, 숫자 4가 7번 정점에 해당하면 “7 4”으로 주어지고, 연산자 ‘/’가 2번 정점에 해당하면 두 자식이 각각 숫자 9인 4번 정점과 연산자 ‘-’인 5번 정점이므로 “2 / 4 5”로 주어진다.

[출력]
각 테스트케이스마다 '#t'(t는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고 사칙연산을 계산한 결과값을 출력한다.
결과값은 소수점 아래는 버리고 정수로 출력한다.

주의할 점
이전 문제와 다르게 complete binary tree 가 아니라는 것에 주의하자
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

                int idx = Integer.parseInt(st.nextToken());
                String data = st.nextToken();

                // 이번 문제는 complete binary tree 가 이니기에
                // parent 와 child 의 연결고리를 설정해줘야 한다.
                // child node 가 있으면
                if (st.hasMoreTokens()) {
                    int leftIdx = Integer.parseInt(st.nextToken());
                    int rightIdx = Integer.parseInt(st.nextToken());

                    completeBinaryTree.insert(idx, data, leftIdx, rightIdx);
                }

                // leaf node 이면
                else {
                    completeBinaryTree.insert(idx, data);
                }
            }
            sb.append("#").append(i).append(" ").append(completeBinaryTree.resultPrint()).append("\n");
        }


        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}

class Node {

    String data;
    Node left;
    Node right;

    public Node() {
        data = null;
        left = null;
        right = null;
    }

    public Node(String data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class CompleteBinaryTree {

    private Node[] node;
    private Node root;

    public void init(int length) {

        node = new Node[length + 1];
    }

    // leaf node 일때
    public void insert(int idx, String data) {

        // node 선언은 이 node 를 child 로 가지는 parent 에서 이미 했다.
        node[idx].data = data;
    }

    // leaf node 가 아닐 때 parent 와 child 의 연결 고리를 설정해줘야 한다.
    public void insert(int idx, String data, int leftIdx, int rightIdx) {

        // root node 선언
        if(idx == 1) {
            node[idx] = new Node(data);

            // 그의 child node 선언
            node[leftIdx] = new Node();
            node[rightIdx] = new Node();

            // parent child 연결
            node[idx].left = node[leftIdx];
            node[idx].right = node[rightIdx];

            root = node[1];
        }

        // parent node 선언
        node[idx].data = data;

        // 그의 child node 선언
        node[leftIdx] = new Node();
        node[rightIdx] = new Node();

        // parent child 연결
        node[idx].left = node[leftIdx];
        node[idx].right = node[rightIdx];
    }

    public int resultPrint() {

        if (root == null) {
            return 1;
        }

        return (int) calc(root);
    }

    public float calc(Node n) {

        // child 가 있으면 node data 가 연산자다
        if(n.left != null) {

            float leftNum = calc(n.left);
            float rightNum = calc(n.right);

            return operation(leftNum, rightNum, n.data.charAt(0));
        }

        // 피연사자면 data return
        return Float.parseFloat(n.data);
    }

    public float operation(float num1, float num2, char operator) {

        switch (operator) {
            case '+':
                return num1 + num2;

            case '-':
                return num1 - num2;

            case '*':
                return num1 * num2;

            case '/':
                return num1 / num2;
        }

        return -1;
    }
}
