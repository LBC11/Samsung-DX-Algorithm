package Kakao.delivery_2023;

import java.util.LinkedList;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {

        long answer = 0;

        // 각 array 에서 배달할 집의 가장 마지막 idx
        int d = n - 1;

        // 각 array 에서 수거할 집의 가장 마지막 idx
        int p = n - 1;

        // 각 array 에서 배달(혹은 수거할 양)
        int temp_cap;

        // 배달을 실행한 idx 중 last_idx + 1 이 배달에 필요한 편도 거리이다.
        int d_last_idx;
        int p_last_idx;

        LinkedList<Integer> list = new LinkedList<>();

        // 배달, 수거 둘 중에 하나만이라도 남아있다면 계속한다.
        while (d >= 0 || p >= 0) {

            // 다음 search 를 위한 초기화
            list.clear();
            temp_cap = 0;

            // 배달이 남은 경우
            if(d >= 0) {

                // temp_cap 이 cap 을 넘지 않거나 d 가 0이상일 때 반복한다.
                while(temp_cap < cap && d >= 0) {

                    // 현재 idx 에 배달할 것이 남아 있을 때
                    if(deliveries[d] != 0) {

                        // 배달 해결
                        temp_cap += deliveries[d];
                        deliveries[d] = 0;

                        // 사용한 idx list 에 저장
                        list.addFirst(d);
                    }

                    d--;
                }

                // cap 을 넘어섰다면
                if(temp_cap > cap) {

                    // 전 idx 로 돌아가 넘친 만큼 다시 할당해준다.
                    deliveries[++d] = temp_cap - cap;
                }
            }

            // list 에 저장한 수가 있다면 가장 처음 저장한 수 사용
            if(!list.isEmpty()) d_last_idx = list.getLast();
            else d_last_idx = -1;

            // 다음 search 를 위한 초기화
            list.clear();
            temp_cap = 0;

            // 수거가 남은 경우
            if(p >= 0) {

                // temp_cap 이 cap 을 넘지 않거나 p 가 0이상일 때 반복한다.
                while(temp_cap < cap && p >= 0) {

                    // 현재 idx 에 수거할 것이 남아 있을 때
                    if(pickups[p] != 0) {

                        // 수거 해결
                        temp_cap += pickups[p];
                        pickups[p] = 0;

                        // 사용한 idx list 에 저장
                        list.addFirst(p);
                    }

                    p--;
                }

                // cap 을 넘어섰다면
                if(temp_cap > cap) {

                    // 전 idx 로 돌아가 넘친 만큼 다시 할당해준다.
                    pickups[++p] = temp_cap - cap;
                }
            }


            // list 에 저장한 수가 있다면 가장 처음 저장한 수 사용
            if(!list.isEmpty()) p_last_idx = list.getLast();
            else p_last_idx = -1;

            answer += (Math.max(d_last_idx, p_last_idx) + 1) * 2L;
        }

        return answer;
    }
}