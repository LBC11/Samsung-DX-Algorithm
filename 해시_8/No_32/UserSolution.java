package 해시_8.No_32;

import java.util.Arrays;

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
