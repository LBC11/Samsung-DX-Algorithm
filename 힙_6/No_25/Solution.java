package 힙_6.No_25;

import java.io.*;
import java.util.StringTokenizer;

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
            }
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
            }
        }

        return ret;
    }

    static void find(long num1, long num2) {

        // num2 가 항상 크기 위해서서
       if(num1 > num2) {
            long temp = num1;
            num1 = num2;
            num2 = temp;
        }

       max_insert(num1);
       min_insert(num2);

       if(maxHeap[1] > minHeap[1]) {
           swap2();
       }

//       // middle value 는 maxHeap 의 root value 이다.
//
//        // 둘 다 middle 보다 큰 경우
//        if (num2 < maxHeap[1]) {
//
//            // 기존 middle 은 min 에 넣는다.
//            min_insert(max_pop());
//
//            // 먼저 둘다 max 에 넣고
//            max_insert(num1);
//            max_insert(num2);
//
//        }
//
//        // 둘 중에 하나만 middle 보다 작고 나머지는 큰 경우
//        else if (num1 < maxHeap[1] && num2 > maxHeap[1]) {
//
//            // 작은 놈은 max 에 넣고 큰 놈은 min 에 넣는다.
//            max_insert(num1);
//            min_insert(num2);
//        }
//
//        // 둘 다 middle 보다 큰 경우
//        else if (num1 > maxHeap[1]) {
//
//            max_insert(min_pop());
//
//            // 먼저 둘 다 min 에 넣고
//            min_insert(num1);
//            min_insert(num2);
//        }



        // result 갱신
        result = (result + maxHeap[1]) % divisor;
    }

    static void swap(int idx1, int idx2, long[] heap) {
        long temp = heap[idx1];
        heap[idx1] = heap[idx2];
        heap[idx2] = temp;
    }

    static void swap2() {
        long temp = maxHeap[1];
        maxHeap[1] = minHeap[1];
        minHeap[1] = temp;
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            result = 0L;
            maxHeap = new long[200001];
            minHeap = new long[200001];

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
