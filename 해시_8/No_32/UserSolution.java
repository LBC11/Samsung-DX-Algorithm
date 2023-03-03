package 해시_8.No_32;

import java.util.Arrays;

public class UserSolution {

    int[] hash_int;
    char[] string;

    void init(int N, char[] init_string) {

        hash_int = new int[N - 2];
        string = Arrays.copyOfRange(init_string, 0, N);

        for (int i = 0; i < N - 2; i++) {
            hash_int[i] = getHash(init_string, i);
        }
    }

    int change(char[] string_A, char[] string_B) {

        int hash_A = getHash(string_A, 0);
        int hash_B = getHash(string_B, 0);

        int ret = 0;

        for (int i = 0; i < hash_int.length; i++) {

            if (hash_int[i] == hash_A) {
                hash_int[i] = hash_B;

                // string 갱신
                string[i] = string_B[0];
                string[i + 1] = string_B[1];
                string[i + 2] = string_B[2];

                // hash 값 재조정
                if (i - 2 >= 0) {

                    // i - 2번째 hash 값 재조정
                    hash_int[i - 2] = getHash(new char[]{string[i - 2], string[i - 1], string[i]}, 0);
                }

                if (i - 1 >= 0) {

                    // i - 1번째 hash 값 재조정
                    hash_int[i - 1] = getHash(new char[]{string[i - 1], string[i], string[i + 1]}, 0);

                }

                if (i + 1 < hash_int.length) {

                    // i + 1번째 hash 값 재조정
                    hash_int[i + 1] = getHash(new char[]{string[i + 1], string[i + 2], string[i + 3]}, 0);
                }

                if (i + 2 < hash_int.length) {

                    // i + 2번째 hash 값 재조정
                    hash_int[i + 2] = getHash(new char[]{string[i + 2], string[i + 3], string[i + 4]}, 0);
                }

                // 그 뒤 2번째 까지 문자열이 바뀌면서 기존의 hash 값도 다시 계산해야 한다.
                i += 2;

                ret++;
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

