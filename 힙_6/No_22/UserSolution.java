package 힙_6.No_22;

/*
12372. 기초 Partial Sort 연습

MAX 10만명의 User 에 대해 수입이 주어진다.
수입이 가장 큰 user 10명의 uID를 수입에 대해 내림차순으로 구하라.

void init ()
각 테스트케이스 처음에 호출되는 초기화 함수이다.

void addUser (int uID, int income)
user 을 추가하는 함수이다.

Parameters:
uID: user id, 0부터 시작해서 순차적으로 증가한다 (0 ≤ uID < 100000)
income: user 의 수입, 클수록 우선순위가 높다. 만약 수입이 동일한 경우 uID가 작은 user 의 우선순위가 높다.

int getTop10 (int result[MAX_CANDI])
수입이 가장 큰 user 10명의 uID를 수입에 대해 내림차순으로 구하는 함수이다.
총 user 의 수가 10명이 되지 않으면 존재하는 user 의 uID를 수입에 대해 내림차순으로 구한다.
result 의 개수를 반환한다.

Parameters:
result[]: 수입이 큰 순서대로 10개의 uID를 저장한다. (1 ≤ result 개수 ≤ 10)

주요 아이디어
1. 먼저 들어오는 user 를 max heap 에 넣는다.
2. getTop10 시 최대 10번 pop 한 user 를 temp 에 넣는다.
3. temp 를 내림차순으로 정렬 후 앞의 10개 idx 를 반환한다.

여기서 heap 구현할 때 초기 parent node 의 idx 를 0로 생각하고
x의 부모 노드 번호 = (x를 2로 나눈 몫)
x의 왼쪽 자식 노드 번호 = (x 곱하기 2) + 1
x의 오른쪽 자식 노드 번호 = (x 곱하기 2) + 2
로 했었는데 이러면 나중에 숫자가 커지면 문제생긴다.

초기 parent 는 1 부터 시작하고
x의 부모 노드 번호 = (x를 2로 나눈 몫)
x의 왼쪽 자식 노드 번호 = (x 곱하기 2)
x의 오른쪽 자식 노드 번호 = (x 곱하기 2) + 1
로 구현해야 한다.

위의 지점때문에 하루 날렸다...
나중에 다시 구현해보자
 */
public class UserSolution {

    User[] users;
    int length;

    User[] temp;
    int temp_length;

    public void init() {

        users = new User[100001];
        length = 0;

        temp = new User[101];
        temp_length = 0;
    }

    public void insert(int idx) {

        int loc = idx;

        while (loc > 0) {

            // insert 한 user 의 income 이 parent_user 의 income 보다 크다면
            if (users[loc].income > users[loc / 2].income) {
                // 둘의 위치 교환
                swap(loc, loc / 2);

                // loc 갱신 후 다시 비교
                loc /= 2;
            }

            // user 의 income 이 parent income 과 같지만 uid 가 더 작을 때
            else if (users[loc].income == users[loc / 2].income && users[loc].idx < users[loc / 2].idx) {

                swap(loc, loc / 2);
                break;
            } else break;
        }

    }

    public User pop() {

        // 반환할 root user
        User ret = users[0];

        // 그 후 root user 삭제 후 마지막 user 를 root 자리로 이동
        users[0] = users[--length];

        // 다시 root 부터 정렬 시작
        heapSort();

        return ret;
    }

    void heapSort() {

        int parentIdx = 0;
        while (parentIdx * 2 + 1 < length) {
            int leftChildIdx = 2 * parentIdx + 1;
            int rightChildIdx = 2 * parentIdx + 2;
            int largestIdx = parentIdx;

            if (leftChildIdx < length) {

                // leftChild user 의 income 이 parent 보다 클 때
                if (users[leftChildIdx].income > users[largestIdx].income
                        // leftChild user 의 income 이 parent income 과 같지만 uid 가 더 작을 때
                        || (users[leftChildIdx].income == users[largestIdx].income
                        && users[leftChildIdx].idx < users[largestIdx].idx)) {

                    // largestIdx 갱신
                    largestIdx = leftChildIdx;
                }
            }

            if (rightChildIdx < length) {

                // rightChild user 의 income 이 parent 보다 클 때
                if (users[rightChildIdx].income > users[largestIdx].income
                        // rightChild user 의 income 이 parent income 과 같지만 uid 가 더 작을 때
                        || (users[rightChildIdx].income == users[largestIdx].income
                        && users[rightChildIdx].idx < users[largestIdx].idx)) {

                    // largestIdx 갱신
                    largestIdx = rightChildIdx;
                }
            }

            if (parentIdx != largestIdx) {

                // idx 가 달라졌다면 값 갱신
                swap(parentIdx, largestIdx);

                // parentIdx 갱신
                // 교환된 값을 가지는 parent 의 sub tree 를 재검사
                parentIdx = largestIdx;
            }

            // 아니라면 반복문 종료
            else break;
        }
    }

    // 둘의 위치를 교환
    void swap(int u1, int u2) {

        User temp = users[u1];
        users[u1] = users[u2];
        users[u2] = temp;
    }

    void swap2(int u1, int u2) {

        User t = temp[u1];
        temp[u1] = temp[u2];
        temp[u2] = t;
    }

    void bubble_sort() {

        for (int i = 0; i < temp_length - 1; i++) {
            // 정렬 되지 않은 index 만큼 반복
            for (int j = 0; j < temp_length - i - 1; j++) {

                if (temp[j + 1].income > temp[j].income ||
                        (temp[j + 1].income == temp[j].income && temp[j + 1].idx < temp[j].idx)) {

                    swap2(j + 1, j);
                }
            }
        }
    }

    public void addUser(int uID, int income) {

        // user init
        users[length] = new User(uID, income);

        // insert the user to the tree
        insert(length);

        length++;
    }

    int getTop10(int[] result) {

        // 10, size - 1 둘 중 작은 수가 현재 return 해야하는 수
        int l = length;

        if (l > 10) l = 10;

        for (int i = 0; i < l; i++) {

            // pop 해서 user 를 뺴내고 temp 에 저장
            User u = pop();
            temp[temp_length++] = u;
        }

        bubble_sort();

        int l2 = temp_length;

        if (l2 > 10) l2 = 10;

        for (int i = 0; i < l2; i++) {
            result[i] = temp[i].idx;
        }

        return l2;
    }


}

class User {

    int idx;
    int income;

    public User(int idx, int income) {
        this.idx = idx;
        this.income = income;
    }
}
