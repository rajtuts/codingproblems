package dynamic;


class ClimbStairsLeetCode70 {
    public int climbStairs(int n) {
        if (n <= 2) return n;
        int first = 1, second = 2;
        for (int i = 1; i <= n - 2; i++) {
            int tmp = first + second;
            first = second;
            second = tmp;
        }
        return second;
    }
} // TC: 0(n), SC: 0(1)