package 트리_3.No_12;


import java.util.Arrays;
import java.util.HashMap;

/*
주요 아이디어
1. Linear time 이란 시간 복잡도가 O(n)을 뜻하는데 이 정도만 되어도 성능에 큰 문제가 없다.
이 이상 걸리는 대부분의 방법은 무시해도 좋을 정도이니 code 를 작성할 때 참고해라.
-> 그래서 path 를 따라 내려가는 일, 올라가는 일등은 모두 허용된다. 그 와중에 각각이
다시 n번을 탐색하여 n^2이 되는 것은 문제가 된다.



2. char 소문자는 97~122로 총 26개이다. 이를 bit packing 하면 5bit 안에 표현할 수 있으므로
6의 길이를 갖는 char[]는 bit packing 을 통해 32bit int 하나에 저장할 수 있다.
이를 통해 data 비교를 1번만 할 수 있게된다.


 */
class UserSolution {

    private final static int NAME_MAXLEN = 6;
    private final static int PATH_MAXLEN = 1999;

    private Node[] node;
    private Node root;
    private int nodeCnt;


    public Node getNode(int idx) {
        node[nodeCnt] = new Node(idx);
        return node[nodeCnt++];
    }

    void init(int n) {

        nodeCnt = 0;
        node = new Node[n];

        // root node 생성
        root = getNode(0);
    }

    void cmd_mkdir(char[] path, char[] name) {

        // mkdir 할 때 count 를 일일이 ++ 해야하기에
        // searchNode 사용 x
        Node n = root;

        // count 갱신
        n.count++;

        // 새롭게 만들 idx
        int now_idx = bitPacking(name);

        int idx;
        int j = 1;

        if(path.length == 1)

        // path 처음은 / 이고 두번째는 / 일수가 없으며 마지막은 '\0' 이라서 제외했다.
        for (int i = 2; i < path.length - 1; i++) {
            if (path[i] == '/') {

                // 다음 child 의 idx
                idx = bitPacking(Arrays.copyOfRange(path, j, i));

                // j 갱신
                j = i + 1;

                // n 갱신, 그에 따른 count 갱신
                n = n.child.get(idx);
                n.count++;
            }
        }

        n.child.put(now_idx, getNode(now_idx));
        n.child.get(now_idx).parent = n;

    }

    void cmd_rm(char[] path) {

        Node n = searchNode(path);

        int idx = n.idx;

        // 상위 node 의 count 를 갱신할 숫자
        int decreased = n.count;

        // 삭제할 node 와 parent 연결 끊기

        // source node 는 기존의 list 와 연결이 끊어진다.
        // 그러면 count 를 갱신하기 위해 다시 search node 를 써야 한다.
        // 이를 방지하기 위해 parent node 가 먼저 되고 child node 와 연결을 끊는다.
        n = n.parent;
        n.child.get(idx).parent = null;
        n.child.remove(idx);

        while (n != null) {

            // count 갱신
            n.count -= decreased;

            // node 갱신
            n = n.parent;
        }

    }

    void cmd_cp(char[] srcPath, char[] dstPath) {

        // search src
        Node src = searchNode(srcPath);

        // search dst
        Node dst = searchNode(dstPath);

        // dst 상위 node 들의 count 갱신 시작

        // 순회할 node
        Node n = dst;

        // 상위 node 의 count 를 갱신할 숫자
        int increased = src.count;

        while (n != null) {

            // count 갱신
            n.count += increased;

            // node 갱신
            n = n.parent;
        }

        dst = searchNode(dstPath);

        // 복사 시작
        nodeCopy(src, dst);

    }

    void cmd_mv(char[] srcPath, char[] dstPath) {

        // search src
        Node src = searchNode(srcPath);

        // count 를 갱신할 숫자
        int amount = src.count;

        // 순회할 node
        Node n = src;

        n = n.parent;

        while (n != null) {

            // count 갱신
            n.count -= amount;

            // node 갱신
            n = n.parent;
        }

        // src node 를 와 기존의 parent 와 연결 끊기
        src.parent.child.remove(src.idx);
        src.parent = null;

        // search dst
        Node dst = searchNode(dstPath);

        // dst 상위 node 들의 count 갱신 시작

        // 순회할 node
        n = dst;

        while (n != null) {

            // count 갱신
            n.count += amount;

            // node 갱신
            n = n.parent;
        }

        dst = searchNode(dstPath);

        // dst node 의 child 로 저장
        dst.child.put(src.idx, src);
        src.parent = dst;

    }

    int cmd_find(char[] path) {

        return searchNode(path).count;
    }

    private void nodeCopy(Node src, Node dst) {

        Node clone = getNode(src.idx);
        clone.idx = src.idx;
        clone.count = src.count;
        clone.parent = dst;
        dst.child.put(src.idx, clone);

        dst.child.forEach((i, node1) ->
                nodeCopy(node1, clone));
    }

    private Node searchNode(char[] path) {

        Node n = root;

        int j = 1;

        // child node 에 접근하기 위한 idx
        int idx;

        // path 처음은 / 이고 두번째는 / 일수가 없으며 마지막은 '\0' 이라서 제외했다.
        for (int i = 2; i < path.length - 1; i++) {
            if (path[i] == '/') {

                // 다음 child 의 idx
                idx = bitPacking(Arrays.copyOfRange(path, j, i));

                // j 갱신
                j = i + 1;

                // n 갱신
                n = n.child.get(idx);

            }
        }

        return n;
    }

    private int bitPacking(char[] name) {

        int ans = 0;

        int num = 0;
        for (int i = 0; i < (name.length - 1) * 5; i++) {

            int j = i % 5;

            // packing 할 숫자
            if (j == 0) {
                num = name[i / 5] - 'a';
            }

            // num 의 j번째 bit 값이 1이면 ans 에 해당 bit 값 더하기
            // 최대 6개의 char 를 저장해야 하니까 해당하는 bit 는 j가 아닌 i이다.
            if ((num & (1 << j)) != 0) ans += (1 << i);
        }

        return ans;
    }
}

class Node {

    int idx;
    int count;
    Node parent;
    HashMap<Integer, Node> child;

    public Node(int idx) {
        this.idx = idx;
        this.count = 0;
        this.parent = null;
        this.child = new HashMap<>();
    }
}

