package Heap_6.No_25;

import java.io.*;
import java.util.StringTokenizer;

/*
3000. 중간값 구하기

심심해하던 홍준이에게 어떤 문제를 줄 지 고민하던 경근이는 다음과 같은 문제를 생각하였다.
경근이가 처음에 한 개의 자연수를 공책에 적는다.
그 후, N번에 걸쳐서 자연수 2개씩을 추가적으로 공책에 적는다.
이 때, 홍준이는 경근이가 수 2개를 추가적으로 적을 때마다 지금까지 적은 수 중에 크기가 중간인 수를 알려주어야 한다.
예를 들어, 처음에 경근이가 5를 공책에 쓰고, 1과 3, 2와 6, 8과 9를 공책에 썼다고 가정하자.
자연수 1과 3을 공책에 썼을 때에는, 이전까지 적은 수가 [1, 3, 5]이고, 이 중 크기가 중간인 수는 3이다.
자연수 2과 6을 공책에 썼을 때에는, 이전까지 적은 수가 [1, 2, 3, 5, 6]이고, 이 중 크기가 중간인 수는 3이다.
자연수 8과 9를 공책에 썼을 때에는, 이전까지 적은 수가 [1, 2, 3, 5, 6, 8, 9]이고, 이 중 크기가 중간인 수는 5이다.
N개의 중간값들을 매번 출력하면 출력양이 너무 많기 때문에, 그 수들의 합을 20171109로 나눈 나머지를 출력하는 프로그램을 작성하시오.

[입력]
첫 줄에 테스트케이스의 개수 T가 주어진다. (1 ≤ T ≤ 10)
각 테스트 케이스의 첫 번째 줄에 N(1 ≤ N ≤ 200,000)과 경근이가 처음에 공책에 쓴 자연수 A가 주어진다.
이후 N개의 줄에 걸쳐서, 경근이가 공책에 쓴 자연수 2개를 나타내는 X와 Y가 순서대로 주어진다.
경근이가 공책에 쓰는 자연수는 1 이상 109 이하이다.

[출력]
각 테스트케이스마다 한 줄에 걸쳐, 테스트케이스 수 “#(테스트케이스 번호) “를 출력하고, N개의 중간값을 모두 더한 값을 20171109로 나눈 나머지를 출력한다.

[힌트]
1. N개의 중간값들을 더하는 과정에서 32bit 정수 자료형의 최대 범위를 벗어날 수 있음에 주의합니다.
따라서 중간값을 더할 때마다, 20171109로 나눈 나머지를 변수에 기록하는 것이 좋습니다.

2. 최대 힙과 최소 힙을 이용하여 문제를 해결할 수 있습니다.

1. 주요 아이디어
maxHeap 은 중간값보다 작은 수를 저장, minHeap 에는 중간값보다 큰 수를 저장 이라는 틀을 이용하면 편하다.

구현할 때 pop method 에서 parent == largest 이면 break 를 해야하는 데 뺴놓았다가 시간을 엄청 소요한 문제...
구현 실수 줄여야 한다!!!!
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static long[] minHeap;
    static long[] maxHeap;

    static int min_last_idx;
    static int max_last_idx;

    static long result;
    static long divisor = 20171109L;

    static void min_insert(long num) {

        minHeap[++min_last_idx] = num;

        int loc = min_last_idx;

        // parent idx 가 1 이상이고 parent 보다 num 이 작을 때
        while (loc / 2 > 0 && minHeap[loc / 2] > minHeap[loc]) {

            // 둘의 num 교환
            swap(loc / 2, loc, minHeap);

            // 현재 위치 parent 로 갱신
            loc /= 2;
        }
    }

    static long min_pop() {

        // 반환할 값
        long ret = minHeap[1];

        // 맨 마지막 값을 root 에 넣고
        minHeap[1] = minHeap[min_last_idx--];

        // 다시 heap 정렬
        int parent_idx = 1;
        while (parent_idx * 2 <= min_last_idx) {

            int left_child_idx = parent_idx * 2;
            int right_child_idx = parent_idx * 2 + 1;
            int smallest_idx = parent_idx;

            // left child 의 값이 더 작다면
            if (left_child_idx <= min_last_idx && minHeap[left_child_idx] < minHeap[smallest_idx]) {

                smallest_idx = left_child_idx;
            }

            // right child 의 값이 더 작다면
            if (right_child_idx <= min_last_idx && minHeap[right_child_idx] < minHeap[smallest_idx]) {

                smallest_idx = right_child_idx;
            }

            // idx 가 갱신되었다면
            if (parent_idx != smallest_idx) {

                // 둘의 값 교환
                swap(parent_idx, smallest_idx, minHeap);

                // parent idx 갱신 후 다시 sort
                parent_idx = smallest_idx;
            } else break;
        }

        return ret;
    }

    static void max_insert(long num) {

        maxHeap[++max_last_idx] = num;

        int loc = max_last_idx;

        // parent idx 가 1 이상이고 parent 보다 num 이 클 때
        while (loc / 2 > 0 && maxHeap[loc / 2] < maxHeap[loc]) {

            // 둘의 num 교환
            swap(loc / 2, loc, maxHeap);

            // 현재 위치 parent 로 갱신
            loc /= 2;
        }
    }

    static long max_pop() {

        // 반환할 값
        long ret = maxHeap[1];

        // 맨 마지막 값을 root 에 넣고
        maxHeap[1] = maxHeap[max_last_idx--];

        // 다시 heap 정렬
        int parent_idx = 1;
        while (parent_idx * 2 <= max_last_idx) {

            int left_child_idx = parent_idx * 2;
            int right_child_idx = parent_idx * 2 + 1;
            int largest_idx = parent_idx;

            // left child 의 값이 더 크다면
            if (left_child_idx <= max_last_idx && maxHeap[left_child_idx] > maxHeap[largest_idx]) {

                largest_idx = left_child_idx;
            }

            // right child 의 값이 더 크다면
            if (right_child_idx <= max_last_idx && maxHeap[right_child_idx] > maxHeap[largest_idx]) {

                largest_idx = right_child_idx;
            }

            // idx 가 갱신되었다면
            if (parent_idx != largest_idx) {

                // 둘의 값 교환
                swap(parent_idx, largest_idx, maxHeap);

                // parent idx 갱신 후 다시 sort
                parent_idx = largest_idx;
            } else break;
        }

        return ret;
    }

    static void find(long num1, long num2) {

        // 중간값 보다 크다면 minHeap 에 insert
        // 아니면 maxHeap 에 insert
        if (num1 > maxHeap[1]) min_insert(num1);
        else max_insert(num1);

        if (num2 > maxHeap[1]) min_insert(num2);
        else max_insert(num2);

        // 한 쪽의 Heap 으로 둘 다 insert 되면
        // 많은 쪽에서 pop 후 그 숫자를 적은 쪽에 insert 한다.
        if (max_last_idx < min_last_idx) {
            long t = min_pop();
            max_insert(t);
        } else if (max_last_idx - 1 > min_last_idx) {
            long t = max_pop();
            min_insert(t);
        }

        // result 갱신
        result = (result + maxHeap[1]) % divisor;
    }

    static void swap(int idx1, int idx2, long[] heap) {
        long temp = heap[idx1];
        heap[idx1] = heap[idx2];
        heap[idx2] = temp;
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            result = 0L;
            maxHeap = new long[300001];
            minHeap = new long[300001];

            // root idx 가 1
            min_last_idx = 0;
            max_last_idx = 0;

            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            max_insert(Long.parseLong(st.nextToken()));

            for (int n = 0; n < N; n++) {

                st = new StringTokenizer(br.readLine());
                find(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
            }

            sb.append("#").append(t).append(" ").append(result).append("\n");

        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}
