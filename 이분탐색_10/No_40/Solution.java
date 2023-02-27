package 이분탐색_10.No_40;

import java.io.*;
import java.util.*;

/*
8898. 3차원 농부

일반적으로 가축들은 평지에서 기르기 마련이지만, 농부 지민이의 가축들은 3차원 좌표공간에서 길러진다.
지민이는 N마리의 소와 M마리의 말들을 키우고 있다.
각 가축들은 3차원 좌표 평면 상의 점으로 표현되는데, 특이하게도 지민이의 모든 소는 x=c1, y=0 직선 상에 존재하고, 지민이의 모든 말들은 x=c2, y=0 직선 상에 존재한다.
최근 삼성대학교의 연구 결과에 의하면, 두 소와 말이 가까이 있을 경우 엄청난 이산화탄소가 발생한다고 한다.
두 동물간의 거리는 맨하탄 거리로, P=(x1, y1, z1)와 Q=(x2, y2, z2) 간의 거리는 dist (P, Q) = |x2-x1 |+|y2-y1 |+|z2-z1 | 로 정의된다.
농부 지민이는 이 소식을 듣고, 농장을 분석하려고 한다.
지민이는 모든 소와 말 쌍에 대해서, 가장 가까운 쌍의 거리와, 이러한 최소 거리를 가지는 쌍의 개수를 알고 싶어한다.
지민이를 도와주자.

[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 소의 수 N, 말의 수 M이 주어진다. (1 ≤ N, M ≤ 500000)
그리고 각 테스트 케이스의 두 번째 줄에는 c1, c2가 주어진다. ( -108 ≤ c1, c2 ≤ 108)
세 번째 줄에는 N개의 정수로 소들의 위치가 주어진다.

입력은 z1, z2, z3, …, zN  의 형태이며, i번째 소는 (c1,0,zi) 에 위치함을 뜻한다. ( -108 ≤ zi ≤ 108)
네 번째 줄에는 M개의 정수로 말들의 위치가 주어진다.

입력은 z1, z2, z3, …, zM 의 형태이며, j번째 말은 (c2,0,zj) 에 위치함을 뜻한다. ( -108 ≤ zj ≤ 108)
각 테스트 케이스에 대해서, 소들의 위치와 말들의 위치는 서로 다르지만, 소와 말이 같은 위치에 있을 수는 있다.

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
가장 가까운 소 쌍의 거리와, 그러한 거리를 가지는 소 쌍들의 개수를 공백으로 구분하여 출력하라.

주요 아이디어
1. cow, horse 중 array.length 가 더 긴 것이 animal1 짧은 것이 animal2
2. animal2 에서 animal1 의 i 번째 value 보다 작거나 같은 idx 중 가장 큰 것을 binarySearch 를 통해 찾는다.
3. animal2[idx] <= animal1[i] <= animal[idx+1] 이런 모습이다.
4. 둘 중에 더 작은 dist(둘이 같은 경우도 있다) 를 가지는 idx 를 찾는다.
5. 이 문제는 동물이 같은 위치에 있을 수 있기 때문에 앞에서 hashMap 을 통해 구한 중복횟수만큼 곱한 값을 num 에 더한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static HashMap<Integer, Integer> a1;
    static HashMap<Integer, Integer> a2;

    static int[] animal1;
    static int[] animal2;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int c1 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            int x_dist = Math.abs(c1 - c2);

            st = new StringTokenizer(br.readLine());
            HashMap<Integer, Integer> cow = new HashMap<>();
            for (int i = 0; i < N; i++) {

                int j = Integer.parseInt(st.nextToken());
                cow.put(j, cow.getOrDefault(j, 0) + 1);
            }

            st = new StringTokenizer(br.readLine());
            HashMap<Integer, Integer> horse = new HashMap<>();
            for (int i = 0; i < M; i++) {

                int j = Integer.parseInt(st.nextToken());
                horse.put(j, horse.getOrDefault(j, 0) + 1);
            }

            // 굳이 이렇게 길이에 따라 a1 인지 a2 인지 정하는 이유
            // cow: 3 0 6
            // horse: -2 5 4 2
            // 에서 cow 를 기준으로 하면 6 - 4 쌍이 생략될 수 있다.
            if (N > M) {

                a1 = cow;
                a2 = horse;
            } else {
                a2 = cow;
                a1 = horse;
            }

            animal1 = Arrays.stream(a1.keySet().toArray(new Integer[0])).mapToInt(Integer::intValue).toArray();
            animal2 = Arrays.stream(a2.keySet().toArray(new Integer[0])).mapToInt(Integer::intValue).toArray();

            Arrays.sort(animal1);
            Arrays.sort(animal2);

            int min_dist = Integer.MAX_VALUE;
            int num = 0;
            for (int i = 0; i < animal1.length; i++) {

                // i 번째 animal1 와 가장 가까운 animal2 의 idx
                int idx = binarySearch(i);

                // animal2 의 모든 수보다 animal1[idx] 의 수가 작을 때
                // end = -1 이 나오므로 end = 0 으로 보정한다.
                if (idx == -1) idx = 0;

                // end 는 animal1[idx] 보다 작거나 같은 수 중 가장 큰 idx 다
                // 그래서 animal2[end] <= animal1[idx] < animal2[end + 1] 이다.

                // i 와 idx 가 더 가까울 경우
                int dist = Math.abs(animal2[idx] - animal1[i]);

                // 각 idx value 의 중복 횟수를 곱하여 temp_num 을 구한다.
                int temp_num = a1.get(animal1[i]) * a2.get(animal2[idx]);

                if (idx + 1 < animal2.length) {

                    // i 와 idx + 1 이 더 가까울 경우
                    if (dist > Math.abs(animal2[idx + 1] - animal1[i])) {
                        dist = Math.abs(animal2[idx + 1] - animal1[i]);

                        // 각 idx value 의 중복 횟수를 곱하여 temp_num 을 구한다.
                        temp_num = a1.get(animal1[i]) * a2.get(animal2[idx + 1]);
                    }

                    // i 와 idx, idx + 1 까지의 거리가 같은 경우
                    else if (dist == Math.abs(animal2[idx + 1] - animal1[i])) {

                        // 각 idx value 의 중복 횟수를 곱하여 temp_num 을 구한다.
                        temp_num += a1.get(animal1[i]) * a2.get(animal2[idx + 1]);
                    }
                }

                if (dist < min_dist) {

                    min_dist = dist;
                    num = temp_num;
                } else if (dist == min_dist) {
                    num += temp_num;
                }
            }

            sb.append("#").append(t).append(" ").append(min_dist + x_dist).append(" ").append(num).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    // animal[idx] 보다 작거나 같은 수 중 가장 큰 idx return
    private static int binarySearch(int idx) {

        int start = 0;
        int end = animal2.length - 1;

        while (start <= end) {

            int mid = (start + end) / 2;

            if (animal2[mid] > animal1[idx]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return end;
    }
}
