package Graph_4.No_13;

import java.io.*;
import java.util.StringTokenizer;

/*
No. 13 프로세서 연결하기

삼성에서 개발한 최신 모바일 프로세서 멕시노스는 가로 N개 x 세로 N개의 cell 로 구성되어 있다.
1개의 cell 에는 1개의 Core 혹은 1개의 전선이 올 수 있다.
멕시노스의 가장 자리에는 전원이 흐르고 있다.
Core 와 전원을 연결하는 전선은 직선으로만 설치가 가능하며,
전선은 절대로 교차해서는 안 된다.

초기 상태로는 아래와 같이 전선을 연결하기 전 상태의 멕시노스 정보가 주어진다.
(멕시노스의 가장자리에 위치한 Core 는 이미 전원이 연결된 것으로 간주한다.)

최대한 많은 Core 에 전원을 연결하였을 경우, 전선 길이의 합을 구하고자 한다.
단, 여러 방법이 있을 경우, 전선 길이의 합이 최소가 되는 값을 구하라.

[제약 사항]
1. 7 ≤  N ≤ 12
2. Core의 개수는 최소 1개 이상 12개 이하이다.
3. 최대한 많은 Core에 전원을 연결해도, 전원이 연결되지 않는 Core 가 존재할 수 있다.

[입력]
입력의 가장 첫 줄에는 총 테스트 케이스의 개수 T가 주어지며 그 다음 줄부터 각 테스트 케이스가 주어진다.
각 테스트 케이스의 첫 줄에는 N값이 주어지며, 다음 N줄에 걸쳐서 멕시노스의 초기 상태가 N x N 배열로 주어진다.
0은 빈 cell 을 의미하며, 1은 core 를 의미하고, 그 외의 숫자는 주어지지 않는다.

[출력]
각 테스트 케이스마다 '#X'를 찍고, 한 칸 띄고, 정답을 출력한다.
(X는 테스트 케이스의 번호를 의미하며 1부터 시작한다.)

주요 아이디어
1. 각 core 의 4방향을 탐색한다면 최악의 경우 4^12 = 16,777,216 번을 탐색한다.
2. 위의 경우 충분히 작은 수 이므로 백트래킹을 통해 완전탐색을 진행하면 된다.
*/
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static int[][] cell;

    static Core[] cores;

    static int max_core;
    static int max_idx;
    static int min_wire;

    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    static void dfs(int idx, int core, int wire) {

        // 현재까지 연결된 core 개수에 앞으로 남은 기회를 모두 더해도
        // 기존의 core_max 개수보다 적다면 탐색할 필요가 없다.
        if (max_core > core + (max_idx + 1 - idx)) return;


        // 모든 core 에서 순회하였을 때
        if (idx == max_idx + 1) {

            // core_max 보다 탐색한 수가 많다면 둘다 갱신
            if (core > max_core) {

                max_core = core;
                min_wire = wire;
            }

            // core 개수는 같지만 사용한 전선의 길이가 더 적다면 갱신
            else if (core == max_core) {
                min_wire = Math.min(min_wire, wire);
            }

            return;

        }

        Core c = cores[idx];

        // 가능하면 core 에 전선을 연결하고 넘어가는 경우
        for (int i = 0; i < 4; i++) {

            // 해당 경로로 전선을 연결할 수 없다면 건너뛴다.
            if (!check(c, i)) continue;

            int temp = fill(c, i, 2);

            // 할수 있다면 해당 경로를 전선으로 연결하고 다음 단계로 넘어간다.
            dfs(idx + 1, core + 1, wire + temp);

            // 다음 방향 탐색을 위해 전선을 해제한다.
            fill(c, i, 0);
        }

        // core 에 전선을 연결하지 않고 다음 단계로 넘어가는 경우
        dfs(idx + 1, core, wire);
    }

    static boolean check(Core c, int d) {
        int x = c.x + dx[d];
        int y = c.y + dy[d];

        while (x >= 0 && y >= 0 && x < cell.length && y < cell.length) {

            // 경로에 cell 이 차있다면 이 방향으로 core 에 전선을 연결할 수 없다.
            if (cell[x][y] != 0) return false;

            x += dx[d];
            y += dy[d];
        }

        return true;
    }

    static int fill(Core c, int d, int content) {

        int x = c.x + dx[d];
        int y = c.y + dy[d];

        int num = 0;

        while (x >= 0 && y >= 0 && x < cell.length && y < cell.length) {

            // 경로를 content 로 채운다.
            cell[x][y] = content;

            // 사용한 wire 개수 갱신
            num++;

            x += dx[d];
            y += dy[d];
        }

        return num;
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());

        for (int i = 1; i <= T; i++) {

            // 기판의 크기
            int n = Integer.parseInt(br.readLine());

            // 기판 상태 init
            cell = new int[n][n];

            // cores 저장할 장소 init
            cores = new Core[12];

            max_idx = 0;

            min_wire = 0;

            max_core = 0;

            // 기판 status 정보 저장
            for (int j = 0; j < n; j++) {

                st = new StringTokenizer(br.readLine());

                for (int k = 0; k < n; k++) {

                    int info = Integer.parseInt(st.nextToken());

                    cell[j][k] = info;

                    // cell info 가 1이라면 core 이다.
                    if (info == 1 && k != 0 && j != 0 && k != n - 1 && j != n - 1) {
                        cores[max_idx++] = new Core(j, k);
                    }
                }

            }

            max_idx--;

            dfs(0, 0, 0);

            sb.append("#").append(i).append(" ").append(min_wire).append("\n");

        }


        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}

class Core {
    int x;
    int y;

    public Core(int x, int y) {
        this.x = x;
        this.y = y;
    }
}


