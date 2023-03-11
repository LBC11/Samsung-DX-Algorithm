package Kakao.delivery_2023;

public class Test {

    public static void main(String[] args) {

        Solution s = new Solution();

        int cap = 2;
        int n = 2;
        int[] deliveries = {0,0};
        int[] pickups = {0,0};

        System.out.println(s.solution(cap, n, deliveries, pickups));
    }
}
