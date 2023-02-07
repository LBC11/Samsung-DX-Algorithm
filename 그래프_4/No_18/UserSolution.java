package 그래프_4.No_18;

public class UserSolution {

    int[][] graph;
    boolean[] visited;

    public void dfs_init(int N, int[][] path) {

        graph = new int[100][100];
        visited = new boolean[100];

        // edge 연결
        for (int[] p : path) {
            graph[p[0]][p[1]] = 1;
        }
    }

    public int dfs(int N) {

        visited[N] = true;

        for (int next = 1; next <= 100; next++) {

            if (!visited[next] && (graph[N][next] == 1)) {
                if(next > N) {
                    return N;
                }
                else {
                    dfs(next);
                }
            }
        }
        return -1;
    }
}
