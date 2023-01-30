package 모의테스트;

import java.io.*;
import java.util.StringTokenizer;

/*
문제 요약
    A~Z 사이의 대문자로 이루어진 2-D array 가 주어진다.
    각 index 는 여행지를 그리고 대문자는 여행지의 기념품을 의미한다.
    한정된 예산으로 인해 같은 기념품을 2개 이상 구매할 수 없다.
    이동은 가로 혹은 세로로 1칸 움직인다.
    (0,0) 에서 출발할 때 가장 많은 기념품을 구매할 수 있는 경로의
    기념품 수를 구하시오.

1. 실패 이유 분석

    heap 영역에 저장되는 ArrayList, boolean[]를 사용했어서
    각각 route 마다 독립적으로 저장해야할 purchased 정보를
    모든 route 가 공유하는 것이 아닐까?

    단순히 할당하는 것은 참조값만 복제하는 것이다.
    객체 자체를 복제하고자 clone()이용

    여행지가 "A" 같이 하나만 있을 경우 code 에서 범위 제한에
    걸리게 되어 기존의 max = 0 을 갱신하지 못했다.
    이를 방지하기 위해 max = 1 을 기본값으로 변경

    더 이상 움직이지 못하는 경우 max 값을 갱신할 수 없어
    범위 검증 if 문에 max 값 갱신을 위한 else 문 추가

2. 개선점

    굳이 ArrayList<Character> 를 사용할 이유가 있을까?
    char 에서 대문자의 범위가 65~90인 것을 이용해서
    boolean[] 으로 대체함.
    access 는 "char value - 65" 로 대체하였다.

*/

public class test1_Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static char[][] places;
    static int[] x_move = {0,0,1,-1};
    static int[] y_move = {1,-1,0,0};
    static int max = 1;

    static void visit(int x, int y, boolean[] purchased) {

        // 각각의 route 가 같은 purchased 정보 공유 방지
        boolean[] p = purchased.clone();

        // 방문한 도시의 기념품이 이미 구매한 기념품일 경우
        if(p[places[x][y] - 65]) {

            // 구매한 상품 개수 세기
            int num = 0;
            for (boolean b : p) {
                if (b) num++;
            }

            // max 값 갱신
            if(num > max) max = num;
        }

        // 방문한 도시의 기념품이 구매한 상품이 아닐 경우
        else {

            // 기념품 구매
            p[places[x][y] - 65] = true;

            for(int i=0; i<4; i++) {

                // 1칸 이동
                int x_moved = x+x_move[i];
                int y_moved = y+y_move[i];

                // 이동한 index 가 배열의 범위 내에 있을 때
                if(0 <= x_moved && x_moved < places.length && 0 <= y_moved && y_moved < places[0].length) {

                    // 새로운 여행지 방문
                    visit(x_moved, y_moved, p);
                }

                // max 값 갱신
                else {
                    // 구매한 상품 개수 세기
                    int num = 0;
                    for (boolean b : p) {
                        if (b) num++;
                    }

                    // max 값 갱신
                    if(num > max) max = num;
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {

        // t번 반복
        int t = Integer.parseInt(br.readLine());
        for(int i=0; i<t; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            places = new char[r][c];
            for(int j=0; j<r; j++) {
                String temp = br.readLine();
                for(int k=0; k<c; k++) {
                    places[j][k] = temp.charAt(k);
                }
            }

            // 여행 시작
            visit(0,0, new boolean[26]);

            // 결과 입력
            sb.append("#").append(i+1).append(" ").append(max).append("\n");

            // max value 초기화
            max = 0;
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
