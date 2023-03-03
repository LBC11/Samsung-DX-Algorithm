package 해시_8.No_32;

import java.util.ArrayList;
import java.util.HashMap;

public class UserSolution {

    int[] pack_numbers;
//    HashMap<Integer, char[]> pack_record;

    void init(int N, char[] init_string) {

        pack_numbers = new int[N - 2];

        for (int i = 0; i < N - 2; i++) {
            pack_numbers[i] = bitPacking(init_string, i);
        }

//        pack_record = new HashMap<>();
    }

    int change(char[] string_A, char[] string_B) {

        int num_A = bitPacking(string_A, 0);
        int num_B = bitPacking(string_B, 0);

        int ret = 0;

        ArrayList<Integer> revise_idx = new ArrayList<>();

        for (int i = 0; i < pack_numbers.length; i++) {

            if (pack_numbers[i] == num_A) {
                pack_numbers[i] = num_B;

                revise_idx.add(i);

                // 그 뒤 2번째 까지 문자열이 바뀌면서 기존의 packing 값도 다시 계산해야 한다.
                i += 2;

                ret++;
            }
        }

        // change 되면서 영향 받은 hash 값들 재조정
        for (int i : revise_idx) {

            char[] unpack = unpack_all(pack_numbers[i]);
            char[] new_string;

            if (i - 2 >= 0) {

                // i - 2번째 hash 값 재조정
                new_string = unpack_all(pack_numbers[i - 2]);

                new_string[2] = unpack[0];

                pack_numbers[i - 2] = bitPacking(new_string, 0);
            }

            if (i - 1 >= 0) {

                // i - 1번째 hash 값 재조정
                new_string = unpack_all(pack_numbers[i - 1]);

                new_string[1] = unpack[0];
                new_string[2] = unpack[1];

                pack_numbers[i - 1] = bitPacking(new_string, 0);
            }

            if (i + 1 < pack_numbers.length) {

                // i + 1번째 hash 값 재조정
                new_string = unpack_all(pack_numbers[i + 1]);

                new_string[0] = unpack[1];
                new_string[1] = unpack[2];

                pack_numbers[i + 1] = bitPacking(new_string, 0);

            }

            if (i + 2 < pack_numbers.length) {

                // i + 2번째 hash 값 재조정
                new_string = unpack_all(pack_numbers[i + 2]);

                new_string[0] = unpack[2];

                pack_numbers[i + 2] = bitPacking(new_string, 0);
            }
        }

        return ret;
    }

    void result(char[] ret) {

        for (int i = 0; i < pack_numbers.length - 1; i++) {

            ret[i] = unpack_first(pack_numbers[i]);
        }

        char[] rets = unpack_all(pack_numbers[pack_numbers.length - 1]);

        for (int i = 0; i < 3; i++) {
            ret[pack_numbers.length - 1 + i] = rets[i];
        }
    }

    int bitPacking(char[] arr, int start) {

        int ans = 0;

        int j = 0;
        for (int i = start; i < start + 3; i++) {

            int num = arr[i] - 'a';

            // num 의 j번째 bit 값이 1이면 ans 에 해당 bit 값 더하기
            for (int t = 0; t < 5; t++) {
                if ((num & (1 << t)) != 0) ans += (1 << j);
                j++;
            }
        }

        return ans;
    }

    // 첫번째 글자만 unpack 후 return
    char unpack_first(int packed) {

        int num = 0;

        for (int i = 0; i < 5; i++) {

            if ((packed & (1 << i)) != 0) num += (1 << i);
        }

        return (char) (num + 'a');
    }

    // 모든 글자 unpack 후 return
    char[] unpack_all(int packed) {

        char[] ret = new char[3];

        for (int i = 0; i < 3; i++) {

            int num = 0;

            for (int j = i * 5; j < i * 5 + 5; j++) {
                if ((packed & (1 << j)) != 0) num += (1 << (j - i * 5));
            }

            // unpack 한 글자
            ret[i] = (char) (num + 'a');
        }

        return ret;
    }
}
