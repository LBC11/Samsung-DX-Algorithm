package Dijkstra_12.No_43;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
16245. 물류허브

[문제 설명]
여러 도시를 연결하는 단방향의 도로 N개가 운송 비용과 함께 주어진다.
이 도시 중에서, 한 곳에 물류 허브를 설치했을 때, 총 운송 비용을 계산하고자 한다.
총 운송 비용은 각 도시에서 허브 도시까지 왕복에 필요한 최소 비용을 모두 합한 값이다.
허브 도시의 운송 비용은 0이다. 그리고 도로는 단방향이기 때문에 허브 도시까지 가는 최소 비용과 돌아오는 최소 비용은 다를 수 있다.

예를 들어, [Fig. 1]과 같이 도시와 도로의 정보가 주어진 경우를 살펴 보자.
원 안의 숫자는 도시를 나타내는 번호이고, 화살표는 각 도시를 연결하는 단방향의 도로이고 그 위의 숫자가 운송 비용이다.
5번 도시에 허브를 설치할 경우 각 도시에서 허브 도시까지 왕복에 필요한 최소 비용은 [Fig. 1]의 표와 같다.
따라서, 총 운송 비용은 83 + 102 + 53 + 44 = 282가 된다.

아래는 User Code 부분에 작성해야 하는 API 의 설명이다.

int init(int N, int sCity[], int eCity[], int mCost[])
각 테스트 케이스의 처음에 호출된다.
N개의 도로 정보가 주어진다. 각 도로의 출발 도시와 도착 도시, 운송 비용이 주어진다.
도로 정보로 주어지는 도시의 총 개수를 반환한다.
단방향 도로이기 때문에 출발 도시에서 도착 도시로만 갈 수 있다.
출발 도시와 도착 도시의 순서쌍이 동일한 도로는 없다.
출발 도시와 도착 도시가 서로 같은 경우는 없다.
Parameters
 N: 도로의 개수 ( 10 ≤ N ≤ 1400 )
 (0 ≤ i ＜ N)인 모든 i에 대해,
 sCity[i]: 도로 i의 출발 도시 ( 1 ≤ sCity[i] ≤ 1,000,000,000 )
 eCity[i]: 도로 i의 도착 도시 ( 1 ≤ eCity[i] ≤ 1,000,000,000 )
 mCost[i]: 도로 i의 운송 비용 ( 1 ≤ mCost[i] ≤ 100 )

Returns
 도시의 총 개수를 반환한다.

void add(int sCity, int eCity, int mCost)
출발 도시가 sCity이고, 도착 도시가 eCity이고, 운송 비용이 mCost인 도로를 추가한다.
init()에 없던 새로운 도시는 주어지지 않는다.
sCity에서 eCity로 가는 도로가 이미 존재하는 경우는 입력으로 주어지지 않는다.

Parameters
 sCity: 도로 i의 출발 도시 ( 1 ≤ sCity ≤ 1,000,000,000 )
 eCity: 도로 i의 도착 도시 ( 1 ≤ eCity ≤ 1,000,000,000 )
 mCost: 도로 i의 운송 비용 ( 1 ≤ mCost ≤ 100 )

int cost(int mHub)
mHub 도시에 물류 허브를 설치할 경우, 총 운송 비용을 계산하여 반환한다.
mHub 도시의 운송 비용은 0으로 계산한다.
각 도시에서 mHub 도시까지 왕복이 불가능한 경우는 입력으로 주어지지 않는다.

Parameters
 mHub: 허브를 설치할 도시 ( 1 ≤ mHub ≤ 1,000,000,000 )

Returns
 총 운송 비용, 다시 말해 각 도시에서 허브 도시까지 왕복에 필요한 최소 비용을 모두 합한 값을 반환한다.

[제약사항]
1. 각 테스트 케이스 시작 시 init() 함수가 호출된다.
2. 각 테스트 케이스에서 도시의 최대 개수는 600 이하이다.
3. 각 테스트 케이스에서 모든 함수의 호출 횟수 총합은 50 이하이다.

