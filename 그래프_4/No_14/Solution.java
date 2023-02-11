package 그래프_4.No_14;

import java.io.*;

/*
1868. 파핑파핑 지뢰찾기

‘파핑 파핑 지뢰 찾기’라는 유명한 게임이 있다. 이 게임은 RXC 크기의 표를 이용하는 게임인데,
표의 각 칸에는 지뢰가 있을 수도 있고 없을 수도 있다.
표의 각 칸을 클릭했을 때, 그 칸이 지뢰가 있는 칸이라면 ‘파핑 파핑!’이라는 소리와 함께 게임은 끝난다.
지뢰가 없는 칸이라면 변이 맞닿아 있거나 꼭지점이 맞닿아 있는 최대 8칸에 대해 몇 개의 지뢰가 있는지가 0에서 8사이의 숫자로 클릭한 칸에 표시된다.
만약 이 숫자가 0이라면 근처의 8방향에 지뢰가 없다는 것이 확정된 것이기 때문에 그 8방향의 칸도 자동으로 숫자를 표시해 준다.
실제 게임에서는 어떤 위치에 지뢰가 있는지 알 수 없지만, 이 문제에서는 특별히 알 수 있다고 하자.
지뢰를 ‘*’로, 지뢰가 없는 칸을 ‘.’로, 클릭한 지뢰가 없는 칸을 ‘c’로 나타냈을 때 표가 어떻게 변화되는지 나타낸다.

세 번째 예에서는 0으로 표시 될 칸들과 이와 인접한 칸들이 한 번의 클릭에 연쇄적으로 숫자가 표시된 것을 볼 수 있다.
파핑 파핑 지뢰 찾기를 할 때 표의 크기와 표가 주어질 때, 지뢰가 있는 칸을 제외한 다른 모든 칸의 숫자들이 표시되려면 최소 몇 번의 클릭을 해야 하는지 구하는 프로그램을 작성하라.


[입력]
첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에 하나의 정수 N(1 ≤ N ≤ 300) 이 주어진다. 이는 지뢰 찾기를 하는 표의 크기가 N*N임을 나타낸다.
다음 N개의 줄의 i번째 줄에는 길이가 N인 문자열이 주어진다.
이 중 j번째 문자는 표에서 i번째 행 j번째 열에 있는 칸이 지뢰가 있는 칸인지 아닌지를 나타낸다.
‘*’이면 지뢰가 있다는 뜻이고, ‘.’이면 지뢰가 없다는 뜻이다. ‘*’와 ‘.’외의 다른 문자는 입력으로 주어지지 않는다.

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
최소 몇 번의 클릭을 해야 지뢰가 없는 모든 칸에 숫자가 표시될 것인지 출력한다.

주요 아이디어
1. 주변에 지뢰가 없을 때 주변 장소까지 자동으로 숫자가 밝혀진다. -> 애네 먼저 처리해야 한다.
2. 주변에 지뢰가 있다면 동시에 0 의 주변에 있지 않다면 일일이 click 해줘야 한다.
-> 주변에 지뢰가 있다면 1, 없다면, 0, 그 자체가 지뢰라면 -1 로 표시하고
   0은 dfs 로 한번에 방문하고 남은 1은 일일이 클릭한다.
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static int[] dx = {0, 0, 1, -1, 1, -1, 1, -1};
    static int[] dy = {1, -1, 0, 0, 1, -1, -1, 1};

    static int[][] map;
    static boolean[][] visited;

    // 주변에 지뢰가 없을시 방문 방법
    static void dfs(int x, int y) {

        visited[x][y] = true;

        // 주변 장소까지 숫자가 밝혀진다.
        for (int i = 0; i < 8; i++) {

            int x_move = x + dx[i];
            int y_move = y + dy[i];

            if (x_move >= 0 && y_move >= 0 && x_move < map.length && y_move < map.length) {


                // 이동한 곳 주변에도 지뢰가 없을시 주변을 밝힌다.
                if (!visited[x_move][y_move] && map[x_move][y_move] == 0) dfs(x_move, y_move);

                visited[x_move][y_move] = true;
            }
        }
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            int n = Integer.parseInt(br.readLine());

            map = new int[n][n];
            visited = new boolean[n][n];

            int click = 0;

            // info 입력
            for (int i = 0; i < n; i++) {

                String s = br.readLine();

                for (int j = 0; j < n; j++) {

                    char a = s.charAt(j);

                    if (a == '*') {

                        // 지뢰 자리이면 -99 (음수)
                        // 아니면 양수
                        map[i][j] = -99;
                        visited[i][j] = true;
                    }
                }
            }

            // 주변에 지뢰가 있는지 탐색
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    // 지금 장소가 지뢰라면 탐색할 필요 x
                    if (map[i][j] < 0) continue;

                    for (int k = 0; k < 8; k++) {

                        int x = i + dx[k];
                        int y = j + dy[k];

                        // 주변 지뢰가 있다면 1 할당
                        if (x >= 0 && y >= 0 && x < n && y < n) {

                            if (map[x][y] < 0) {
                                map[i][j] = 1;
                                break;
                            }
                        }
                    }
                }
            }

            // 주변에 지뢰가 없는 경우 방문
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (!visited[i][j] && map[i][j] == 0) {
                        dfs(i, j);
                        click++;
                    }
                }
            }

            // 주변에 지뢰가 있는 경우 방문
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (!visited[i][j]) {
                        visited[i][j] = true;
                        click++;
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(click).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}