package Graph_4.No_19;
/*
기초 BFS 연습

void bfs_init (int map_size, int map[10][10])
행과 열의 크기가 map_size(1 <= map_size <= 10) 인 지도가 주어진다.
지도는 0-base 인 배열로 표현되며 map[10][10] 으로 주어진다.
배열의 각 값들은 0 또는 1이며 '0'은 길을, '1'은 벽을 의미한다.

int bfs(int x1, int y1, int x2, int y2)
시작점 x1, y1 좌표가 주어지고 도착점 x2, y2가 주어지면 시작점에서 도착점까지
가는데 최단 거리로 갈 경우 몇 번을 이동하면 되는지 이동 횟수를 계산해서 return 해야 한다.
x는 열이고 y는 행임을 주의해야 한다. 그리고 좌상 좌표는 x=1, y=1 이다.
만약 만약 갈 수 있는 방법이 없을 경우 -1을 return 해야 한다.
시작점과 도착점이 같은 경우는 없다.

주요 아이디어
1. bfs 를 이용한 간단한 최단거리 구하는 문제
2. dfs 로 풀려고 하면 시간 초과가 발생하는 데
   그 이유는 잘못된 route 의 방향으로 들어섰음에도
   끝까지 가야지 탐색이 종료되기 떄문이다.

 */
class UserSolution {

    int[][] m;

    int[] x_m = {0, 0, -1, 1};
    int[] y_m = {1, -1, 0, 0};

    void bfs_init(int map_size, int[][] map) {

        m = map;
    }

    int bfs(int x1, int y1, int x2, int y2) {

        // index 와 실제 좌표값이 1씩 차이나기 때문에
        x1--;
        y1--;
        x2--;
        y2--;

        int[][] visited = new int[m.length][m.length];

        for(int i=0; i<m.length; i++) {
            for(int j=0; j<m.length; j++) {
                visited[i][j] = m[i][j];
            }
        }

        // distance 를 저장할 공간
        int[][] dist = new int[m.length][m.length];

        int[][] queue = new int[m.length * m.length][2];
        int head = 0;
        int tail = 0;


        // 문제 잘 읽어보면 x가 열이고 y가 행이라서 반대로 할당해주었다.
        queue[tail][0] = y1;
        queue[tail++][1] = x1;

        while (head != tail) {

            int x_now = queue[head][0];
            int y_now = queue[head++][1];

            if (x_now == y2 && y_now == x2) {

                return dist[x_now][y_now];
            }

            for (int i = 0; i < 4; i++) {
                int x = x_now + x_m[i];
                int y = y_now + y_m[i];

                // x, y의 idx 가 map 의 범위 내이고 x,y를 방문한 적이 없다면
                if (x >= 0 && y >= 0 && x < m.length && y < m.length && visited[x][y] != 1) {

                    // 방문표시
                    // queue 에서는 먼저 들어간 애가 먼저 나온다는 것이
                    // 보장되기에 여기서 방문 표시를 할 수 있다.
                    visited[x][y] = 1;

                    dist[x][y] = dist[x_now][y_now] + 1;

                    queue[tail][0] = x;
                    queue[tail++][1] = y;
                }
            }
        }

        return -1;
    }
}