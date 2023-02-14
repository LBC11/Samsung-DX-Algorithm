package 그래프_4.No_17;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
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

            parent = new int[n];
            for (int i = 0; i < n; i++) {

                // 초기 parent 값은 자기 자신
                parent[i] = i;
            }

            edges = new LinkedList<>();

            int m = Integer.parseInt(br.readLine());

            for (int i = 0; i < m; i++) {

            }

            // edges 계산
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
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

            sb.append("#").append(t).append(" ").append(Math.round(result)).append("\n");

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
