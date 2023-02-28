package 트라이_11.No_42;

import java.io.*;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    static Node root;

    // String s을 trie 에 insert
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

    // trie 안에 String s 가 존재하는지 check
    static boolean search(String s) {

        Node node = root;
        for (int i = 0; i < s.length(); i++) {

            int idx = s.charAt(i) - 'a';

            // 해당 idx 의 child node 가 없다면 String s 가 존재하지 않다.
            if (node.next[idx] == null) return false;
            else {
                node = node.next[idx];
            }
        }

        // 마지막 node 가 존재해도 terminal 인지에 따라 존재하는지 결정된다.
        return node.isTerminal;
    }

    // trie 에서 정렬되었을 때 k번째 문자열 찾는 함수
    static void find(int k) {

        Node node = root;

        if (k > node.cnt) {
            sb.append("none");
            return;
        }

        while (true) {

            if (node.isTerminal) k--;

            if (k == 0) return;

            int temp = k;

            for (int i = 0; i < 26; i++) {

                // k번째 문자열을 찾을 때까지(temp 가 0이 될때까지)
                if (node.next[i] != null) {
                    temp -= node.next[i].cnt;

                    // temp 가 0이하라는 말은 찾으려는 k번째 문자열이
                    // 이 node 방향으로 이어져있다는 말이다.
                    if (temp <= 0) {

                        // 다음 node 에서 사용할 k
                        k = temp + node.next[i].cnt;

                        sb.append((char) (i + 'a'));

                        node = node.next[i];
                        break;
                    }
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

            for (int i = s.length(); i > 0; i--) {
                for (int j = 0; j < i; j++) {

                    String temp = s.substring(j, i);

                    // String temp 가 trie 에 존재하지 않으면
                    if(!search(temp)) {

                        // trie 에 insert 한다.
                        insert(temp);
                    }
                }
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

    int cnt;
    boolean isTerminal;
    Node[] next;

    public Node() {
        this.cnt = 0;
        this.isTerminal = false;
        this.next = new Node[26];
    }
}
