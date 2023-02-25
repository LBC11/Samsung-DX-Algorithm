package 이분탐색_10.No_36;

import java.io.*;
import java.util.StringTokenizer;

/*
10507. 영어 공부

수림이는 영어 공부를 도와주는 스마트폰 앱을 사용하고 있다.
이 앱에는 영어 공부를 한 날에는 점수가 전날보다 1씩 쌓이고, 접속하지 않으면 점수가 0이 되는 “연속 공부 기간” 점수가 존재한다.
앱에는 랭킹 시스템이 있는데, 수림이가 그동안 적립했던 점수 중 최댓값을 가지고 다른 사람들과 경쟁한다.
즉, 영어 공부를 매일매일 했던 연속 기간이 길면 길수록 랭킹에서 유리하다.
수림이는 영어 공부보다는 해킹을 더 좋아해서, p개의 날짜에 대해서 영어 공부를 실제로 하지 않았더라도 한 날이라고 체크할 수 있는 방법을 알아냈다.
수림이가 p개의 날짜를 적절히 체크를 하였을 때, 영어 공부를 매일매일 했던 연속 기간의 최대 가능한 길이를 구하여라.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 영어 공부를 한 날의 수, 추가로 체크할 수 있는 날의 수를 의미하는 자연수 n, p가 주어진다. (1 ≤ n, p ≤ 200,000).
두 번째 줄에는 n개의 서로 다른 정수가 증가하는 순서대로 주어진다. 이들은 수림이가 영어 공부를 실제로 한 날의 번호들이다. 번호의 크기는 0 이상 106 이하이다..

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
연속 공부 기간의 가능한 최대 길이를 출력하라.

주요 아이디어
1. two pointer 사용

+ 최장점을 찾을 때 parametric search 을 이용하면 더 효율적이다.
*/
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static int solution(int[] study, int p) {

        int start = 0;
        int end = 1;

        int max = p + 1;

        while (start <= end && end < study.length) {

            // 현재의 end 부터 start 까지 p만큼 채우면 연속적인지 check
            int check = (study[end] - study[start] + 1) - p - (end - start + 1);

            // 연속적으로 채울수 없으면
            if (check > 0) {

                // start 를 1칸 당긴다.
                start++;
            }

            // p를 모두 사용하고 연속적으로 채울 수 있다면
            else if(check == 0){

                // 사용하지 않은 p(-check) 만큼 더한 값으로 max 기록 갱신
                max = Math.max(study[end] - study[start] + 1, max);

                // end 를 한칸 늘려도 가능한지 확인 하기위해 1칸 늘린다.
                end++;
            }

            // p를 모두 사용하지 않았지만 연속적으로 채울 수 있다면
            else {

                // max 기록 갱신
                max = Math.max(study[end] - study[start] + 1 - check, max);

                // end 를 한칸 늘려도 가능한지 확인 하기위해 1칸 늘린다.
                end++;

            }
        }

        return max;
    }

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            int[] study = new int[n];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                study[i] = Integer.parseInt(st.nextToken());
            }

            sb.append("#").append(t).append(" ").append(solution(study, p)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
