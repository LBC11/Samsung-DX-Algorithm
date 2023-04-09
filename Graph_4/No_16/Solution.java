package Graph_4.No_16;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
1251. [S/W 문제해결 응용] 4일차 - 하나로

당신은 인도네시아 내의 N개의 섬들을 연결하는 교통시스템 설계 프로젝트인 ‘하나로’를 진행하게 되었습니다.
하나로 프로젝트는 천해의 자연을 가진 인도네시아의 각 섬 간 교통이 원활하지 않아 관광 산업의 발전을 저해하는 요소를 줄이고 부가 가치를 창출하고자 진행하는 프로젝트입니다.
본 프로젝트에서는 인도네시아 내의 모든 섬을 해저터널로 연결하는 것을 목표로 합니다.
해저터널은 반드시 두 섬을 선분으로 연결하며, 두 해저 터널이 교차된다 하더라도 물리적으로는 연결되지 않는 것으로 가정합니다.
아래 그림 1과 같은 경우, A섬에서 D섬으로는 연결되었지만 A섬으로부터 B섬, C섬에는 도달 할 수 없기 때문에 연결되지 않은 것입니다.

위와 같은 방법을 통해 인도네시아 내의 모든 섬들을 연결해야 하는 프로젝트입니다.
그림 3에서 B와 A처럼 직접적으로 연결된 경우도 있지만, B와 C처럼 여러 섬에 걸쳐 간접적으로 연결된 경우도 있습니다.
다만 인도네시아에서는 해저터널 건설로 인해 파괴되는 자연을 위해 다음과 같은 환경 부담금 정책이 있습니다.
- 환경 부담 세율(E)과 각 해저터널 길이(L)의 제곱의 곱(E * L^2)만큼 지불
총 환경 부담금을 최소로 지불하며, N개의 모든 섬을 연결할 수 있는 교통 시스템을 설계하시오.
64비트 integer 및 double로 처리하지 않을 경우, overflow가 발생할 수 있습니다 (C/C++ 에서 64비트 integer는 long long 으로 선언).

[입력]
가장 첫 줄은 전체 테스트 케이스의 수이다.
각 테스트 케이스의 첫 줄에는 섬의 개수 N이 주어지고 (1≤N≤1,000),
두 번째 줄에는 각 섬들의 정수인 X좌표, 세 번째 줄에는 각 섬들의 정수인 Y좌표가 주어진다 (0≤X≤1,000,000, 0≤Y≤1,000,000).
마지막으로, 해저터널 건설의 환경 부담 세율 실수 E가 주어진다 (0≤E≤1).

[출력]
각 테스트 케이스의 답을 순서대로 출력하며, 각 케이스마다 줄의 시작에 “#C”를 출력하여야 한다. 이때 C는 케이스의 번호이다.
같은 줄에 빈칸을 하나 두고, 주어진 입력에서 모든 섬들을 잇는 최소 환경 부담금을 소수 첫째 자리에서 반올림하여 정수 형태로 출력하라.

크루스칼 알고리즘(Kruskal Algorithm)
그래프 내의 모든 정점들을 가장 적은 비용으로 연결하기 위해 사용됩니다.

방법
1. 간선의 가중치를 하나의 자료구조에 넣고 sort 한다.
2. sort 된 자료구조에서 순서대로 간선을 꺼내와 둘의 root idx 가 다르다면 간선을 사용한다.
3. 사용된 간선의 두 정점 중 큰 idx 를 가진 vertex 의 root idx 를 작은 정점의 idx 로 갱신한다.
4. 사용된 간선의 개수가 정점의 개수 - 1 이 될 때까지 반복한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static long[] x;
    static long[] y;
    static int[] parent;

    static LinkedList<Edge> edges;

    // parent node idx 찾아주는 함수
    static int find(int x) {

        // 경로 압축을 위해서
        if (parent[x] == x) return x;

        return parent[x] = find(parent[x]);
    }

    // 서로의 parent 가 같다면 연결되었다고 간주한다.
    static void union(int a, int b) {

        int p_a = find(a);
        int p_b = find(b);

        if (p_a > p_b) parent[p_a] = p_b;
        else parent[p_b] = p_a;
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            int n = Integer.parseInt(br.readLine());

            x = new long[n];
            y = new long[n];
            parent = new int[n];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {

                // x value
                x[i] = Integer.parseInt(st.nextToken());

                // 초기 parent 값은 자기 자신
                parent[i] = i;
            }

            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < n; i++) {

                // y value
                y[i] = Integer.parseInt(st.nextToken());
            }

            double percent = Double.parseDouble(br.readLine());

            edges = new LinkedList<>();

            // edges 계산
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {

                    long distance = (long) (Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
                    edges.add(new Edge(i, j, distance));
                }
            }

            // edge 를 오름차순으로 sort
            edges.sort(Comparator.comparingLong(o -> o.distance));

            long result = 0;
            for (Edge edge : edges) {

                int p_s = find(edge.start);
                int p_e = find(edge.end);

                // parent 가 다르다는 애기는 아직 서로 연결이 되어 있지 않다는 의미
                if (p_s != p_e) {
                    union(edge.start, edge.end);
                    result += edge.distance;
                }
            }

            sb.append("#").append(t).append(" ").append(Math.round(result * percent)).append("\n");

        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}

class Edge {

    int start;
    int end;
    long distance;

    public Edge(int start, int end, long distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }
}
