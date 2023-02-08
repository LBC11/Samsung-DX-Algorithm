import java.util.ArrayList;
import java.util.List;

/**
 * 문제 내용: 삼국지 게임을 구현
 * 
 * 전략
 * - 모든 영토들을 초기화(init)하는 과정에서 각 영토들에 그룹 번호를 부여한다.
 * - 이 그룹 번호를 지칭하는 노드(IndexNode)에는 그룹 번화 동맹국, 적대국들의 정보가 담겨있다.
 * - A,B가 동맹을 하면 A쪽으로 그룹 번호를 통일시킨다.
 *   - B의 그룹번호에 들어있는 모든 동맹국들을 통으로 A에 옮긴다.
 *   - B의 그룹번호에 들어있는 모든 적대국들을 통으로 A의 적대국 목록에 옮긴다.
 *      - 이 때, B를 적대국으로 지정한 모든 그룹번호들에 A라는 적대국을 추가한다. (서로가 적으로 인지하게)
 * - A가 B영토를 침략해서 성공한다면, B를 떼어내서 그룹번호를 A로 바꾸고 군주 이름을 바꾸고 A로 넘어간다.
 *
 * @autho HeejoPark
 */
class IndexNode{
    int groupNo;    //그룹번호
    List<Integer> enemyGroupNo; //해당 동맹의 적들
    Monarchs allyMonarchs;  //해당 그룹 번호를 가진 동맹국들

    IndexNode(int groupNo){
        this.groupNo = groupNo;
        this.allyMonarchs = new Monarchs(-1, -1, groupNo);
        this.enemyGroupNo = new ArrayList<>();
    }
}
class Monarchs{ //군주의 정보
    int groupNo;    //그룹 번호
    int x;  //영토의 x위치
    int y;  //영토의 y위치
    Monarchs next;  //다음 군주로의 연결 링크

    Monarchs(int x, int y, int groupNo){
        this.x = x;
        this.y = y;
        this.groupNo = groupNo;
        this.next = null;
    }
}
class UserSolution {
    Monarchs[][] nodeMap;    //군주 노드
    IndexNode[] groupList;   //군주들의 그룹번호 정보
    int[][] soldierMap;     //해당 영토의 병사 수
    String[][] monarchNameMap;  //해당 군주의 이름
    int N;
    void init(int N, int mSoldier[][], char mMonarch[][][])
    {
        this.N = N;
        groupList = new IndexNode[N*N];
        nodeMap = new Monarchs[N][N];
        soldierMap = new int[N][N];
        monarchNameMap = new String[N][N];
        for(int i =0; i<N; i++){
            for(int j = 0; j<N; j++){
                int groupNo = (i * N) + j;
                nodeMap[i][j] = new Monarchs(i, j, groupNo);    //노드 생성
                groupList[groupNo] = new IndexNode(groupNo);    //그룹리스트 초기화
                groupList[groupNo].allyMonarchs = new Monarchs(-1, -1, groupNo);    //동맹리스트 초기화
                groupList[groupNo].allyMonarchs.next = nodeMap[i][j];   //해당 군주 동맹에 넣기
                soldierMap[i][j] = mSoldier[i][j];  //군사 수 입력받기
                monarchNameMap[i][j] = charToString(mMonarch[i][j]);    //군주 이름 입력받기
            }
        }
    }
    void destroy()
    {
    }
    int ally(char mMonarchA[], char mMonarchB[])
    {
        //해당 군주의 노드를 찾기
        Monarchs monarchA = findNodeOfMonarch(mMonarchA);
        Monarchs monarchB = findNodeOfMonarch(mMonarchB);

        //군주가 같으면 -1 반환
        if(monarchA == monarchB){
            return -1;
        }
        //군주들이 이미 동맹관계라면 -1 반환
        if(isAlreadyAlly(monarchA, monarchB)){
            return -1;
        }
        //군주들이 이미 적대관계라면 -2 반환
        if(isAlreadyEnemy(monarchA, monarchB)){
            return -2;
        }
        //두 군주는 동맹관계가 된다.
        setAlly(monarchA, monarchB);
        return 1;
    }
    int attack(char mMonarchA[], char mMonarchB[], char mGeneral[])
    {
        //군주 노드를 찾기
        Monarchs monarchA = findNodeOfMonarch(mMonarchA);
        Monarchs monarchB = findNodeOfMonarch(mMonarchB);

        //만약 두 군주가 동맹관계라면 -1 반환
        if(isAlreadyAlly(monarchA, monarchB)){
            return -1;
        }

        //monarchB 주변에 monarchA의 동맹국이 없다면 -2 반환
        boolean isMonarchAAllyNearMonarchB = false;
        findAllys : for(int i =-1; i<=1; i++){
            for(int j =-1; j<=1; j++){
                if(i==0 && j==0){
                    continue;
                }
                int nextX = monarchB.x + i;
                int nextY = monarchB.y + j;
                if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){ //배열 밖을 벗어나면 패스
                    continue;
                }
                //해당 위치가 A와 동맹국이라면 주변에 동맹국이 존재하는 것이다.
                if(isAlreadyAlly(nodeMap[nextX][nextY], monarchA)){
                    isMonarchAAllyNearMonarchB = true;
                    break findAllys;
                }
            }
        }
        if(!isMonarchAAllyNearMonarchB){
            return -2;
        }

