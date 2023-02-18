package 힙_6.No_23;

import java.io.*;
import java.util.StringTokenizer;

/*
2930. 힙

힙(Heap)은 최댓값 혹은 최솟값을 찾아내는 연산을 빠르게 하기 위해 고안된 자료구조이다.
완전이진트리(Complete binary tree)를 기본으로 한 자료구조로서 다음과 같은 힙 속성(property)을 만족한다.
- A가 B의 부모노드(parent node) 이면, A의 키(key)값과 B의 키값 사이에는 항상 일정한 대소관계가 성립한다.
키값의 대소관계는 오로지 부모노드와 자식노드 간에만 성립하며, 형제노드 사이에서는 일정한 대소관계가 정해지지 않는다.
부모노드의 키값이 자식노드의 키값보다 항상 크거나 같은 힙을 '최대 힙', 부모노드의 키값이 자식노드의 키값보다 항상 작거나 같은 힙을 '최소 힙'이라고 부른다.

힙의 루트노드(root node)는 힙을 구성하는 노드의 키값 중 최댓값 혹은 최솟값을 가지게 된다.
본 문제에서는 최대 힙(max heap)을 올바르게 구현하였는지 확인할 수 있다.
초기에 최대 힙이 비어있을 때에 다음의 2가지 연산을 수행하는 프로그램을 작성하자.

연산 1. 자연수 x를 삽입

연산 2. 최대 힙의 루트 노드의 키값을 출력하고, 해당 노드를 삭제

예를 들어, 쿼리가 순서대로 다음과 같이 5개가 주어졌다고 가정해보자.
1. 연산 1 - 3을 삽입
2. 연산 1 - 5를 삽입
3. 연산 2 - 최댓값 출력 후 해당 키값 삭제
4. 연산 1 - 1을 삽입
5. 연산 2 - 최댓값 출력 후 해당 키값 삭제

3번째 연산을 수행할 때를 기준으로 최대 키값은 5이기 때문에 5가 출력되고, 5는 삭제되기 때문에 최대 힙에는 3만 남게 된다.
5번째 연산을 수행할 때를 기준으로 최대 키값은 3이기 때문에 3이 출력되고, 3은 삭제되기 때문에 최대 힙에는 1만 남게 된다.
만약 가장 큰 키값이 여러 개일 경우에는, 삭제할 때에 그 키값을 가지는 노드들 전부가 삭제되는 것이 아니라, 루트 노드 단 하나만 삭제됨에 주의한다.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스마다 첫째 줄에 수행해야하는 연산의 수를 나타내는 자연수 N(1≤N≤105)이 주어진다.
둘째 줄부터 N개의 줄에 걸쳐서 순서대로 수행해야하는 연산에 대한 정보가 주어진다.
연산 1을 수행해야 한다면 2개의 자연수 '1 x'가 주어지며, x(1≤x≤109)를 최대 힙에 추가하는 연산임을 의미한다.
연산 2를 수행해야 한다면 1개의 자연수 '2'가 주어지며, 현재 최대 힙의 루트 노드의 키값을 출력하고 해당 노드를 삭제하는 연산임을 의미한다.

[출력]
각 테스트 케이스마다 첫째 줄에 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고, 연산 2의 결과들을 공백 하나를 사이에 두고 순서대로 출력한다.
만약, 연산 2를 수행해야 하는데 힙에 원소가 없어서 출력해야 할 최대 키값이 존재하지 않는다면 -1을 출력한다.

[힌트]
힙(Heap) 자료구조의 정의에 대한 설명은 본 사이트의 다음 부분에서 찾아보실 수 있습니다.
 'Learn' → 'Course' → 'Programming - Intermediate' → 'SW 문제해결 기본 - Tree' → '5차시 heap'
아래는 구현과 관련하여 도움이 될 수 있는 힌트입니다.

1. 힙은 완전이진트리이기 때문에 배열로 구현하면 상당히 편리합니다.
다음과 같이 힙의 노드들에 번호를 붙여 보겠습니다. 노드 아래에 '[ ]' 안에 있는 숫자가 각 노드의 번호입니다.
트리에서의 높이가 높을수록, 높이가 같다면 왼쪽에 위치할수록 번호가 작으며 1번부터 시작합니다.

2. 어떤 노드의 번호가 x일 때에 다음과 같은 성질을 만족합니다.
    1. x의 부모 노드 번호 = (x를 2로 나눈 몫)
    2. x의 왼쪽 자식 노드 번호 = (x 곱하기 2)
    3. x의 오른쪽 자식 노드 번호 = (x 곱하기 2) + 1

3. 삭제 연산을 구현할 때에, 자식 노드 2개가 모두 존재한다면 둘 중 큰 키값을 가지는 노드와 현재 노드의 위치를 바꾸어주는 식으로 구현함에 주의합니다.

4. C++의 경우, Standard Template Library로 안에 priority_queue가 있으며 최대 힙과 같은 역할을 수행합니다.
Java의 경우, PriorityQueue를 이용하면 구현되어 있는 method들을 호출하여 사용할 수 있습니다.

5. 힙이 비어있는지 먼저 확인을 하고, 원소를 삭제하는 연산을 수행해야 합니다.

p.s 여기서는 No_22 처럼 실수하지 않고 heap 재대로 구현했다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static int[] users;
    static int lastIdx;

    public static void insert(int n) {

        users[lastIdx++] = n;

        int loc = lastIdx -1;

        while (loc / 2 > 0) {

            // insert 한 값이 parent 의 num 보다 크다면
            if (users[loc]  > users[loc / 2]) {
                // 둘의 위치 교환
                swap(loc, loc / 2);

                // loc 갱신 후 다시 비교
                loc /= 2;
            }

            else break;
        }
    }

    public static int pop() {

        // 반환할 값이 없으면 -1
        if(lastIdx == 1) return -1;

        // 반환할 root value
        int ret = users[1];

        // 그 후 root user 삭제 후 마지막 user 를 root 자리로 이동
        users[1] = users[--lastIdx];

        // 다시 root 부터 정렬 시작
        heapSort();

        return ret;
    }

    static void heapSort() {

        int parentIdx = 1;
        while (parentIdx * 2 < lastIdx) {
            int leftChildIdx = 2 * parentIdx;
            int rightChildIdx = 2 * parentIdx + 1;
            int largestIdx = parentIdx;

            if (leftChildIdx < lastIdx && users[leftChildIdx] > users[largestIdx]) {

                // largestIdx 갱신
                largestIdx = leftChildIdx;
            }

            if (rightChildIdx < lastIdx && users[rightChildIdx] > users[largestIdx]) {

                // largestIdx 갱신
                largestIdx = rightChildIdx;
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

    static void swap(int u1, int u2) {

        int temp = users[u1];
        users[u1] = users[u2];
        users[u2] = temp;
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for(int t=1; t<=T; t++) {

            users = new int[100001];
            lastIdx = 1;

            sb.append("#").append(t).append(" ");

            int N = Integer.parseInt(br.readLine());

            for(int n=0; n<N; n++) {
                st = new StringTokenizer(br.readLine());

                int cmd = Integer.parseInt(st.nextToken());

                switch (cmd) {
                    case 1:
                        insert(Integer.parseInt(st.nextToken()));
                        break;
                    case 2:
                        sb.append(pop()).append(" ");
                }
            }

            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}
