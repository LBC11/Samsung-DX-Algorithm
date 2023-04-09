package Tree_3.No_9;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

/*
No. 9 [S/W 문제해결 기본] 9일차 - 사칙연산 유효성 검사

사칙연산으로 구성되어 있는 식은 이진 트리로 표현할 수 있다.
아래는 식 “(8/2)*(6-4)”을 이진 트리로 표현한 것이다.
임의의 정점에 연산자가 있으면 해당 연산자의 왼쪽 서브 트리의 결과와 오른쪽 서브 트리의 결과를 사용해서 해당 연산자를 적용한다.
사칙연산 “+, -, *, /”와 양의 정수로만 구성된 임의의 이진 트리가 주어질 때, 이 식의 유효성을 검사하는 프로그램을 작성하여라.
여기서 말하는 유효성이란, 사칙연산 “+, -, *, /”와 양의 정수로 구성된 임의의 식이 적절한 식인지를 확인하는 것으로, 계산이 가능하다면 “1”, 계산이 불가능할 경우 “0”을 출력한다.
(단, 계산이 가능한지가 아닌 유효성을 검사하는 문제이므로 0으로 나누는 경우는 고려하지 않는다. )

[제약 사항]
총 10개의 테스트 케이스가 주어진다.
총 노드의 개수는 200개를 넘어가지 않는다.
트리는 완전 이진 트리 형식으로 주어지며, 노드당 하나의 연산자 또는 숫자만 저장할 수 있다.
노드는 아래 그림과 같은 숫자 번호대로 주어진다.

[입력]
각 테스트 케이스의 첫 줄에는 각 케이스의 트리가 갖는 정점의 총 수 N(1≤N≤200)이 주어진다.
그 다음 N줄에 걸쳐 각각의 정점 정보가 주어진다.
해당 정점에 대한 정보는 해당 정점의 알파벳, 해당 정점의 왼쪽 자식, 오른쪽 자식의 정점번호가 차례대로 주어진다.
정점 번호는 1부터 N 까지의 정수로 구분된다. 입력에서 정점 번호를 매기는 규칙은 위와 같으며, 루트 정점의 번호는 반드시 1이다.
입력에서 이웃한 숫자 또는 연산자, 자식 정점의 번호는 모두 공백으로 구분된다.
위의 예시에서, 숫자 8이 4번 정점에 해당하면 “4 8”으로 주어지고, 연산자 ‘/’가 2번 정점에 해당하면 두 자식이 각각 숫자 ‘8’인 4번 정점과 숫자 ‘2’인 5번 정점이므로 “2 / 4 5”로 주어진다.
총 10개의 테스트 케이스가 주어진다.

[출력]
#부호와 함께 테스트 케이스의 번호를 출력하고, 공백 문자 후 테스트 케이스의 정답을 출력한다.

문제 해결 idea
숫자 다음에는 문자(연산자)가 나와야 한다.
또한 문자 다음에는 숫자가 나와야 한다.
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
            }
            sb.append("#").append(i).append(" ").append(completeBinaryTree.inOrder_calc_heck()).append("\n");
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

    public int inOrder_calc_heck() {

        if(root == null) {
            return 1;
        }

        // 연산자와 피연사자를 담을 그릇
        Stack<Node> stack = new Stack<>();

        // 순회할 node
        Node curr = root;

        // 연산식에서 숫자가 처음에 나와야 하므로 검증할 그 전 단계는 연산자로 한다.
        char before = '/';

        while(curr != null || !stack.isEmpty()) {

            // 가장 왼쪽 node 도착
            while(curr != null) {

                stack.push(curr);
                curr = curr.left;
            }

            // 위의 while 문이 끝나면 null 이다.
            curr = stack.pop();

            // 다음이 mid
            char after = curr.data;

            // 둘다 숫자이거나 문자이면
            if((before >= 48 && after >= 48) || (before < 48 && after < 48)) {

                // 계산 불가능
                return 0;
            } else {

                // before char 갱신
                before = after;
            }

            // right 가 마지막
            curr = curr.right;
        }

        // 모든 node 에서 문자와 숫자가 순서대로 나오면 계산 가능
        return 1;

    }
}

