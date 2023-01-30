package 비트연산;

import java.io.*;
import java.util.HashSet;

/*
 */
public class No_3 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static long divisor = 1000000007;
    static char[] peoples = {'A', 'B', 'C', 'D'};
    static String s;

    static long solution() {

        for(int i=0; i<s.length(); i++) {

        }

        return 0;
    }

    /*
    before 는 전 단계에서 참여한 동아리원 정보
    idx 는 이번에 사용할 String 의 idx
     */
    static void manage(int before, int idx) {

        // after 에 포함되어야 하는 동아리원
        HashSet<Integer> include = new HashSet<>();
        for(int i=0; i<4; i++) {
            if()
        }
    }

    public static void main(String[] args) throws IOException {

        // t번 반복
        int t = Integer.parseInt(br.readLine());

        for (int i = 1; i <= t; i++) {

            s = br.readLine();
            sb.append("#").append(i).append(" ").append(solution()).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
