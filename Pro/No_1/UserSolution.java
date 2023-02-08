package Pro.No_1;

class UserSolution {

    /*
    No. 1 [Pro] 병사관리

    void init()
    각 테스트 케이스의 처음에 호출된다.
    테스트 케이스의 시작 시 고용된 병사는 없다.

    void hire(int mID, int mTeam, int mScore)
    고유번호가 mID, 소속팀이 mTeam, 평판 점수가 mScore 인 병사를 고용한다.
    한 테스트 케이스 내에서 동일한 mID를 가진 병사가 여러 번 고용되는 경우는 없음이 보장된다.

    Parameters
    mID : 고유번호 (1 ≤ mID ≤ 100,000)
    mTeam : 소속팀 (1 ≤ mTeam ≤ 5)
    mScore : 평판 점수 (1 ≤ mScore ≤ 5)

    void fire(int mID)
    고유번호가 mID인 병사를 해고한다.
    fire() 함수 호출 시, 고유번호가 mID인 병사가 고용되어 있음이 보장된다.

    Parameters
    mID : 고유번호 (1 ≤ mID ≤ 100,000)

    void updateSoldier(int mID, int mScore)
    고유번호가 mID인 병사의 평판 점수를 mScore 로 변경한다.
    고유번호가 mID인 병사가 고용되어 있음이 보장된다.

    Parameters
    mID : 고유번호 (1 ≤ mID ≤ 100,000)
    mScore : 평판 점수 (1 ≤ mScore ≤ 5)

    void updateTeam(int mTeam, int mChangeScore)
    소속팀이 mTeam 인 병사들의 평판 점수를 모두 변경한다.
    소속팀이 mTeam 인 병사가 한 명 이상 고용되어 있음이 보장된다.

    updateTeam() 함수에서의 평판 점수 변경은 아래의 규칙에 따른다.
    ‘변경 전 평판 점수 + mChangeScore’가 5보다 클 경우, 평판 점수를 5로 변경한다.
    ‘변경 전 평판 점수 + mChangeScore’가 1보다 작을 경우, 평판 점수를 1로 변경한다.
    그 외의 경우, 평판 점수를 ‘변경 전 평판 점수 + mChangeScore’로 변경한다.

    Parameters
    mTeam : 소속팀 (1 ≤ mTeam ≤ 5)
    mChangeScore : 평판 점수의 변화량 (-4 ≤ mChangeScore ≤ 4)

    int bestSoldier(int mTeam)
    소속팀이 mTeam 인 병사들 중 평판 점수가 가장 높은 병사의 고유번호를 반환한다.
    평판 점수가 가장 높은 병사가 여러 명일 경우, 고유번호가 가장 큰 병사의 고유번호를 반환한다.
    소속팀이 mTeam 인 병사가 한 명 이상 고용되어 있음이 보장된다.

    Parameters
    mTeam : 소속팀 (1 ≤ mTeam ≤ 5)

    Returns
    평판 점수가 가장 높은 병사의 고유번호

    [제약사항]
    1. 각 테스트 케이스 시작 시 init() 함수가 호출된다.
    2. 각 테스트 케이스에서 hire() 함수의 호출 횟수는 100,000 이하이다.
    3. 각 테스트 케이스에서 fire() 함수의 호출 횟수는 100,000 이하이다.
    4. 각 테스트 케이스에서 updateSoldier() 함수의 호출 횟수는 100,000 이하이다.
    5. 각 테스트 케이스에서 updateTeam() 함수의 호출 횟수는 100,000 이하이다.
    6. 각 테스트 케이스에서 bestSoldier() 함수의 호출 횟수는 100 이하이다.
     */

    /*
    나의 분석

    1. team - mID
    병사들의 고용과 해고가 잦기 때문에 삽입, 삭제가 빠르고
    자유로운 크기 조절이 가능한 LinkedList 자료구조로 결정

    2. mID - mScore
    따로 mID, mScore 를 field 로 가지는 Member class 선언 후 사용하는 방법
    -> primitive type 이 reference type 보다 성능이 우수하기에 보류

    index 는 mID, mScore 는 value 를 저장한 int[100001] 를 사용하는 방법
    -> array 의 access 는 O(1) 이기에 메모리를 희생하고 성능을 높일수 있다고 생각해 결정
    + 그렇다고 team - mID 까지 포함한 int[][] 를 사용하면 bestSoldier 를 찾을 때
      성능이 너무 저하되기에 기각

    3. updateTeam
    score 가 필요한 때는 bestSoldier 를 찾을 때 뿐이다. 그렇다면 updateTeam 에서
    점수를 일일이 갱신할 것이 아니라 int[] changes 를 이용하여 변화량만 저장해놓고
    나중에 사용하면 된다.
     */

