package Tree_3.No_12;


import java.util.Arrays;
import java.util.HashMap;

/*
9429. Directory

[문제 설명]
컴퓨터 운영체제는 파일 분류를 위해 ‘디렉터리’라는 개념을 사용한다.
디렉터리는 여러 파일들과 다른 디렉터리를 포함하여, 사용자에게 논리적인 파일 그룹을 제공한다.

디렉터리는 ‘/’ 구분 문자를 사용하여, 파일 시스템 내에서 자신의 경로를 표현한다.
‘/’ 는 루트(최상위) 디렉터리를 나타내기도 하며, 상/하위 디렉터리간의 계층 관계를 나타내기도 한다.
예를 들어 “/aa/bb/” 디렉터리 경로는, 루트 디렉터리의 하위에 “aa” 디렉터리가 있고 “aa” 디렉터리의 하위에는 “bb” 디렉터리가 있음을 나타낸다.
디렉터리와 관련된 다양한 기능들이 있지만, 이 문제에서는 아래 5가지 기능만을 구현한다.

- 디렉터리 생성
- 디렉터리 삭제
- 디렉터리 복사
- 디렉터리 이동
- 하위 디렉터리 개수 확인

위와 같은 디렉터리의 기능을 구현하라.

void init(int n)
각 testcase 시작 시, 초기화를 위해 1번 호출되고 루트 디렉터리를 생성한다.

Parameters
n: 이번 testcase에서 루트 디렉터리를 포함하여 생성되는 최대 디렉터리의 개수 (5 ≤ n ≤ 50,000)

void cmd_mkdir(char path[2000], char name[7])
path[] 디렉터리의 하위에 name[] 이름을 가진 새로운 디렉터리를 생성한다.

Parameters
path[2000]: 생성할 디렉터리의 상위 디렉터리의 경로 (1 ≤ length ≤ 1,999)
name[7]: 생성할 디렉터리의 이름 (2 ≤ length ≤ 6)

void cmd_rm(char path[2000])
path[] 디렉터리와 그 모든 하위 디렉터리를 삭제한다.

Parameters
path[2000]: 삭제할 디렉터리의 경로 (1 ≤ length ≤ 1,999)

void cmd_cp(char srcPath[2000], char dstPath[2000])
dstPath[] 디렉터리의 하위에 srcPath[] 의 디렉터리와 그 모든 하위 디렉터리를 복사한다.

Parameters
srcPath[2000]: 복사할 디렉터리의 경로 (1 ≤ length ≤ 1,999)
dstPath[2000]: srcPath[] 디렉터리가 복사 될 위치의 상위 디렉터리 경로 (1 ≤ length ≤ 1,999)

void cmd_mv(char srcPath[2000], char dstPath[2000])
dstPath[] 디렉터리의 하위에 srcPath[] 의 디렉터리와 그 모든 하위 디렉터리를 이동한다.

Parameters
srcPath[2000]: 이동할 디렉터리의 경로 (1 ≤ length ≤ 1,999)
dstPath[2000]: srcPath[] 디렉터리가 이동 될 위치의 상위 디렉터리 경로 (1 ≤ length ≤ 1,999)

int cmd_find(char path[2000])
path[] 디렉터리의 모든 하위 디렉터리 개수를 반환한다.

Parameters
path[2000]: 디렉터리의 경로 (1 ≤ length ≤ 1,999)

Return
path[] 디렉터리의 모든 하위 디렉터리 개수

[제약사항]
1. 각 testcase에서 생성되는 최대 디렉터리의 개수 n 은 5 이상 50,000 이하의 정수이다. (5 ≤ n ≤ 50,000)
2. 디렉터리의 이름은 알파벳 소문자로 구성되며, 길이는 2 이상 6 이하이다. (2 ≤ length ≤ 6)
3. 모든 디렉터리는 그 경로의 길이가 1,999 를 넘지 않는다. (1 ≤ length ≤ 1,999)
4. API에 파라미터로 전달되는 char 배열은 전달 값 이후에 ‘\0’ 값으로 끝난다.
5. 같은 상위 디렉터리를 가지는 디렉터리 간에는 같은 이름을 가지는 경우는 없다.
6. 같은 상위 디렉터리를 가지는 디렉터리는 30 개 이하이다.
7. cmd_cp, cmd_mv 함수 호출 시 dstPath가 srcPath의 하위 디렉터리인 경우는 없다.
8. 함수 호출 시 전달되는 파라미터의 경로는 유효한 경로임이 보장된다.
9. 각 testcase에서 함수 호출 횟수의 총합은 50,000 회 이하이다.
10. 각 testcase에서 cmd_cp 함수의 호출 횟수는 전체 함수 호출 횟수의 10 % 이내이다.

주요 아이디어
1. Linear time 이란 시간 복잡도가 O(n)을 뜻하는데 이 정도만 되어도 성능에 큰 문제가 없다.
이 이상 걸리는 대부분의 방법은 무시해도 좋을 정도이니 code 를 작성할 때 참고해라.
-> 그래서 path 를 따라 내려가는 일, 올라가는 일등은 모두 허용된다. 그 와중에 각각이
다시 n번을 탐색하여 n^2이 되는 것은 문제가 된다.

2. char 소문자는 97~122로 총 26개이다. 이를 bit packing 하면 5bit 안에 표현할 수 있으므로
6의 길이를 갖는 char[]는 bit packing 을 통해 32bit int 하나에 저장할 수 있다.
이를 통해 data 비교를 1번만 할 수 있게된다.

3. child 의 최대 개수가 30개 이하인데, 어떻게 하면 child 를 이름으로 바로 찾을 수 있는가가
주요 성능에 대한 이슈이다. array 혹은 list 로 매번 반복문을 돌리는 것은 비효율적
=> map<Integer, Node> 를 통해 key 값으로는 2에서 이름을 packing 값을 사용하여 O(1)로 접근이
   가능하게 했다.

4. dir 생성에서는 미리 Node[n] 만큼 미리 memory 를 할당해놓는 방식을 사용했다.

5. remove 에서는 memory 의 크기가 이미 충분하기에 parent 와 child 의 연결을 끊는 것으로 삭제되었다고
간주하는게 가능하다. 만약 memory 의 공간이 부족하다면 일일이 Node a = null 을 통해 해제시켜줘야 한다.

6. copy 에서는 제약 상황에서 알 수 있듯이 명령어의 최대 10% 밖에 되지 않아 하나하나 순회하면서 clone 을
만드는 방식이어도 상관없다. (사실 이 방법밖에 없다.)

7. find 에서는 미리 sub dir 의 개수를 저장하는 int count 를 field 에 저장하여 이 값을 단순히 return 해주는
방식이면 O(1)이 된다.

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
        root = getNode(-1);
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

        // path 가 / 면 그냥 root 를 사용하면 된다.
        if(path.length != 1) {

            // path 처음은 / 이고 두번째는 / 일수가 없으며 마지막은 '\0' 이라서 제외했다.
            for (int i = 2; i < path.length - 1; i++) {
                if (path[i] == '/') {

                    // 다음 child 의 idx
                    idx = bitPacking(Arrays.copyOfRange(path, j, i+1));

                    // j 갱신
                    j = i + 1;

                    // n 갱신, 그에 따른 count 갱신
                    n = n.child.get(idx);
                    n.count++;
                }
            }
        }

        n.child.put(now_idx, getNode(now_idx));
        n.child.get(now_idx).parent = n;

    }

    void cmd_rm(char[] path) {

        Node n = searchNode(path);

        int idx = n.idx;

        // 상위 node 의 count 를 갱신할 숫자 그 자신도 삭제되기에 +1 해야 한다.
        int decreased = n.count + 1;

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
        int increased = src.count + 1;

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

        // count 를 갱신할 숫자 그 자신도 포함하기에 + 1
        int amount = src.count + 1;

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

        src.child.forEach((i, node1) ->
                nodeCopy(node1, clone));
    }

    private Node searchNode(char[] path) {

        Node n = root;

        // child node 에 접근하기 위한 idx
        int idx;
        int j = 1;

        // path 가 / 면 그냥 root 를 사용하면 된다.
        if(path.length != 1) {

            // path 처음은 / 이고 두번째는 / 일수가 없으며 마지막은 '\0' 이라서 제외했다.
            for (int i = 2; i < path.length - 1; i++) {
                if (path[i] == '/') {

                    // 다음 child 의 idx
                    idx = bitPacking(Arrays.copyOfRange(path, j, i+1));

                    // j 갱신
                    j = i + 1;

                    // n 갱신
                    n = n.child.get(idx);
                }
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

