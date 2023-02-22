package 분할정복_9.No_33;

import java.io.*;
import java.util.HashSet;

/*
7701. 염라대왕의 이름 정렬

염라대왕은 이승의 사람들의 모든 이름을 가지고 있다.
어느날 저승에 일어난 진도 8.0 지진에 항상 정리되어 있던 이승 명부가 흐트러졌다.
이승 명부는 이름의 길이가 짧을수록 이 앞에 있었고, 같은 길이면 사전 순으로 앞에 있었다.
이왕 이렇게 된 김에 모든 이름을 다시 정리하고 같은 이름은 하나만 남겨놓기로 한 염라대왕을 도와주자.

[입력]
첫 번째 줄에 테스트 케이스의 수 T(1 ≤ T ≤ 50)가 주어진다.
각 테스트 케이스의 첫 번째 줄에는 이승 명부의 이름 개수 N(1 ≤ N ≤ 20,000)이 주어진다.
각 테스트 케이스의 두 번째 줄부터 N개의 줄에 걸쳐서 알파벳 소문자로 이루어진 이름들이 주어진다.
이름에는 공백이 포함되지 않으며 최소 1개, 최대 50개의 알파벳으로 이루어져 있다.

[출력]
각 테스트 케이스마다 ‘#x’(x는 테스트케이스 번호를 의미하며 1부터 시작한다)를 출력하고,
정리된 이름을 한 줄에 하나씩 출력하라. 같은 이름은 한 번만 출력해야 하는 것을 주의하라.

주요 아이디어
1. Set 을 이용해서 중복되는 문자열 제거
2. QuickSort 를 통해 문자열 sorting 진행
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuffer sb = new StringBuffer();

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            sb.append("#").append(t).append("\n");

            int N = Integer.parseInt(br.readLine());

            // 중복된 문자열을 없애기 위해서
            HashSet<String> temp = new HashSet<>();

            for (int i = 0; i < N; i++) {

                temp.add(br.readLine());
            }

            String[] names = new String[N];

            int j = 0;
            for (String name : temp) {
                names[j++] = name;
            }

            quickSort(names, 0, j - 1);

            for (int i = 0; i < j; i++) {

                sb.append(names[i]).append("\n");
            }
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    private static void quickSort(String[] names, int left, int right) {

        // 원소의 숫자가 0 또는 1개이면 정렬할 필요가 없다.
        if (left >= right) return;

        int pivot = divide(names, left, right);

        quickSort(names, left, pivot);
        quickSort(names, pivot + 1, right);
    }

    private static int divide(String[] names, int left, int right) {

        int l = left;
        int r = right;
        String pivot = names[(left + right) / 2];

        while (true) {

            // pivot 보다 길이가 길거나 사전상으로 뒤인 원소를 찾는다.
            while (compare(names[l], pivot) == 1) {
                l++;
            }

            // pivot 보다 길이가 짧거나 사전상으로 앞인 원소를 찾는다.
            while (compare(pivot, names[r]) == 1 && l < r) {
                r--;
            }

            if (l >= r) {
                return r;
            }

            // value 교환
            swap(names, l, r);
        }
    }

    private static void swap(String[] names, int l, int r) {

        String temp = names[l];
        names[l] = names[r];
        names[r] = temp;
    }

    private static int compare(String s1, String s2) {

        // 1 -> s1 가 더 앞이다, s1 의 길이가 더 짧거나 사전상으로 앞이다.
        // 0 -> s2 이 더 앞이다.
        // -1 -> s1 , s2 가 같다.
        if (s1.length() > s2.length()) {
            return 0;
        } else if (s1.length() == s2.length()) {
            for (int i = 0; i < s1.length(); i++) {
                int i1 = s1.charAt(i) - s2.charAt(i);
                if (i1 > 0) return 0;
                else if (i1 < 0) return 1;
            }

            return -1;
        } else {
            return 1;
        }
    }
}
