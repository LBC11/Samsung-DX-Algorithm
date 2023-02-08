package Code_Battle_2;

import java.util.HashMap;

/*
No. 2 [Pro] 삼국지게임

[문제 설명]
당신은 어린 시절 삼국지 게임 매니아였다. 어린 시절 추억을 살려 삼국지 게임을 만들어보기로 한다.
구현하려는 기능은 구체적으로 아래와 같다.

-     어떤 군주와 다른 군주와의 동맹 (ally)
-     어떤 군주와 그 동맹 군주들이 다른 군주의 영토를 공격 (attack)
-     병사 모집 (recruit)

전체 영토는 N x N 영토로 구성되어 있다.
각 영토에는 영토를 다스리는 군주가 있으며, 군주의 이름에 중복은 없으며, 모든 군주는 단 하나의 영토를 통치한다.
초기에는 군주들은 동맹이나 적대관계가 없다. 각 영토에는 병사들이 있다.

군주들은 서로 동맹을 한다.
동맹을 하면 전투 시 함께 공격하고, 함께 방어를 할 수 있다.
두 군주들이 동맹을 하면 서로의 모든 동맹까지 함께 동맹을 맺는다.
동맹을 맺을 때, 동맹을 맺는 두 군주를 포함한 서로의 모든 동맹 군주 간 적대관계가 있는 경우 동맹을 맺지 않는다.
동맹을 맺을 때 두 군주와 그 동맹들의 적대관계는 새로운 동맹에 그대로 유지가 된다.

삼국지 게임의 핵심은 전투이다. 전투는 동맹과 동맹 간의 전투다.
공격을 받는 군주가 공격하는 군주의 동맹에 속해 있는 경우에 전투는 발생하지 않는다.
전투는 공격하는 군주의 또는 그의 동맹 (이하 군주의 동맹) 영토가 피 공격 영토에 인접해 있을 경우에 만 발생한다.

전투가 발생하는 경우 공격하는 동맹의 각 군주들은 방어하는 각 군주들과 서로 적대관계가 된다.
실제 전투가 일어나지 않는 경우는 적대관계가 되지 않는다.
전투는 공격하는 군주의 동맹들이 함께 공격을 하고, 공격을 받는 군주의 동맹도 함께 방어를 한다.
공격하는 인접 동맹들은 자신이 가진 병사의 절반을 공격 대상 영토에 보내 함께 공격을 하고,
방어를 하는 군주의 인접 동맹에서도 병사의 절반씩을 보내어 함께 방어를 한다.

만약 공격한 병사의 수가 방어한 병사의 수보다 많다면 공격은 성공한다.
영토가 함락 되었으므로 패배한 군주는 처형되고, 공격을 지휘한 장수가 해당 영토의 새로운 군주가 된다.
새로운 군주의 동맹과 적대관계는 공격한 군주의 동맹 및 적대 관계를 그대로 승계한다.
새로운 군주의 병사의 수는 공격하고 남은 병사의 수가 된다.

방어하는 쪽의 병사의 수가 공격하는 쪽의 병사의 수보다 많거나 같으면 공격은 실패한다.
방어하는 군주의 병사의 수는 방어하고 남은 병사의 수가 된다.

아래 API 설명을 참조하여 각 함수를 구현하라.
※ 아래 함수 signature는 C/C++에 대한 것으로 다른 언어에 대해서는 제공되는 Main과 User Code를 참고하라.

아래는 User Code 부분에 작성해야 하는 API 의 설명이다.

void init(int N, int mSoldier[][], char mMonarch[][][])
각 테스트 케이스의 처음에 호출된다.
전체 영토는 N x N의 격자 모양으로 이루어져 있다.
mSoldier 은 각 영토의 병사의 수이다.
mMonarch 는 각 영토의 군주 이름이다.
군주 이름은 알파벳 소문자로 이루어져 있으며, 길이는 4 이상 10 이하의 문자열이다.
초기에는 모든 군주들은 동맹관계도 적대관계도 없다.

__Parameters
_____N : 전체 영토의 크기 (4 ≤ N ≤ 25, 16 ≤ N x N ≤ 625)
_____mSoldier : 각 영토의 병사의 수 (4 ≤ mSoldier[][] ≤ 200)
_____mMonarch : 각 영토의 군주의 이름 (4 ≤ mMonarch[][]의 길이 ≤ 10)

void destroy()
각 테스트 케이스의 마지막에 호출된다.
빈 함수로 두어도 채점에는 영향을 주지 않는다.

int ally(char mMonarchA[], char mMonarchB[])
군주 mMonarchA 의 동맹들이 군주 mMonarchB 의 동맹들과 동맹을 맺는다.
군주 mMonarchA 와 군주 mMonarchB 가 동일 하거나 이미 동맹관계이면 -1을 반환한다.
군주 mMonarchA 의 동맹과 군주 mMonarchB 의 동맹 간에 적대관계가 있으면 -2를 반환한다.
위의 두 경우가 아닌 경우 동맹관계가 맺어지고, 1을 반환한다.
각 군주 이름은 알파벳 소문자로 이루어져 있으며, 길이는 4 이상 10 이하의 문자열이다.
mMonarchA 와 mMonarchB 는 현재 군주임이 보장된다.

__Parameters
_____mMonarchA : 군주의 이름 (4 ≤ 길이 ≤ 10)
_____mMonarchB : 군주의 이름 (4 ≤ 길이 ≤ 10)

__Returns
_____동맹의 결과 (이미 동맹관계이면 -1, 적대관계가 있으면 -2, 성공하면 1)

int attack(char mMonarchA[], char mMonarchB[], char mGeneral[])
군주 mMonarchA 와 동맹들이 군주 mMonarchB 의 영토를 공격한다.
공격을 지휘하는 장수는 mGeneral 이다.
군주 mMonarchA 와 군주 mMonarchB 가 동맹관계 이면 -1을 반환하고, 전투는 일어나지 않는다.
군주 mMonarchA 의 영토 또는 동맹 영토가 군주 mMonarchB 의 영토와 인접하지 않다면 -2을 반환하고, 전투는 일어나지 않는다.
전투가 발생하면 군주 mMonarchA 의 동맹과 군주 mMonarchB 의 동맹은 서로 적대관계가 된다.
전투가 발생하면 군주 mMonarchB 의 영토에 인접한 군주 mMonarchA 를 포함한 모든 동맹들은 보유한 병사의 절반을 보내 함께 공격한다.
군주 mMonarchB 의 영토에 인접한 군주 mMonarchB 의 모든 동맹들도 보유한 병사의 절반을 mMonarchB 의 영토로 보내 방어를 돕는다.
보내는 병사 계산시 소수점은 버린다.
공격하는 병사의 수가 0명이라도 전투가 발생한 것이다.
전투 시 병사들은 상대 병사와 1:1로 싸워 함께 전사한다.
전투의 결과는 남은 병사로 결정한다.
공격하는 쪽의 병사가 남았다면, 공격 성공으로 1을 반환하고,
방어하는 쪽의 병사가 남았거나, 모든 병사가 사망한 경우 0을 반환한다.
공격을 지휘한 장수는 병사 수에 포함하지 않는다.
공격이 성공하면 군주 mMonarchB 는 처형되고,
mMonarchB 가 다스렸던 영토는 멸망하여 동맹관계도 적대관계도 없는 새로운 영토가 된다.
새로운 영토의 군주는 mGeneral 이 되고, mMonarchA의 동맹에 편입되며, 적대 관계는 mMonarchA 의 적대 관계와 동일하다.
각 군주 이름은 알파벳 소문자로 이루어져 있으며, 길이는 4 이상 10 이하의 문자열이다.
mMonarchA 와 mMonarchB 는 현재 군주임이 보장된다. mGeneral 는 군주가 아님이 보장된다.

__Parameters
_____mMonarchA : 공격하는 군주의 이름 (4 ≤ 길이 ≤ 10)
_____mMonarchB : 공격을 받는 영토의 군주의 이름 (4 ≤ 길이 ≤ 10)
_____mGeneral : 공격을 지휘하는 장수의 이름 (4 ≤ 길이 ≤ 10)

__Returns
_____공격의 결과 (공격이 승리하면 1, 방어에 성공하면 0, 이미 동맹관계이면 -1,
_____공격 영토 주변에 공격 하는 동맹이 없는 경우 -2)

int recruit(char mMonarch[], int mNum, int mOption)
병사를 모집한다.

mOption 이 0 일 때,
-………. 군주 mMonarch 의 영토에 mNum 명의 병사를 모집한다.
-………. 병사 모집 이후에 군주 mMonarch 영토의 병사의 수를 반환한다.

mOption 이 1 일 때,
-………. 군주 mMonarch 를 포함한 모든 동맹의 영토에 각각 mNum 명의 병사를 모집한다.
-………. 병사 모집 이후에 군주 mMonarch 동맹의 모든 병사의 수 합산하여 반환한다.

군주 이름은 알파벳 소문자로 이루어져 있으며, 길이는 4 이상 10 이하의 문자열이다.
mMonarch 는 현재 군주임이 보장된다

__Parameters
_____ mMonarch : 군주의 이름 (4 ≤ 길이 ≤ 10)
_____ mNum : 병사의 수 (1 ≤ mNum ≤ 200)
_____ mOption : 병사를 모집하는 조건

__Returns
_____병사의 수

[제약사항]
1. 각 테스트 케이스 시작 시 init() 함수가, 종료 시 destroy() 함수가 호출된다.
2. 각 테스트 케이스에서 ally() 함수는 최대 8,000회 호출된다.
3. 각 테스트 케이스에서 attack() 함수는 최대 8,000회 호출된다.
4. 각 테스트 케이스에서 recruit() 함수는 최대 13,000회 호출된다.
5. 각 API에 전달되는 모든 문자열은 소문자이며, ‘\0’ (NULL 문자) 로 끝난다.

 주요 아이디어
 1. bit packing 을 통해 소문자 10글자로 이루어진 이름을 빠르게 비교 가능
 2. Hash 를 통해 long 으로 Node, Team O(1)로 접근 가능
 3. Team num 을 node 에 저장하고 각 적대관계를 hostile[][] 에 저장하여 각 team 의 관계를 O(1)로 알 수 있음.
 4. team 합병시 linked list 를 통해 O(1) 로 실행
 5. attack 시 방어 영주의 인접 node 를 빠르게 탐색하게 하기위해 node 에 x,y 위치 저장
    + 각 node 의 team num 을 통해 관계 빠르게 확인
 6. team class 를 통해 덩어리 만들기
 */