        //전투 발생. 각 군주의 모든 노드에 적대관계를 표시
        setEnemy(monarchA, monarchB);

        //monarchA가 속한 동맹팀이 지역 monarchB를 친다.
        int aSoldierSum = 0;
        int bSoldierSum = 0;
        for(int i =-1; i<=1; i++){
            for(int j =-1; j<=1; j++){
                if(i==0 && j==0){   //공격 대상인 monarchB 자기 자신은 모든 병력을 동원한다.
                    bSoldierSum += soldierMap[monarchB.x][monarchB.y];
                    continue;
                }
                int nextX = monarchB.x + i;
                int nextY = monarchB.y + j;
                if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= N){ //배열 밖을 벗어나면 패스
                    continue;
                }
                //해당 위치가 A와 동맹국이라면 a쪽에 병력을 지원한다.
                if(isAlreadyAlly(nodeMap[nextX][nextY], monarchA)){
                    aSoldierSum += (soldierMap[nextX][nextY] / 2);
                    soldierMap[nextX][nextY] -= (soldierMap[nextX][nextY] / 2);
                }
                //해당 위치가 B와 동맹국이라면 b쪽에 병력을 지원한다.
                else if(isAlreadyAlly(nodeMap[nextX][nextY], monarchB)){
                    bSoldierSum += (soldierMap[nextX][nextY] / 2);
                    soldierMap[nextX][nextY] -= (soldierMap[nextX][nextY] / 2);
                }
                //어느 쪽도 아니라면 패스
            }
        }
        //전투 결과 반영
        soldierMap[monarchB.x][monarchB.y] = Math.abs(aSoldierSum - bSoldierSum);
        //점령 성공했다면
        if(aSoldierSum > bSoldierSum){
            //monarchB에 관련된 정보를 지운다.
            Monarchs curIndex = groupList[monarchB.groupNo].allyMonarchs.next;
            Monarchs prevIndex = groupList[monarchB.groupNo].allyMonarchs;
            while(curIndex.next != null){
                //해당 노드를 찾았다면
                if(curIndex.x == monarchB.x && curIndex.y == monarchB.y){
                    break;
                }
                curIndex = curIndex.next;
                prevIndex = prevIndex.next;
            }
            //찾은 노드를 B에서 끊는다.
            prevIndex.next = curIndex.next;
            //A의 영토로 변경하고, mGeneral이 군주가 된다.
            curIndex.groupNo = monarchA.groupNo;
            String str = charToString(mGeneral);
            monarchNameMap[curIndex.x][curIndex.y] = str;
            //A에 집어넣는다.
            Monarchs newIndex = groupList[monarchA.groupNo].allyMonarchs;
            while(newIndex.next != null){
                newIndex = newIndex.next;
            }
            newIndex.next = curIndex;
            curIndex.next = null;
            return 1;
        }
        //점령 실패
        else{
            return 0;
        }
    }


    int recruit(char mMonarch[], int mNum, int mOption)
    {
        int sum = 0;
        Monarchs monarch = findNodeOfMonarch(mMonarch);
        switch(mOption){
            case 0: //0번 옵션이면 해당 군주에만 병사를 추가한다.
                soldierMap[monarch.x][monarch.y] += mNum;   //해당 군주에 병사 추가
                sum += soldierMap[monarch.x][monarch.y];     //추가된 병사를 포함하여 병사 수를 출력
                break;
            case 1: //1번 옵셥이면 모든 동맹의 영토에 병사를 추가한다.
                int groupNo = monarch.groupNo;
                Monarchs increaseIndex = groupList[groupNo].allyMonarchs;
                while(increaseIndex.next != null){
                    //모든 동맹들을 하나씩 돌아가며 병사 추가
                    increaseIndex = increaseIndex.next;
                    soldierMap[increaseIndex.x][increaseIndex.y] += mNum;
                    sum += soldierMap[increaseIndex.x][increaseIndex.y];
                }
                break;
            default:
                //ERROR: 잘못된 옵션 번호 입력
                sum = -1;
                break;
        }
        return sum;
    }

    /**
     * 서로를 동맹국으로 설정한다.
     * @param monarchA
     * @param monarchB
     */
    void setAlly(Monarchs monarchA, Monarchs monarchB){
        int unionGroupNo = monarchA.groupNo;    //합칠 그룹 번호
        int removeGroupNo = monarchB.groupNo;   //사라질 그룹 번호
        Monarchs unionIndex = groupList[unionGroupNo].allyMonarchs; //합칠 그룹
        while(unionIndex.next != null){
            unionIndex = unionIndex.next;   //합칠 그룹 노드의 맨 끝으로 이동
        }
        Monarchs removeIndex = groupList[removeGroupNo].allyMonarchs;   //옮겨질 노드들

        while(removeIndex.next != null){
            removeIndex = removeIndex.next; //monarch와 동맹인 모든 군주들의
            removeIndex.groupNo = unionGroupNo; //그룹번호를 monarchA의 그룹번호로 바꾼다.
        }

        //monarchB와 동맹인 모든 군주들을 monarchA에 편입시킨다.
        removeIndex = groupList[removeGroupNo].allyMonarchs;    //monarchB를 포함한 모든 동맹들
        unionIndex.next = removeIndex.next; //monarchA쪽에 넣는다

        //적의 정보들도 서로 공유한다.
        for(int i =0; i<groupList[removeGroupNo].enemyGroupNo.size(); i++){
            groupList[unionGroupNo].enemyGroupNo.add(groupList[removeGroupNo].enemyGroupNo.get(i)); //합쳐진 그룹번호에 기존 적국 명단을 추가한다.
            groupList[groupList[removeGroupNo].enemyGroupNo.get(i)].enemyGroupNo.add(unionGroupNo); //적국들은 합쳐진 새로운 그룹번호를 적으로 저장한다.
        }
    }

    /**
     * 두 군주를 적으로 표기한다.
     * @param monarchA
     * @param monarchB
     */
    void setEnemy(Monarchs monarchA, Monarchs monarchB){
        if(!isAlreadyEnemy(monarchA, monarchB)) {
            groupList[monarchA.groupNo].enemyGroupNo.add(monarchB.groupNo);
            groupList[monarchB.groupNo].enemyGroupNo.add(monarchA.groupNo);
        }
    }

    /**
     * 두 군주가 이미 동맹인지를 판단한다.
     * @param monarchA
     * @param monarchB
     * @return 동맹국 여부
     */
    boolean isAlreadyAlly(Monarchs monarchA, Monarchs monarchB){
        if(monarchA.groupNo == monarchB.groupNo){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 두 군주가 이미 적인지를 판단한다.
     * @param monarchA
     * @param monarchB
     * @return 적국 여부
     */
    boolean isAlreadyEnemy(Monarchs monarchA, Monarchs monarchB){
        for(int i =0; i<groupList[monarchA.groupNo].enemyGroupNo.size(); i++){
            if(groupList[monarchA.groupNo].enemyGroupNo.get(i) == monarchB.groupNo){
                return true;
            }
        }
        return false;
    }

    /**
     * 군주의 이름을 가지고 해당 군주가 있는 위치를 찾는다.
     * @param mMonarch
     * @return 군주의 좌표(x,y)
     */
    int[] findLocationOfMonarch(char mMonarch[]){
        String str = charToString(mMonarch);
        for(int i = 0; i<N; i++){
            for (int j =0; j<N; j++){
                //이름이 일치한다면
                if(str.equals(monarchNameMap[i][j])){
                    return new int[]{i, j};
                }
            }
        }
        //ERROR
        return new int[]{-1, -1};
    }

    /**
     * 해당 군주를 상징하는 노드를 찾는다.
     * @param mMonarch
     * @return 군주의 노드
     */
    Monarchs findNodeOfMonarch(char mMonarch[]){
        int[] locMonarch = findLocationOfMonarch(mMonarch);
        return nodeMap[locMonarch[0]][locMonarch[1]];
    }

    /**
     * char[] 타입을 String 타입으로 변경
     * @param mMonarch
     * @return (String) mMonarch
     */
    String charToString(char mMonarch[]) {
        String str = "";
        for (int i = 0; i < mMonarch.length; i++) {
            //알파벳부분이 아니라면 종료
            if(mMonarch[i] < 'a' || mMonarch[i] > 'z'){
                break;
            }
            str += mMonarch[i];
        }
        return str;
    }
}