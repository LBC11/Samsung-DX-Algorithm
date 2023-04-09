package Graph_4.No_17;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
No. 17 [Professional] 고속도로 건설 2

모든 도시를 잇는 고속도로를 건설하려 한다.
그러나 비싼 비용의 문제에 부딪혀, 정부는 최소 비용으로 모든 도시 간을 이동할 수 있게 하고 싶어한다.
또한 하나의 제약이 더 있는데, 언덕 등을 깎지 않는 친환경 건설을 위해 어떤 도시끼리는 직접 도로를 이을 수 없다.
친환경 건설을 위해 이을 수 있는 도로의 목록이 주어질 때, 정부를 도와 모든 도시 간을 잇는 고속도로를 건설하는 최소 비용을 알아내자.

[입력]
첫 줄에 테스트케이스의 개수 T가 주어진다. (1 ≤ T ≤ 8)
각 테스트 케이스의 첫 번째 줄에 도시의 수 N이 주어진다. (2 ≤ N ≤ 50,000)
각 테스트 케이스의 두 번째 줄에 도로 후보의 수 M이 주어진다. (1 ≤ M ≤ 200,000)
각 테스트 케이스의 세 번째 줄부터 M개의 줄에 걸쳐 각 도로 후보의 정보 s, e, c가 주어진다.
s와 e는 도로 후보가 잇는 각 도시의 번호이고, c는 그 도로를 건설하는데 드는 비용이다. (1 ≤ c ≤ 10,000)
항상 모든 도시를 잇는 고속도로를 건설할 수 있는 입력만 주어진다.

[출력]
각 테스트케이스마다 한 줄에 걸쳐, 테스트케이스 수 “#(TC) “를 출력하고,
모든 도시를 잇는 고속도로를 건설하는데 드는 최소 비용을 출력한다.

주요 아이디어
1. 17 번의 풀이와 비슷
2. 각 path 를 cost 의 관점에서 오름차순으로 정렬한다
3. 순차적으로 path 의 start city 와 end city 의 root idx 를 확인한 후
   같지 않다면 union 하고 cost 를 sum 에 더해준다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

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

            parent = new int[n + 1];
            for (int i = 0; i <= n; i++) {

                // 초기 parent 값은 자기 자신
                parent[i] = i;
            }

            edges = new LinkedList<>();

            int m = Integer.parseInt(br.readLine());

            for (int i = 0; i < m; i++) {

                st = new StringTokenizer(br.readLine());

                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                edges.add(new Edge(start, end, cost));
            }

            // edge 를 오름차순으로 sort
            edges.sort(Comparator.comparing(o -> o.cost));

            int result = 0;
            for (Edge edge : edges) {

                int p_s = find(edge.start);
                int p_e = find(edge.end);

                // parent 가 다르다는 애기는 아직 서로 연결이 되어 있지 않다는 의미
                if (p_s != p_e) {
                    union(edge.start, edge.end);
                    result += edge.cost;
                }
            }

            sb.append("#").append(t).append(" ").append(result).append("\n");

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
    int cost;

    public Edge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}