class UserSolution {

    Node[][] places;

    HashMap<Long, Node> node;

    HashMap<Integer, Team> teams;

    // team 간의 적대 관계 저장
    int[][] hostile;

    // team 생성을 위한 고유 식별자
    int num;

    int[] dx = {0, 0, 1, -1, 1, -1, 1, -1};
    int[] dy = {1, -1, 0, 0, 1, -1, -1, 1};

    void init(int N, int[][] mSoldier, char[][][] mMonarch) {

        teams = new HashMap<>();
        hostile = new int[N*N][N*N];
        num = 0;

        places = new Node[N][N];
        node = new HashMap<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                // 나중에 비교를 쉽게 하기 위해 mMonarch 는 bit packing 한 값으로 저장
                long name = bitPacking(mMonarch[i][j]);

                places[i][j] = getNode(name, mSoldier[i][j], i, j);
            }
        }


    }

    void destroy() {

    }

    int ally(char[] mMonarchA, char[] mMonarchB) {
        Node nodeA = node.get(bitPacking(mMonarchA));
        Node nodeB = node.get(bitPacking(mMonarchB));

        // 둘의 팀이 같다면
        if (nodeA.team_num == nodeB.team_num) {
            return -1;
        }

        // 둘의 팀이 다르다면
        else {

            // 두 팀이 적대 관계라면
            if (hostile[nodeA.team_num][nodeB.team_num] == -1) {
                return -2;
            }

            // 두 팀이 아무런 관계가 아니라면 team merge
            else {
                mergeTeam(teams.get(nodeA.team_num), teams.get(nodeB.team_num));
                return 1;
            }
        }
    }

    int attack(char[] mMonarchA, char[] mMonarchB, char[] mGeneral) {

        Node nodeA = node.get(bitPacking(mMonarchA));
        Node nodeB = node.get(bitPacking(mMonarchB));

        // 둘이 같은 팀이라면
        if (nodeA.team_num == nodeB.team_num) {
            return -1;
        }

        int x_b = nodeB.x;
        int y_b = nodeB.y;

        // false: 공격 불가능
        boolean flag = false;

        for (int i = 0; i < 8; i++) {
            int x = x_b + dx[i];
            int y = y_b + dy[i];

            // x,y 가 places idx 범위 안에 들 경우
            if (x >= 0 && y >= 0 && x < places.length && y < places.length && places[x][y].team_num == nodeA.team_num) {
                // 한 곳이라도 인접해있으면 공격 가능
                flag = true;
                break;
            }
        }

        // 공격 영토 주변에 인접한 동맹이 없는 경우
        if (!flag) return -2;
        else {

            // 공격 시작
            // 적대 관계 성립
            hostile[nodeA.team_num][nodeB.team_num] = -1;
            hostile[nodeB.team_num][nodeA.team_num] = -1;
        }


        // 병력 계산
        int attack_num = 0;
        int defend_num = nodeB.soldier;

        for (int i = 0; i < 8; i++) {
            int x = x_b + dx[i];
            int y = y_b + dy[i];

            // x,y 가 places idx 범위 안에 들 경우
            if (x >= 0 && y >= 0 && x < places.length && y < places.length) {

                // 공격 편일 경우
                if (places[x][y].team_num == nodeA.team_num) {

                    // 지원병력
                    int reinforcement = places[x][y].soldier / 2;

                    // 기존의 node 에서 병력 수 갱신
                    places[x][y].soldier -= reinforcement;

                    // 지원병력 추가
                    attack_num += reinforcement;
                }

                // 방어 편일 경우
                else if (places[x][y].team_num == nodeB.team_num) {

                    // 지원병력
                    int reinforcement = places[x][y].soldier / 2;

                    // 기존의 node 에서 병력 수 갱신
                    places[x][y].soldier -= reinforcement;

                    // 지원병력 추가
                    defend_num += reinforcement;
                }
            }
        }

        // 공격이 이긴 경우
        if(attack_num > defend_num) {

            // 장군이 그 영토를 새롭게 차지함.
            long name = bitPacking(mGeneral);
            Node n = new Node(name, attack_num - defend_num, x_b, y_b);
            places[x_b][y_b] = n;

            node.put(name, n);

            addNode2Tail(teams.get(nodeA.team_num), n);

            node.remove(nodeB.monarch_name);
            removeNodeInTeam(nodeB);

            return 1;
        }

        // 방어가 이긴 경우
        else {

            nodeB.soldier = defend_num - attack_num;

            return 0;
        }
    }

    int recruit(char[] mMonarch, int mNum, int mOption) {
        if (mOption == 0) {
            Node n = node.get(bitPacking(mMonarch));

            n.soldier += mNum;

            return n.soldier;
        } else {

            int ans = 0;

            Team t = teams.get(node.get(bitPacking(mMonarch)).team_num);

            Node n = t.head.next;

            while (n != t.tail) {
                n.soldier += mNum;

                ans += n.soldier;

                n = n.next;
            }

            return ans;
        }
    }

    private long bitPacking(char[] name) {

        long ans = 0;

        int num = 0;
        for (int i = 0; i < name.length * 5; i++) {

            if(name[i/5] == '\0') break;

            int j = i % 5;

            // packing 할 숫자
            if (j == 0) {
                num = name[i / 5] - 'a';
            }

            // num 의 j번째 bit 값이 1이면 ans 에 해당 bit 값 더하기
            // 최대 6개의 char 를 저장해야 하니까 해당하는 bit 는 j가 아닌 i이다.
            if ((num & (1 << j)) != 0) ans += (1L << i);
        }

        return ans;
    }

    private void addNode2Tail(Team t, Node node) {

        node.prev = t.tail.prev;
        t.tail.prev = node;

        node.prev.next = node;
        node.next = t.tail;

        // team number 갱신
        node.team_num = t.num;
    }

    public void removeNodeInTeam(Node n) {

        n = n.prev;

        n.next = n.next.next;
        n.next.prev = n;
    }

    private void mergeTeam(Team a, Team b) {

        Node n = b.head.next;

        // b 에 속한 node 들의 team number 갱신
        while (n != b.tail) {
            n.team_num = a.num;

            n = n.next;
        }

        // 적대관계 merge
        int numA = a.num;
        int numB = b.num;

        for (int i = 0; i < hostile.length; i++) {
            if (hostile[numB][i] == -1) {

                hostile[numA][i] = -1;
                hostile[i][numA] = -1;
            }
        }

        // member merge

        // a의 맨 뒤 node 와 b의 맨 앞 node 연결
        a.tail.prev.next = b.head.next;
        b.head.next.prev = a.tail.prev;

        // b의 head 연결 끊기
        b.head.next = null;

        // a의 tail = b의 tail 하고 a만 사용
        a.tail = b.tail;
    }

    private Node getNode(long monarch_name, int soldier, int x, int y) {

        // Node 생성 후 node 에 저장
        Node n = new Node(monarch_name, soldier, x, y);
        node.put(monarch_name, n);

        // n 이 속한 Team init
        Team t = new Team(num++);
        teams.put(t.num, t);

        // team 에 n 추가
        addNode2Tail(t, n);

        return n;
    }
}

class Node {

    long monarch_name;
    int soldier;

    // 인접한지
    int x;
    int y;

    int team_num;

    Node next;
    Node prev;

    public Node(long monarch_name, int soldier, int x, int y) {
        this.monarch_name = monarch_name;
        this.soldier = soldier;
        this.x = x;
        this.y = y;
        this.team_num = -1;
        this.next = null;
        this.prev = null;
    }
}

class Team {

    // 고유 식별 번호
    int num;

    // team 에 속한 member 저장 장소
    Node head;
    Node tail;

    public Team(int num) {
        this.num = num;
        head = new Node(-1, -1, -1, -1);
        tail = new Node(-1, -1, -1, -1);

        head.next = tail;
        tail.prev = head;
    }

}