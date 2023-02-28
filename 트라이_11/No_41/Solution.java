package 트라이_11.No_41;

import java.io.*;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static Node root;

    // Trie 에 문자열 넣기
    static void insert(String s) {

        Node node = root;

        for (int i = 0; i < s.length(); i++) {

            int idx = s.charAt(i) - 'a';

            if (node.next[idx] == null) {
                node.next[idx] = new Node();
            }
            node.cnt++;
            node = node.next[idx];
        }

        node.cnt++;
        node.isTerminal = true;
    }

    static void find(int k) {
        Node node = root;

        // root 의 접미어 개수보다 k 가 클 때
        if (k > node.cnt) {
            sb.append("none");
            return;
        }

        while (true) {

            // 현재 위치가 leaf 면 k갱신
            if (node.isTerminal) k--;

            // k번째 접미어 sb 에 더했으면 반복문 종료
            if (k == 0) break;

            // i 번째 문자열을
            int temp = k;
            for (int i = 0; i < 26; i++) {
                k = temp;

                if (node.next[i] != null) temp -= node.next[i].cnt;

                // temp 가 k 번째 문자열이 아니면 leaf node 여도 temp 는 양수이다.
                if (temp <= 0) {
                    node = node.next[i];
                    sb.append((char) (i + 'a'));
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            root = new Node();

            int k = Integer.parseInt(br.readLine());
            String s = br.readLine();

            for (int i = s.length() - 1; i >= 0; i--) {
                insert(s.substring(i));
            }

            sb.append("#").append(t).append(" ");
            find(k);
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}

class Node {

    // 해당 node 를 거쳐가는 단어 개수
    int cnt;
    boolean isTerminal;
    Node[] next;

    public Node() {
        this.cnt = 0;
        this.isTerminal = false;

        // 소문자 개수수
        next = new Node[26];
    }
}