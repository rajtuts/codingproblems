package dynamic;

public class FibonacLeetCode509 {
    public int fib(int n) {
        if (n <= 1) return n;
        int first = 0, second = 1;
        for (int i = 2; i <= n; i++) {
            int tmp = first + second;
            first = second;
            second = tmp;
        }
        return second;
    }
} // TC: 0(n), SC: 0(1)


