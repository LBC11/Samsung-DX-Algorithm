package 그래프_4.No_13;

import java.io.*;
import java.util.StringTokenizer;

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


