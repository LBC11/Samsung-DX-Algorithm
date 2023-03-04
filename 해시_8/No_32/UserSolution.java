package 해시_8.No_32;

import java.util.Arrays;

/*
문자열 암호화

[문제 설명]
문자열을 주어진 암호화 규칙에 따라서 변환하는 프로그램을 구현하고자 한다.
이 규칙은 전체 문자열 중 부분 문자열 A 를 B 로 변환하는 규칙이다.
규칙을 적용하는 방법은 다음과 같다.

전체 문자열을 처음 index 부터 탐색하여, 부분 문자열이 A 인지 판단한다.
A 가 아닐 경우 다음 index 의 부분문자열을 탐색한다.
A 일 경우 해당 index 의 부분문자열을 B 로 변환하고, index 를 A 의 길이만큼 증가시킨 후 탐색을 계속한다.

당신은 규칙이 주어지는 순서에 따라 문자열을 변환하여 최종 문자열을 구해야 한다.

void init(int N, char init_string[])
각 테스트 케이스의 처음에 호출된다.
변환을 할 원본 문자열이 주어진다.

Parameters
N : 원본 문자열의 길이
init_string[] : 원본 문자열.

int change(char string_A[], char string_B[])
string_A 와 일치하는 부분문자열을 string_B로 바꾼다.

Parameters
string_A : 규칙을 이루는 문자열에서 찾아 변환시킬 문자열이다.
string_B : string_A 를 대체할 문자열
(string_A, string_B) 의 각 길이는 3이다.

Returns
num : 변경한 부분 문자열의 개수를 반환한다.

void result(char ret[])
최종 문자열 상태를 ret 배열에 반환한다.
이 함수는 각 테스트케이스 마지막에 한번만 호출된다.

[제약사항]
1. 각 테스트 케이스 시작 시 init() 함수가 호출된다.
2. 문자열의 길이는 최대 50,000 이다.
3. 각 테스트 케이스에서 문자열은 영어 소문자로 이루어져 있으며 항상 ’\0’ 으로 끝난다.
4. 변환할 문자열의 길이는 항상 3 이다.
5 각 테스트 케이스에서 change() 함수가 호출되는 횟수는 최대 50,000 이다. result() 함수는 1 번만 호출 된다.

[입출력]
입출력은 제공되는 Main 부분의 코드에서 처리하므로 User Code 부분의 코드에서는 별도로 입출력을 처리하지 않는다.
 */
class UserSolution {
    final int MAX_N = 50000;
    final int HASH_SIZE = 26 * 26 * 26;
    char[] str;
    Node[] nodes = new Node[MAX_N];
    Node[] strHash = new Node[HASH_SIZE];

    void init(int n, char[] init_string) {
        str = Arrays.copyOfRange(init_string, 0, n);
        for (int i = 0; i < HASH_SIZE; ++i) {
            strHash[i] = new Node(-1, i, null, null);
        }
        for (int i = n - 3; i >= 0; --i) {

            // n-3 ~ n 까지의 str.charAt value 의 hash 값
            int hash = getHash(str, i);

            // node init
            nodes[i] = new Node(i, hash, null, null);

            //
            nodes[i].prev = strHash[hash];
            nodes[i].next = strHash[hash].next;
            if (strHash[hash].next != null) strHash[hash].next.prev = nodes[i];
            strHash[hash].next = nodes[i];
        }
    }

    int getHash(char[] a, int i) {
        return (a[i] - 'a') * 26 * 26 + (a[i + 1] - 'a') * 26 + (a[i + 2] - 'a');
    }

    int change(char[] string_A, char[] string_B) {
        int hashA = getHash(string_A, 0);
        int cnt = 0;

        int idx;
        Node node = strHash[hashA].next;

        while (node != null) {
            cnt++;
            idx = node.idx;
            str[idx] = string_B[0];
            str[idx + 1] = string_B[1];
            str[idx + 2] = string_B[2];

            while (node != null && node.idx - idx <= 2) node = node.next;

            for (int i = idx - 2; i <= idx + 2; ++i) {
                if (i < 0 || i >= str.length - 2) continue;

                int hash = getHash(str, i);
                if (nodes[i].hash == hash) continue;
                nodes[i].hash = hash;
                if (nodes[i].prev != null) nodes[i].prev.next = nodes[i].next;
                if (nodes[i].next != null) nodes[i].next.prev = nodes[i].prev;
                Node tempNode = strHash[hash];
                while (tempNode.next != null && tempNode.next.idx < i) tempNode = tempNode.next;
                nodes[i].next = tempNode.next;
                tempNode.next = nodes[i];
                nodes[i].prev = tempNode;
                if (nodes[i].next != null) nodes[i].next.prev = nodes[i];
            }
        }
        return cnt;
    }

    void result(char[] ret) {
        System.arraycopy(str, 0, ret, 0, str.length);
    }
}

class Node {
    int idx;
    int hash;
    Node prev;
    Node next;

    public Node(int idx, int hash, Node prev, Node next) {
        this.idx = idx;
        this.hash = hash;
        this.prev = prev;
        this.next = next;
    }
}
