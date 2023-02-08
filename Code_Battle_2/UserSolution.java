package Code_Battle_2;

import java.util.HashMap;

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