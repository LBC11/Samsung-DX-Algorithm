package Pro.No_3;

import java.util.Comparator;
import java.util.PriorityQueue;

class UserSolution {

    CityTaxes cityTaxes = new CityTaxes();
    PredictTax predictTax = new PredictTax();
    OfficialQueue officialQueue = new OfficialQueue();

    void init(int N, int M) {

        cityTaxes.init(N);
        predictTax.init(N);
        officialQueue.init(M);
    }

    void destroy() {
    }

    int order(int tStamp, int mCityA, int mCityB, int mTax) {

        officialQueue.timeFlow(tStamp - 1);
        cityTaxes.reserveTax(mCityB, tStamp + Math.abs(mCityA - mCityB), mTax);
        officialQueue.updatePrediction(tStamp + Math.max(0, Math.abs(mCityA - mCityB)), mCityB, mTax);
        return check(tStamp);
    }

    int check(int tStamp) {

        officialQueue.timeFlow(tStamp);
        return officialQueue.totCapitalTax;
    }

    // 도시의 곡물 저장, 납부 시스템
    class CityTaxes {

        PriorityQueue<Tax>[] cities;

        void init(int N) {
            cities = new PriorityQueue[N];

            for (int i = 0; i < N; i++) {
                cities[i] = new PriorityQueue<>(Comparator.comparingInt(o -> o.timeStamp));
            }
        }

        // tax 저장 함수
        void reserveTax(int city, int time, int tax) {

            cities[city].add(new Tax(time, tax));
        }

        // tax 가져가기
        int takeTax(int city, int time) {

            int ans = 0;

            // 해당 time 이전까지의 모든 tax 합 return
            while (!cities[city].isEmpty() && cities[city].peek().timeStamp < time) {
                ans += cities[city].poll().tax;
            }

            return ans;
        }
    }

    class Tax {

        int timeStamp;
        int tax;

        public Tax(int timeStamp, int tax) {
            this.timeStamp = timeStamp;
            this.tax = tax;
        }
    }


    class PredictTax {

        int[] addedTaxes;
        int[] removedTaxes;
        boolean[] isDispatched;

        PriorityQueue<City> pq;

        void init(int N) {
            addedTaxes = new int[N];
            removedTaxes = new int[N];
            isDispatched = new boolean[N];

            pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.tax));
        }

        int top() {

            // 해당 도시에 파견된 관리가 있거나 현재의 predictedTax 양과 다르다면 넘어간다.
            while (!pq.isEmpty() && (isDispatched[-pq.peek().cityIdx] || pq.peek().tax != addedTaxes[-pq.peek().cityIdx] - removedTaxes[-pq.peek().cityIdx])) {
                pq.poll();
            }

            // pq 가 비어있거나 맨 위의 predictedTax 가 0이면
            if (pq.isEmpty() || pq.peek().tax == 0) return 0;

            // 가장 세금이 많이 모여있는 cityIdx
            return -pq.peek().cityIdx;
        }

        void dispatch(int city) {
            isDispatched[city] = true;
        }

        void terminationDispatch(int city, int tax) {

            isDispatched[city] = false;
            removedTaxes[city] += tax;
            pq.add(new City(-city, addedTaxes[city] - removedTaxes[city]));
        }

        void updatePrediction(int city, int tax) {

            addedTaxes[city] += tax;
            if (!isDispatched[city]) {
                pq.add(new City(-city, addedTaxes[city] - removedTaxes[city]));
            }
        }
    }

    class City {

        int cityIdx;
        int tax;

        public City(int cityIdx, int tax) {
            this.cityIdx = cityIdx;
            this.tax = tax;
        }
    }

    class OfficialQueue {

        // 파견되지 않고 대기 중인 관리의 수
        int officialEmpty;

        // 수도에 납부된 전체 곡물의 양
        int totCapitalTax;

        PriorityQueue<TimeCity> pq;

        void init(int M) {
            officialEmpty = M;
            totCapitalTax = 0;
            pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.timestamp));
        }

        void timeFlow(int timestamp) {

            while (!pq.isEmpty() && pq.peek().timestamp <= timestamp) {
                int time = pq.peek().timestamp;
                int cityIdx = pq.peek().city.cityIdx;
                int tax = pq.peek().city.tax;

                pq.poll();

                // 시간 t에 관리 한 명이 도시 x의 곡물 y를 가지고 수도로 돌아옴
                if (tax >= 0) {
                    officialEmpty += 1;
                    totCapitalTax += tax;
                    predictTax.terminationDispatch(cityIdx, tax);
                }

                // 시간 t에 관리 한 명이 도시 x에 도착함
                else if (cityIdx >= 0) {

                    int mTax = cityTaxes.takeTax(cityIdx, time);
                    pq.add(new TimeCity(time + cityIdx, new City(cityIdx, mTax)));
                } else {

                    // 시간 t에 도시 -x의 예측되는 곡물의 양이 -y 만큼 증가함
                    predictTax.updatePrediction(-cityIdx, -tax);
                }

                if (pq.isEmpty() || pq.peek().timestamp > time) {
                    while (officialEmpty > 0 && predictTax.top() > 0) {

                        int city = predictTax.top();
                        predictTax.dispatch(city);
                        pq.add(new TimeCity(city + time, new City(city, -1)));
                        officialEmpty -= 1;
                    }
                }
            }
        }

        // 시간 t Stamp 가 되면, 관리가 출발해서 도시 cityID에 도착하였을 때,
        // 예상되는 곡물의 양을 기존에서 mTax 증가시킴
        void updatePrediction(int tStamp, int cityID, int mTax) {
            pq.add(new TimeCity(tStamp, new City(-cityID, -mTax)));
        }
    }

    class TimeCity {

        int timestamp;
        City city;

        public TimeCity(int timestamp, City city) {
            this.timestamp = timestamp;
            this.city = city;
        }
    }
}



