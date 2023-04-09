package BinarySearch_10.No_39;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
9999. 광고 시간 정하기

한나는 스페이스 스퀘어에 L분짜리 광고를 하나 올리려고 한다. 한나가 조사한 바에 따르면, 광고에 효과가 아주 좋은 N개의 피크 시간대가 있으며, i번째 피크 시간대는 si분부터 ei분 까지다.
한나는 최대한 피크 시간대와 길게 겹치도록 광고를 올리고 싶다.
한나는 적절히 t를 정해, t분부터 t+L분까지 지속되는 광고를 올릴 것이다.
이 때, 광고가 올라가 있는 시간과 피크 시간대가 가장 많이 겹치도록 했을 때, 그 겹치는 시간이 얼마나 되는지 구하는 프로그램을 작성하라.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 하나의 정수 L (1 ≤ L ≤ 108 )이 주어진다.
두 번째 줄에는 하나의 정수 N(1≤N≤105)이 주어진다.
다음부터 N개의 줄의 i번째 줄에는 두 정수 si, ei (0 ≤ si < ei < 108)가 공백 하나로 구분되어 주어진다.
ei < si+1 (1 ≤ i < N)을 만족한다.

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
광고가 올라가 있는 시간과 피크 시간대가 가장 많이 겹치도록 했을 때, 그 겹치는 시간을 (분 단위로) 출력한다.
아래 테스트 케이스의 경우, 3분에 광고를 시작하게 하면, 8분까지 광고가 지속된다. 이 때, 3분에서 5분, 6분에서 8분까지 광고가 피크 시간대와 겹치고 최대 4분 겹치게 된다.)

주요 아이디어
1. 최대한 시간이 겹치게 하기 위해서는 peek start time 중 하나에서 시작해야 한다.
2. i번째 idx 에서 시작했을 때 l 범위안에 어느 idx peek time 의 start 까지 닿는지 max idx 를 binarySearch 를 통해 검색한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static int length;
    static ArrayList<Ad> ads;


    public static void main(String[] args) throws IOException {

//        System.setIn(new java.io.FileInputStream("C:/Users/LBC/Desktop/samsung_dx/이분탐색_10/No_39/2_input.txt"));
//
//        br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            length = Integer.parseInt(br.readLine());

            int N = Integer.parseInt(br.readLine());

            ads = new ArrayList<>();

            int sum = 0;
            for (int i = 0; i < N; i++) {

                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());

                ads.add(new Ad(start, end, sum));

                sum += (end - start);
            }

            int max = 0;

            // 최대한 겹치게 하는 광고 시간의 시작시간은 peek time 시작 시간 중 하나여서
            for (int i = 0; i < N; i++) {

                int last_idx = binarySearch(i, ads.size() - 1);

                // last_time 이 last_idx 의 start, end 사이에 있는 것을 고려
                int last_time = Math.min(ads.get(i).start+length, ads.get(last_idx).end);

                int temp = ads.get(last_idx).sum - ads.get(i).sum + (last_time - ads.get(last_idx).start);

                max = Math.max(max, temp);
            }

            sb.append("#").append(t).append(" ").append(max).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    // last_idx 찾기
    private static int binarySearch(int start, int end) {

        // key 값
        int upper = ads.get(start).start + length;
        while (start <= end) {

            int mid = (start + end) / 2;

            // mid.start 가 upper 가 작은 경우 -> 아직 last_idx 가 아니다.
            if (ads.get(mid).start <= upper) {
                start = mid + 1;
            }

            // mid 가 last_idx 보다 크다.
            else {
                end = mid - 1;
            }
        }

        return end;
    }
}

class Ad {
    int start;
    int end;

    // 전 idx 까지의 길이 합
    int sum;

    public Ad(int start, int end, int sum) {
        this.start = start;
        this.end = end;
        this.sum = sum;
    }
}