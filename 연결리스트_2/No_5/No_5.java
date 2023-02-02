package 연결리스트_2.No_5;

import java.io.*;
import java.util.StringTokenizer;

/*
수열 편집

N개의 10억 이하 자연수로 이뤄진 수열이 주어진다. 이 수열은 완성된 것이 아니라 M번의 편집을 거쳐 완성된다고 한다.
완성된 수열에서 인덱스 L의 데이터를 출력하는 프로그램을 작성하시오.
다음은 미완성 수열과 편집의 예이다.

인덱스
0 1 2 3 4

수열
1 2 3 4 5

I 2 7 -> 2번 인덱스 앞에 7을 추가하고, 한 칸 씩 뒤로 이동한다.

인덱스
0 1 2 3 4 5

수열
1 2 7 3 4 5

D 4 -> 4번 인덱스 자리를 지우고, 한 칸 씩 앞으로 이동한다.

인덱스
0 1 2 3 4

수열
1 2 7 3 5

C 3 8 -> 3번 인덱스 자리를 8로 바꾼다.

인덱스
0 1 2 3 4

수열
1 2 7 8 5

만약 편집이 끝난 후 L이 존재하지 않으면 -1을 출력한다.

[입력]
첫 줄에 테스트케이스의 수 T가 주어진다. 1<=T<=50
다음 줄부터 테스트 케이스의 별로 첫 줄에 수열의 길이 N, 추가 횟수 M, 출력할 인덱스 번호 L이 주어지고, 다음 줄에 수열이 주어진다.
그 다음 M개의 줄에 걸쳐 추가할 인덱스와 숫자 정보가 주어진다. 5<=N<=1000, 1<=M<=1000, 6<=L<=N+M
각 I, D, C 명령에서 입력 받는 인덱스의 위치가 존재하지 않는 불가능한 경우는 입력으로 들어오지 않는다.

[출력]
각 줄마다 "#T" (T는 테스트 케이스 번호)를 출력한 뒤, 답을 출력한다.
 */
public class No_5 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        UserSolution userSolution = new UserSolution();

        // t번 반복
        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {

            // N, M, L 정보 입력
            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());

            // 처음의 수열 정보
            st = new StringTokenizer(br.readLine());
            userSolution.init(N, st);

            for (int j = 0; j < M; j++) {

                // cmd + 실행에 필요한 정보
                st = new StringTokenizer(br.readLine());

                char cmd = st.nextToken().charAt(0);
                switch (cmd) {
                    case 'I':
                        userSolution.addNode2Num(st);
                        break;

                    case 'D':
                        userSolution.removeNode(st);
                        break;

                    case 'C':
                        userSolution.replaceNode(st);
                        break;
                }
            }

            sb.append("#").append(i + 1).append(" ").append(userSolution.getData(L)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
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

    public void addNode2Num(StringTokenizer st) {

        int location = Integer.parseInt(st.nextToken());

        Node x = head;

        // insert 할 위치 찾기
        for (int i = 0; i < location; i++) {
            x = x.next;
        }

        // 집어넣을 Node 생성
        Node n = getNode(Integer.parseInt(st.nextToken()));

        n.next = x.next;
        x.next = n;

        n.next.prev = n;
        n.prev = x;

    }

    public void removeNode(StringTokenizer st) {

        int location = Integer.parseInt(st.nextToken());

        Node x = head;

        // delete 할 위치 찾기
        for (int i = 0; i < location; i++) {
            x = x.next;
        }

        // 범위 안의 모든 Node 건너뛰기
        x.next = x.next.next;
        x.next.prev = x;
    }

    public void replaceNode(StringTokenizer st) {

        int location = Integer.parseInt(st.nextToken());

        Node x = head;

        // delete 할 위치 찾기
        for (int i = 0; i < location; i++) {
            x = x.next;
        }

        // data 갱신
        x.next.data = Integer.parseInt(st.nextToken());
    }

    public int getData(int L) {

        Node x = head;

        int i = 0;
        while (x.next != tail && i < L) {
            x = x.next;
            i++;
        }

        if (x.next == tail) return -1;
        else return x.next.data;

    }
}