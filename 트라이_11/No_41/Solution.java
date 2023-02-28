package 트라이_11.No_41;

import java.io.*;


/*
1256. [S/W 문제해결 응용] 6일차 - K번째 접미어

영어 소문자로 된 문자열이 있다. 문자열의 길이가 n일 때 접미어들은 문자열의 길이만큼 존재한다.
“monster” 문자열의 접미어들 중에서 사전적 순서로 4번째에 오는 접미어는 “onster 이다.
K값과 문자열이 주어지면 접미어들 중 사전적 순서로 K번째에 해당하는 접미어를 찾아서 출력하시오.

[입력]
가장 첫 줄은 전체 테스트 케이스의 수이다.
10개의 테스트 케이스가 표준 입력을 통하여 주어진다.
각 테스트 케이스는 정수 K 하나가 쓰여진 줄 다음에 영어 소문자로 된 문자열이 쓰인 줄로 이루어진다.
문자열의 길이는 최대 400 이다.

[출력]
각 테스트 케이스마다, 첫 줄에는 “#C”를 출력해야 하는데 C는 케이스 번호이다.
같은 줄에 빈 칸을 하나 사이에 두고 이어서 사전 순서로 K번째 나오는 부분 문자열을 출력한다.
만약 K번째 문자열이 존재하지 않는다면, “none”을 출력한다.

주요 아이디어
1. Trie 구조 이용
2. 문자열이 소문자만 사용되었다는 것을 이용해 child = new Node[26] 을 통해 자동으로 정렬되는 효과를 가진다.
2. Node 마다 거쳐간 단어의 개수를 저장하여 k번째 문자열을 손쉽게 구한다.
 */
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

        // k번째 접미어를 찾아 sb 에 더할 때까지 반복
        while (k > 0) {

            // 지금까지 지나친 접미어의 개수를 뺀 값
            int temp = k;
            for (int i = 0; i < 26; i++) {
                k = temp;

                // k번째 문자열을 찾을 때까지(temp 가 음수가 될때까지)
                if (node.next[i] != null) {
                    temp -= node.next[i].cnt;

                    // temp 가 0이하라는 말은 찾으려는 k번째 문자열이
                    // 이 node 방향으로 이어져있다는 말이다.
                    if (temp <= 0) {
                        node = node.next[i];
                        sb.append((char) (i + 'a'));
                        break;
                    }
                }
            }

            // 현재 위치가 leaf 면 k갱신
            if (node.isTerminal) k--;
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