    /*
    우수 풀이 사례 분석
    1. 제약사항들을 분석했을 때 bestSoldier(search) 만 O(n) 이고 나머지
       fun 에서는 O(1) 이어야 한다.

    2. fire 의 경우 mID 를 node 의 index 로 바로 사용할 경우 O(1) 로
       구현 가능하다.

    3. DoublyLinkedList 와 점수를 idx 로 사용하여 묶는 방식을 사용하면
       updateTeam O(1) 로 구현 가능하다.

    4. updateSoldier 는 원래의 용병을 fire 하고 다시 hire 하는 방식이면
       똑같이 O(1) 으로 구현 가능하다.
     */

    private Node[] nodes;

    private Node[][] head;
    private Node[][] tail;

    public void init() {

        nodes = new Node[100001];

        // 첫번째 idx 는 team, 두번째 idx 는 score
        head = new Node[6][6];
        tail = new Node[6][6];

        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                head[i][j] = new Node(-1, -1);
                tail[i][j] = new Node(-1, -1);

                head[i][j].next = tail[i][j];
                tail[i][j].prev = head[i][j];
            }
        }
    }

    public void hire(int mID, int mTeam, int mScore) {

        Node n = getNode(mID, mTeam);
        Node t = tail[mTeam][mScore];

        // n 을 mTeam 의 mScore 번째 list tail 앞에 연결한다.
        n.prev = t.prev;
        n.prev.next = n;

        n.next = t;
        t.prev = n;
    }

    public void fire(int mID) {

        Node n = nodes[mID];

        // n 앞 뒤의 연결
        n.prev.next = n.next;
        n.next.prev = n.prev;

        // 연결 끊기
        n.next = null;
        n.prev = null;
    }

    public void updateSoldier(int mID, int mScore) {

        int mTeam = nodes[mID].mTeam;

        // fire, hire 둘 다 O(1) 이므로 이 함수도 O(1)이 된다.
        fire(mID);
        hire(mID, mTeam, mScore);
    }

    public void updateTeam(int mTeam, int mChangeScore) {

        // score 증가일 경우
        if (mChangeScore > 0) {
            for (int i = 5; i >= 1; i--) {
                int after = i + mChangeScore;

                // 점수가 5 보다 클 때는 5로 변경한다.
                if (after > 5) {
                    after = 5;
                }

                // before 보다 after 의 idx 크기가 클 때
                // 변경을 멈춘다.
                if (i == after) continue;

                Node beforeHead = head[mTeam][i];
                Node beforeTail = tail[mTeam][i];

                Node afterTail = tail[mTeam][after];

                // after 의 tail prev 와 before 의 head next 연결
                afterTail.prev.next = beforeHead.next;
                beforeHead.next.prev = afterTail.prev;

                // after 의 tail 이 before tail 과 연결
                afterTail.prev = beforeTail.prev;
                beforeTail.prev.next = afterTail;

                // before 의 head 와 tail 연결
                beforeHead.next = beforeTail;
                beforeTail.prev = beforeHead;
            }
        }

        // score 감소일 경우
        else {
            for (int i = 1; i < 6; i++) {
                int after = i + mChangeScore;

                // 점수가 1 보다 작을 때는 1로 변경한다.
                if (after < 1) after = 1;

                // before 보다 after 의 idx 크기가 클 때
                // 변경을 멈춘다.
                if (after == i) continue;

                Node beforeHead = head[mTeam][i];
                Node beforeTail = tail[mTeam][i];

                Node afterTail = tail[mTeam][after];

                // after 의 tail prev 와 before 의 head next 연결
                afterTail.prev.next = beforeHead.next;
                beforeHead.next.prev = afterTail.prev;

                // after 의 tail 이 before tail 과 연결
                afterTail.prev = beforeTail.prev;
                beforeTail.prev.next = afterTail;

                // before 의 head 와 tail 연결
                beforeHead.next = beforeTail;
                beforeTail.prev = beforeHead;
            }
        }
    }

    public int bestSoldier(int mTeam) {

        int ans = 0;

        for(int i=5; i>=1; i--) {

            Node n = head[mTeam][i].next;

            while(n != tail[mTeam][i]) {

                if(n.mID > ans) ans = n.mID;

                n = n.next;
            }

            // ans 가 0이 아니면 현재 최고 score 에서 가장 큰 수가 ans 이다.
            if(ans != 0) {

                return ans;
            }
        }

        return -1;
    }

    private Node getNode(int mID, int mTeam) {
        nodes[mID] = new Node(mID, mTeam);
        return nodes[mID];
    }
}

class Node {

    int mID;
    int mTeam;

    Node next;
    Node prev;

    public Node(int mID, int mTeam) {
        this.mID = mID;
        this.mTeam = mTeam;
        this.next = null;
        this.prev = null;
    }
}