[입출력]
입출력은 제공되는 Main 부분의 코드에서 처리하므로 User Code 부분의 코드에서는 별도로 입출력을 처리하지 않는다.
Sample input 에 대한 정답 출력 결과는 “#TC번호 결과” 의 포맷으로 보여지며 결과가 100 일 경우 정답, 0 일 경우 오답을 의미한다.

주요 아이디어
1. 정점에서 정점까지의 거리는 Dijkstra algorithm 을 이용하여 구한다.
2. 도시의 idx 의 범위가 최대 1,000,000,000 이므로 array 보다는 HashMap 을 이용하여 정보를 저장하였다.
3. 이런 희소 그래프(edge 가 많이 없는) 에서는 Dijkstra 를 구현할 때
   queue 보다는 priorityQueue 를 이용하는 것이 시간 복잡도에서 유리하다.
4. 모든 정점에서 모든 정점까지의 min_cost 를 구하는 것은 n! 의 시간복잡도를 가지기 때문에 제한시간을 초과한다.
5. 특정 정점에서 모든 정점까지의 min_cost 만 구해도 괜찮다.
6. 5번에서 outcome_cost 가 해결이 되었다면 아직 income_cost 를 구해야 하는데
   이는 모든 edge 를 뒤집고 5번과 같은 logic 을 거치면 구할 수 있다!!!!! -> 매우 중요!!!
 */
class UserSolution {

    HashMap<Integer, City> cities;

    public int init(int N, int[] sCity, int[] eCity, int[] mCost) {

        cities = new HashMap<>();

        for(int i=0; i<N; i++) {

            Road r = new Road(sCity[i], eCity[i], mCost[i]);

            // city 가 init 되어있지 않다면
            if(!cities.containsKey(sCity[i])) {

                // init 후 cities 에 추가
                cities.put(sCity[i], new City(sCity[i]));
            }

            // city 에 도로 정보 추가
            cities.get(sCity[i]).outcome.add(r);

            if(!cities.containsKey(eCity[i])) {
                cities.put(eCity[i], new City(eCity[i]));
            }

            cities.get(eCity[i]).income.add(r);
        }

        return cities.size();
    }

    public void add(int sCity, int eCity, int mCost) {

        // 새로운 도로 생성
        Road r = new Road(sCity, eCity, mCost);

        // 각각 city 에 도로 정보 추가
        cities.get(sCity).outcome.add(r);
        cities.get(eCity).income.add(r);
    }

    public int cost(int mHub) {

        // mHub 부터 모든 정점까지의 최소 cost 합과 모든 정점에서 mHub 까지의 최소 cost 합을 더한 값
        return Dijkstra(mHub) + Dijkstra_reverse(mHub);
    }

    private int Dijkstra(int mHub) {

        // city_idx, cost
        HashMap<Integer, sPath> min_cost = new HashMap<>();

        // 그 자신을 향한 min_cost = 0이다.
        sPath p = new sPath(mHub, 0);

        // mHub 부터 탐색 시작
        min_cost.put(mHub, p);

        PriorityQueue<sPath> pq = new PriorityQueue<>((Comparator.comparingInt(o -> o.cost)));

        pq.add(p);

        while(!pq.isEmpty()) {

            sPath curr = pq.poll();

            // 현재 도시
            int curr_city = curr.end;
            int curr_cost = curr.cost;

            // 기존의 min_cost 보다 curr_cost 가 크다면 계산할 이유가 없다.
            // 시간 복잡도를 줄이기 위해 priorityQueue 를 사용해서 이 logic 필요
            // 기존처럼 queue 를 이용한다면 순서대로 탐색하기도 하고 아래 logic 에서
            // 이 부분에 대해 이미 검증하였기에 필요없다.
            if(min_cost.get(curr_city).cost < curr_cost) continue;

            for (Road road : cities.get(curr_city).outcome) {

                sPath path = new sPath(road.end, curr_cost + road.cost);

                // 만약 다음 city 에 대한 min_cost 가 아직 없다면
                if(!min_cost.containsKey(road.end)) {

                    // min_cost 설정
                    min_cost.put(road.end, path);

                    // 해당 path 는 pq 에 추가하여 추가적인 탐색 실행
                    pq.add(path);
                }

                else {

                    // 새로운 길이 기존의 길보다 cost 가 적다면
                    if(path.cost < min_cost.get(road.end).cost) {

                        // min_cost 설정
                        min_cost.put(road.end, path);

                        // 해당 path 는 pq 에 추가하여 추가적인 탐색 실행
                        pq.add(path);
                    }

                    // 크거나 같은 경우는 그냥 넘어간다.
                }
            }
        }

        // 그동안 계산한 모든 min_cost 를 더한다.
        int cost_sum = 0;
        for (sPath value : min_cost.values()) {
            cost_sum += value.cost;
        }

        return cost_sum;
    }

