package 그래프_4.No_16;

import org.w3c.dom.Node;

import java.io.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static StringTokenizer st;

    static int[] x;
    static int[] y;
    static int[] parent;

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for(int t=1; t<=T; t++) {

            int n = Integer.parseInt(br.readLine());

            x = new int[n];
            y = new int[n];
            parent = new int[n];

            st = new StringTokenizer(br.readLine());
            for(int i=0; i<n; i++) {

                // x value
                x[i] = Integer.parseInt(st.nextToken());

                // 초기 parent 값은 자기 자신
                parent[i] = i;
            }

            st = new StringTokenizer(br.readLine());

            for(int i=0; i<n; i++) {

                // y value
                y[i] = Integer.parseInt(st.nextToken());
            }





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
