package 연결리스트.No_6;

class Node {
    public int data;
    public Node prev;
    public Node next;

    public Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class UserSolution {

    private final static int MAX_NODE = 10000;

    private Node[] node = new Node[MAX_NODE];
    private int nodeCnt = 0;
    private Node head;
    private Node tail;

    public Node getNode(int data) {
        node[nodeCnt] = new Node(data);
        return node[nodeCnt++];
    }

    public void init() {

        nodeCnt = 0;
        head = getNode(-1);
        tail = getNode(-2);

        head.next = tail;
        tail.prev = head;
    }

    public void addNode2Head(int data) {

        Node n = getNode(data);

        n.next = head.next;
        head.next = n;

        n.next.prev = n;
        n.prev = head;

    }

    public void addNode2Tail(int data) {

        Node n = getNode(data);

        n.prev = tail.prev;
        tail.prev = n;

        n.prev.next = n;
        n.next = tail;
    }

    public void addNode2Num(int data, int num) {

        Node n = getNode(data);
        Node x = head;

        for(int i=0; i<num-1; i++) {
            x = x.next;
        }

        n.next = x.next;
        x.next = n;

        n.next.prev = n;
        n.prev = x;

    }

    public int findNode(int data) {

        Node x = head;

        int ans=0;

        while(x.next != null && x.next.data != data) {
            x = x.next;
            ans++;
        }

        return ++ans;
    }

    public void removeNode(int data) {

        Node x = head;

        while(x.next != null && x.next.data != data) {
            x = x.next;
        }

        if(x.next != null) {
            x.next = x.next.next;
            x.next.prev = x;
        }

    }

    public int getList(int[] output) {

        Node x = head;

        int size = 0;

        while(x.next != tail) {
            output[size++] = x.next.data;
            x = x.next;
        }

        return size;
    }

    public int getReversedList(int[] output) {
        Node x = tail;

        int size = 0;

        while(x.prev != head) {
            output[size++] = x.prev.data;
            x = x.prev;
        }

        return size;
    }
}