    // 모든 정점에서 특정 정점으로 오는 min_cost 를 계산하기 위해서
    // 모든 road 를 뒤집어 버리고 위의 Dijkstra 의 logic 을 그대로
    // 사용하면 된다.
    private int Dijkstra_reverse(int mHub) {

        // city_idx, cost
        HashMap<Integer, sPath> min_cost = new HashMap<>();

        // 그 자신을 향한 min_cost = 0이다.
        sPath p = new sPath(mHub, 0);

        // mHub 부터 탐색 시작
        min_cost.put(mHub, p);

        PriorityQueue<sPath> pq = new PriorityQueue<>((Comparator.comparingInt(o -> o.cost)));

        pq.add(p);

        while(!pq.isEmpty()) {

            sPath curr = pq.poll();

            // 현재 도시
            int curr_city = curr.end;
            int curr_cost = curr.cost;

            // 기존의 min_cost 보다 curr_cost 가 크다면 계산할 이유가 없다.
            // 시간 복잡도를 줄이기 위해 priorityQueue 를 사용해서 이 logic 필요
            // 기존처럼 queue 를 이용한다면 순서대로 탐색하기도 하고 아래 logic 에서
            // 이 부분에 대해 이미 검증하였기에 필요없다.
            if(min_cost.get(curr_city).cost < curr_cost) continue;

            // edge 를 뒤집었다고 간주하기 때문에 outcome 이 아니라 income 이고
            // end 가 아니라 start 이다.
            for (Road road : cities.get(curr_city).income) {

                sPath path = new sPath(road.start, curr_cost + road.cost);

                // 만약 다음 city 에 대한 min_cost 가 아직 없다면
                if(!min_cost.containsKey(road.start)) {

                    // min_cost 설정
                    min_cost.put(road.start, path);

                    // 해당 path 는 pq 에 추가하여 추가적인 탐색 실행
                    pq.add(path);
                }

                else {

                    // 새로운 길이 기존의 길보다 cost 가 적다면
                    if(path.cost < min_cost.get(road.start).cost) {

                        // min_cost 설정
                        min_cost.put(road.start, path);

                        // 해당 path 는 pq 에 추가하여 추가적인 탐색 실행
                        pq.add(path);
                    }

                    // 크거나 같은 경우는 그냥 넘어간다.
                }
            }
        }

        // 그동안 계산한 모든 min_cost 를 더한다.
        int cost_sum = 0;
        for (sPath value : min_cost.values()) {
            cost_sum += value.cost;
        }

        return cost_sum;
    }
}

class sPath {

    int end;
    int cost;

    public sPath(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

class City {

    // 몇번째 도시인지
    int idx;
    ArrayList<Road> income;
    ArrayList<Road> outcome;

    public City(int idx) {
        this.idx = idx;
        this.income = new ArrayList<>();
        this.outcome = new ArrayList<>();
    }
}

class Road {

    int start;
    int end;
    int cost;

    public Road(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}