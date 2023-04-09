package Hash_8.No_30;

import java.io.*;
import java.util.StringTokenizer;
/*
7091. 은기의 아주 큰 그림

바쁜 일정이 지난 은기는 새로운 취미로 미술 학원을 다니기로 하였다.
마우스가 아닌 붓을 잡은 은기는, 미술 실력이 많이 부족하였다.
그래서 선생님이 그린 N×M 크기의 흑백 그림을 따라 그리며 실력을 쌓고 있다.

그러던 어느 날, 은기는 꿈을 꾸었다. 꿈에서 H×W 크기의 흑백 그림 하나를 보았다.
은기는 다음 날, 선생님의 그림 안에 꿈에서 보았던 그림이 몇 번 등장하는지 궁금하였다.
다음의 예시를 보자. 위쪽은 은기가 꿈에서 본 그림이고, 아래쪽은 선생님이 그린 그림이다.

oo
xx

ooo
xxx
ooo

은기가 꿈에서 본 그림과 선생님의 그림 정보가 주어질 때,

선생님의 그림에 은기가 꿈에서 본 그림이 등장하는 횟수를 계산하는 프로그램을 작성하라.

[입력]

첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 네 개의 정수 H, W, N, M ( 1 ≤ H ≤ N ≤ 2000, 1 ≤ W ≤ M ≤ 2000 )가 공백으로 구분되어 주어진다.
다음 H개의 줄에 은기가 꿈에서 본 그림의 정보가 주어진다.
다음 N개의 줄에 선생님이 그린 그림의 정보가 주어진다.
그림은 ‘x’ 또는 ‘o’로만 이루어져 있다.

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고, 답을 한 줄에 하나씩 출력한다.

주요 아이디어
1. 라빈 카프 알고리즘

-> 재대로 logic 이해하지 못했다... 공부 더하자!!!
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();
    static StringTokenizer st;

    static final long Hash_size = 1 << 30;
    static final long DIV = Hash_size - 1;

    static int calMul(int num, int shift) {

        long rev = 1;
        for (int i = 1; i < num; i++) {
            rev = (rev << shift) + rev;
        }

        return (int) (rev & DIV);
    }

    static int getHash(int[] arr, int num, int shift) {

        long hash = 0;
        for(int i=0; i<num; i++) {
            hash = (hash << shift) + hash + arr[i];
        }

        return (int) (hash & DIV);
    }

    static int getNext(int prev, int sub, int mul, int add, int shift) {
        long hash = prev - ((long) sub * mul);
        hash = (hash << shift) + hash + add;
        return (int) (hash & DIV);
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            int[][] student = new int[h][w];
            int[][] teacher = new int[n][m];

            for (int i = 0; i < h; i++) {

                String s = br.readLine();
                for (int j = 0; j < w; j++) {

                    if (s.charAt(j) == 'o') student[i][j] = 0;
                    else student[i][j] = 1;
                }
            }

            for (int i = 0; i < n; i++) {

                String s = br.readLine();
                for (int j = 0; j < m; j++) {

                    if (s.charAt(j) == 'o') teacher[i][j] = 0;
                    else teacher[i][j] = 1;
                }
            }

            // hash 계산하는 데 필요한 장소
            int[][] temp = new int[2000][2000];

            // teacher hash 저장하는 장소
            int[][] teacher_hash = new int[2000][2000];

            // get my hash
            for(int i=0; i<h; i++) {
                temp[0][i] = getHash(student[i], w, 4);
            }
            int student_hash = getHash(temp[0], h, 5);

            int mul_c = calMul(w, 4);
            int mul_r = calMul(h, 5);
            for(int i=0; i<n; i++) {
                temp[0][i] = getHash(teacher[i], w, 4);
                for(int j=1; j<=m-w; j++) {
                    temp[j][i] = getNext(temp[j-1][i], teacher[i][j-1], mul_c, teacher[i][j+w-1], 4);
                }
            }
            for(int i=0; i<=m-w; i++) {
                teacher_hash[0][i] = getHash(temp[i], h, 5);
                for(int j=1; j<=n-h; j++) {
                    teacher_hash[j][i] = getNext(teacher_hash[j-1][i], temp[i][j-1], mul_r, temp[i][j+h-1], 5);
                }
            }

            int cnt = 0;
            for(int i =0; i<=n-h; i++) {
                for(int j=0; j<=m-w; j++) {

                    // teacher hash 값이 student hash 와 같다면
                    if(teacher_hash[i][j] == student_hash) cnt++;
                }
            }

            sb.append("#").append(t).append(" ").append(cnt).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
