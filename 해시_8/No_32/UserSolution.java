package 해시_8.No_32;

import java.util.*;

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

주요 아이디어


실패 분석
1. 그동안 hash 하는 방법이 잘못된 줄 알고 라빈 카프 알고리즘, 비트 패킹 등등 여러가지 시도를 했었는데
본질적인 이유는 ArrayList 를 사용했다는 거였다. ArrayList 는 중간 idx 의 element 를 delete 할 시
그 뒤의 element 를 한칸씩 당기는데 이러한 과정에서 발생하는 시간이 O(n)이다.
-> LinkedList 구현할 때 배웠던 건데 다시 한번 살펴보자
2. 함수를 getHash 함수 호출할 때 기존의 string 을 사용하면 되는데 new 로 char[] 을 새로 선언하는 비효율성이 있었다.
3. change function 에서 hash_A 를 가지는 idx 를 찾기 위하여 모든 s 를 순회하였던 것
   -> HashMap 으로 해결


 */
import java.util.Arrays;

public class UserSolution {

    // key: hash value
    // value: 해당 hash value 을 가지는 idx
    HashMap<Integer, TreeSet<Integer>> map;

    char[] string;

    void init(int N, char[] init_string) {

        map = new HashMap<>();
        string = Arrays.copyOfRange(init_string, 0, N);

        for (int i = 0; i < N - 2; i++) {

            // map info 할당
            int hash = getHash(init_string, i);
            if (!map.containsKey(hash)) map.put(hash, new TreeSet<>());
            map.get(hash).add(i);
        }
    }

    public int change(char[] string_A, char[] string_B) {

        // 각 hash value
        int hash_A = getHash(string_A, 0);
        int hash_B = getHash(string_B, 0);

        int ret = 0;

        // hash_A 의 value 를 가지는 idx 가 없을 경우 0 return
        if (!map.containsKey(hash_A)) return ret;

        // hash_B 의 set init
        if (!map.containsKey(hash_B)) map.put(hash_B, new TreeSet<>());

        // 전 단계에 change 한
        int prev = -3;

        List<Integer> array = new LinkedList<>(map.get(hash_A));

        // 문자열을 교체했을 때 영향을 받는 다른 idx 가
        // hash_A 를 가지고 있었을 때 영향을 받은 후에도
        // 계속해서 hash 값으로 hash_A 를 가지고 있는 지 확인한다.
        // 가지고 있는 애만 map 에서 정보를 삭제한다.
        for (int i : array) {

            // 이전 단계에서 변경한 idx 의 + 2 보다 작아야 영향을 받는다.
            // 상한만 검사하는 이유는 set 은 자동으로 오름차순으로 정렬하기 때문이다.
            if (prev + 2 < i) {
                boolean string_check = true;

                // 문자열 비교
                for (int j = 0; j < 3; j++)
                    if (string[i + j] != string_A[j]) {
                        string_check = false;
                        break;
                    }

                // 문자열이 일치하는 경우
                if (string_check) {

                    // 문자열 할당
                    for (int j = 0; j < 3; j++)
                        string[i + j] = string_B[j];

                    // change 한 idx map 에서 삭제
                    map.get(hash_A).remove(i);

                    // change 한 idx 개수 갱신
                    ret++;

                    // prev 갱신
                    prev = i;
                }
            }
        }

        // hash_A 를 가져 문자열이 실제로 변경된 idx 만 array 에 남긴다.
        array.removeAll(map.get(hash_A));

        // tree clear
        map.get(hash_A).clear();

        for (int i : array) {
            for (int j = -2; j < 3; j++) {

                // string 범위 내에 드느지 check
                if (i + j < 0 || i + j >= string.length - 2) continue;

                // hash value cal
                int hash = getHash(string, i + j);

                // 해당 hash value map 에 저장
                if (!map.containsKey(hash)) map.put(hash, new TreeSet<>());
                map.get(hash).add(i + j);
            }
        }

        return ret;
    }

    void result(char[] ret) {

        System.arraycopy(string, 0, ret, 0, string.length);
    }

    int getHash(char[] arr, int i) {

        return (arr[i] - 'a') * 26 * 26 + (arr[i + 1] - 'a') * 26 + (arr[i + 2] - 'a');
    }
}
