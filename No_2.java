import java.io.*;
import java.util.StringTokenizer;

/*
정수 N, M 이 주어질 때, M의 이진수 표현의 마지막 N 비트가 모두 1로 켜져 있는지 아닌지를 판별하여 출력하라.
 */
public class No_2 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static String solution(int n, int m) {

        for(int i=n-1; i>=0; i--) {
            if(((m & (1<<i)) >> i) != 1) {
                return "OFF";
            }
        }

        return "ON";
    }

    public static void main(String[] args) throws IOException {

        // t번 반복
        int t = Integer.parseInt(br.readLine());

        StringTokenizer st;
        for (int i = 1; i <= t; i++) {
            st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            sb.append("#").append(i).append(" ").append(solution(n,m)).append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
