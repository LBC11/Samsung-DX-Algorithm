package LinkedList_2.No_4;

import java.io.*;
import java.util.StringTokenizer;

/*
암호문3

0 ~ 999999 사이의 수를 나열하여 만든 암호문이 있다.
암호문을 급히 수정해야 할 일이 발생했는데, 이 암호문은 특수 제작된 처리기로만 수정이 가능하다.
이 처리기는 다음과 같이 3개의 기능을 제공한다.

1. I(삽입) x, y, s : 앞에서부터 x의 위치 바로 다음에 y개의 숫자를 삽입한다. s는 덧붙일 숫자들이다.[ ex) I 3 2 123152 487651 ]
2. D(삭제) x, y : 앞에서부터 x의 위치 바로 다음부터 y개의 숫자를 삭제한다.[ ex) D 4 4 ]
3. A(추가) y, s : 암호문의 맨 뒤에 y개의 숫자를 덧붙인다. s는 덧붙일 숫자들이다. [ ex) A 2 421257 796813 ]

위의 규칙에 맞게 작성된 명령어를 나열하여 만든 문자열이 주어졌을 때, 암호문을 수정하고, 수정된 결과의 처음 10개 숫자를 출력하는 프로그램을 작성하여라.

[입력]
첫 번째 줄 : 원본 암호문의 길이 N ( 2000 ≤ N ≤ 4000 의 정수)
두 번째 줄 : 원본 암호문
세 번째 줄 : 명령어의 개수 ( 250 ≤ M ≤ 500 의 정수)
네 번째 줄 : 명령어
위와 같은 네 줄이 한 개의 테스트 케이스이며, 총 10개의 테스트 케이스가 주어진다.

[출력]
#기호와 함께 테스트 케이스의 번호를 출력하고, 공백 문자 후 수정된 암호문의 처음 10개 항을 출력한다.

[제약 사항]
실행 시간 60ms 이하

주요 아이디어
1. LinkedList 직접 구현
 */
public class No_4 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();


    public static void main(String[] args) throws IOException {

//        System.setIn(new FileInputStream("C:/Users/LBC/Downloads/input.txt"));

        UserSolution userSolution = new UserSolution();

        int T = 10;
        for (int test_case = 1; test_case <= T; test_case++) {

            // 원본 길이
            int length = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            userSolution.init(length, st);

            // 명령어 길이
            length = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < length; i++) {

                char cmd = st.nextToken().charAt(0);
                switch (cmd) {
                    case 'I':
                        userSolution.addNode2Num(st);
                        break;

                    case 'D':
                        userSolution.removeNode(st);
                        break;

                    case 'A':
                        userSolution.addNode2Tail(st);
                        break;
                }
            }

            sb.append("#").append(test_case);

            int[] output = new int[10];
            userSolution.getList(output);

            for (int i : output) {
                sb.append(" ").append(i);
            }
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();

    }
}

class Node {

    int data;
    Node next;
    Node prev;

    public Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

class UserSolution {

    // 이렇게 미리 max_node 개수를 해놓고 사용하는 이유는
    // 이 방식이 memory 미리 할당해서 더 빠르게 Node 를 초기화
    // 할 수 있어서이다.
    private final static int MAX_NODE = 10000;
    private Node[] node = new Node[MAX_NODE];

    private int nodeCnt = 0;
    private Node head;
    private Node tail;

    public Node getNode(int data) {
        node[nodeCnt] = new Node(data);
        return node[nodeCnt++];
    }

    public void init(int length, StringTokenizer st) {

        nodeCnt = 0;

        head = getNode(-1);
        tail = getNode(-2);

        // 순회할 Node
        Node x = head;

        // 마지막을 제외하면 그 전 Node 와 연결
        for (int i = 0; i < length - 1; i++) {

            Node n = getNode(Integer.parseInt(st.nextToken()));

            x.next = n;
            n.prev = x;

            x = x.next;
        }

        // 마지막은 tail 까지 연결
        Node n = getNode(Integer.parseInt(st.nextToken()));

        x.next = n;
        n.prev = x;

        n.next = tail;
        tail.prev = n;
    }

    public void addNode2Tail(StringTokenizer st) {

        int t = Integer.parseInt(st.nextToken());

        Node x = tail.prev;

        // t-1번, prev Node 랑 연결
        for (int i = 0; i < t - 1; i++) {

            Node n = getNode(Integer.parseInt(st.nextToken()));

            x.next = n;
            n.prev = x;

            x = x.next;
        }

        // 마지막은 tail 까지 연결
        Node n = getNode(Integer.parseInt(st.nextToken()));

        x.next = n;
        n.prev = x;

        n.next = tail;
        tail.prev = n;
    }

    public void addNode2Num(StringTokenizer st) {

        int location = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        Node x = head;

        // insert 할 위치 찾기
        for (int i = 0; i < location; i++) {
            x = x.next;
        }

        Node x_next = x.next;

        // t-1번 Node 삽입
        for (int i = 0; i < t - 1; i++) {

            Node n = getNode(Integer.parseInt(st.nextToken()));

            x.next = n;
            n.prev = x;

            x = x.next;
        }

        // 마지막은 tail 까지 연결
        Node n = getNode(Integer.parseInt(st.nextToken()));

        x.next = n;
        n.prev = x;

        n.next = x_next;
        x_next.prev = n;

    }

    public void removeNode(StringTokenizer st) {

        int location = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        Node x = head;

        // delete 할 위치 찾기
        for (int i = 0; i < location; i++) {
            x = x.next;
        }

        // delete 할 범위 설정
        Node temp = x;
        for (int i = 0; i < t; i++) {
            temp = temp.next;
        }

        // 범위 안의 모든 Node 건너뛰기
        x.next = temp.next;
        x.next.prev = x;
    }

    public void getList(int[] output) {

        Node x = head;

        for (int i = 0; i < 10; i++) {
            output[i] = x.next.data;
            x = x.next;
        }

    }
}
