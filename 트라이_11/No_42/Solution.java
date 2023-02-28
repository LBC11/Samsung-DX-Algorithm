package 트라이_11.No_42;

import java.io.*;

/*
1257. [S/W 문제해결 응용] 6일차 - K번째 문자열

영어 소문자로 된 문자열이 있다.
이 문자열의 부분문자열은 문자열의 두 위치를 골라서, 이 사이의 연속한 문자열을 뽑아낸 것이다.
두 위치가 같을 때는 길이가 1인 부분 문자열이 된다.

예를 들어, 문자열 love의 모든 부분 문자열은 l, o, v, e, lo,ov, ve, lov, ove, love이다.
또 다른 예로, 문자열 food의 부분 문자열은 f, o, d, fo, oo,od, foo, ood, food가 있다. 동일한 문자열 o가 두번 나오지만, 중복을 제거한 것에 유의하자.
이 문자열에 대해서 사전 순서로 정렬을 하는 것을 고려해보자.
두 문자열을 왼쪽부터 오른쪽으로 비교해나가면서, 처음으로 다른 글자가 나왔을 때 알파벳 순으로 먼저 나오는 문자가 있는 쪽이 순서가 앞이다.
다른 글자가 나오기 전에 한 문자열이 끝난다면 이 문자열이 순서가 앞이다.

순서	love의 부분 문자열	food의 부분 문자열
1	e	                d
2	l	                f
3	lo	                fo
4	lov	                foo
5	love	            food
6	o	                o
7	ov	                od
8	ove	                oo
9	v	                ood
10	ve

문자열과 정수 K가 주어지고, 이 문자열의 부분 문자열들을 사전 순서대로 나열하였을 때 K번째에 오는 문자열을 출력하는 프로그램을 작성하시오.
다음의 입출력 조건을 준수하시오.

[입력]
가장 첫 줄은 전체 테스트 케이스의 수이다.
각 테스트 케이스는 정수 K 하나가 쓰여진 줄 다음에 영어 소문자로 된 문자열이 쓰인 줄로 이루어진다.
문자열의 길이는 최대 400이다.

[출력]
각 테스트 케이스마다, 첫 줄에는 “#C”를 출력해야 하는데 C는 케이스 번호이다.
같은 줄에 빈 칸을 하나 사이에 두고 이어서 사전 순서로 K번째 나오는 부분 문자열을 출력한다.
만약 K번째 문자열이 존재하지 않는다면, “none”을 출력한다.

주요 아이디어
1. Trie 구조 이용
2. 문자열이 소문자만 사용되었다는 것을 이용해 child = new Node[26] 을 통해 자동으로 정렬되는 효과를 가진다.
2. Node 마다 거쳐간 단어의 개수를 저장하여 k번째 문자열을 손쉽게 구한다.
4. 41 번의 k번째 접미어와는 다르게 root node 를 제외한 node 에서도 degree 의 크기가 2이상일 수 있다는 것을 유의해야 한다.
 */
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

        // k번째 접미어를 찾아 sb 에 더할 때까지 반복
        while (k > 0) {

            // 지금까지 지나친 접미어의 개수를 뺀 값
            int temp = k;

            for (int i = 0; i < 26; i++) {

                // k번째 문자열을 찾을 때까지(temp 가 0이 될때까지)
                if (node.next[i] != null) {
                    temp -= node.next[i].cnt;

                    // temp 가 0이하라는 말은 찾으려는 k번째 문자열이
                    // 이 node 방향으로 이어져있다는 말이다.
                    if (temp <= 0) {

                        // 다음 node 에서 사용할 k
                        // 41번의 k번째 접미어를 구하는 경우에는 접미어이기에
                        // root node 를 제외한 다른 node 의 degree(child node 개수)는 1이하이다.
                        // 그래서 temp 가 0이하인 방향으로 끝까지 가기만 해도 된다.
                        // leaf node 면 0 아니면 1
                        // 하지만 이 문제의 경우 root node 를 제외한 node 에서도 root 와 같이
                        // degree 가 2 이상을 가질 수 있기때문에 temp 을 통해서 방향만 설정하면 안 된다.
                        // next node 를 새로운 root 로 생각하고 다시 한번 root 에서 탐색하는 것처럼 반복해야 하기에
                        // k를 여기서 지금까지 지나친 문자열의 개수를 제외한 수로 갱신해주어야 한다.
                        k = temp + node.next[i].cnt;

                        sb.append((char) (i + 'a'));

                        node = node.next[i];
                        break;
                    }
                }
            }

            // current node 가 terminal 이라면
            if (node.isTerminal) k--;
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
                    if (!search(temp)) {

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
