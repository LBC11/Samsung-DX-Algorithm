package 연결리스트_2.No_6;

class Node {
    public int data;
    public Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class UserSolution {

    private final static int MAX_NODE = 10000;

    private Node[] node = new Node[MAX_NODE];
    private int nodeCnt = 0;
    private Node head;

    public Node getNode(int data) {
        node[nodeCnt] = new Node(data);
        return node[nodeCnt++];
    }

    public void init() {

        // 초기화
        nodeCnt = 0;
        head = getNode(-1);
    }

    public void addNode2Head(int data) {

        Node n = getNode(data);

        // 기존 head 의 next 는 n으로
        n.next = head.next;

        // n의 다음은 기존 head 의 다음으로
        head.next = n;
    }

    // doubly linked 가 아니기에 tail 이 없어서 반복문 사용
    public void addNode2Tail(int data) {

        Node x = head;

        // x 가 null 이 아닐 때까지
        while (x.next != null) {
            x = x.next;
        }

        x.next = getNode(data);
    }

    public void addNode2Num(int data, int num) {

        Node n = getNode(data);
        Node x = head;

        // x 가 num-1 일 때까지
        for (int i = 0; i < num - 1; i++) {
            x = x.next;
        }

        n.next = x.next;
        x.next = n;
    }

    public void removeNode(int data) {

        Node x = head;

        // x.next != null 이것을 조건문에 추가해놓지 않으면
        // memory 문제가 발생한다.
        while (x.next != null && x.next.data != data) {
            x = x.next;
        }

        if(x.next != null) {
            x.next = x.next.next;
        }
    }

    public int getList(int[] output) {

        int size = 0;

        Node x = head;

        // x 가 null 이 아닐 때까지
        while (x.next != null) {
            x = x.next;
            output[size++] = x.data;
        }

        return size;
    }
}
