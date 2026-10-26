# LeetCode 509 — Fibonacci Number

<img width="812" height="817" alt="image" src="https://github.com/user-attachments/assets/557ac0f4-299b-453d-a09c-351bf1f74aab" />

Easy

Topic: Recursion, Dynamic Programming

Problem: Given an integer `n`, calculate the \\(n\\)-th Fibonacci number.

## 1. Understand the problem

The Fibonacci sequence is defined as:

\\[ F(n)= \begin{cases} 0 & n=0\\\ 1 & n=1\\\ F(n-1)+F(n-2) & n\geq2 \end{cases} \\]

Each number is the sum of the previous two numbers.

| `n` | Fibonacci number `F(n)` |
| --- | ----------------------- |
| 0   | 0                       |
| 1   | 1                       |
| 2   | 1                       |
| 3   | 2                       |
| 4   | 3                       |
| 5   | 5                       |
| 6   | 8                       |
| 7   | 13                      |

For example:

```
Input:  n = 5
Output: 5
```

Because \\(F(5)=F(4)+F(3)=3+2=5\\).

## 2. Visualize how it works

Let's calculate `F(5)`.

## F(5) = 5

3 + 2

## F(4) = 3

2 + 1

## F(3) = 2

1 + 1

To find `F(5)`, we need `F(4)` and `F(3)`. Each of those is also the sum of two previous Fibonacci numbers.

## 3. Java solution 1 — Recursion

The most direct solution follows the mathematical definition.

```

class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        return fib(n - 1) + fib(n - 2);
    }
}

```

### How it works

For `n = 4`:

```
fib(4)
= fib(3) + fib(2)
= 2 + 1
= 3
```

Complexity:

- Time: \\(O(2^n)\\) — many calculations are repeated.
- Space: \\(O(n)\\) — recursive call stack.

This solution is easy to understand, but inefficient for larger `n`.

## 4. Java solution 2 — Dynamic programming

Instead of recalculating the same Fibonacci numbers, store previously computed results in an array.

```

class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}

```

### Dry run: `n = 6`

| `i` | Calculation     | `dp[i]` |
| --- | --------------- | ------- |
| 0   | Base case       | 0       |
| 1   | Base case       | 1       |
| 2   | `dp[1] + dp[0]` | 1       |
| 3   | `dp[2] + dp[1]` | 2       |
| 4   | `dp[3] + dp[2]` | 3       |
| 5   | `dp[4] + dp[3]` | 5       |
| 6   | `dp[5] + dp[4]` | 8       |

Output: `8`

Complexity:

- Time: \\(O(n)\\)
- Space: \\(O(n)\\)

## 5. Java solution 3 — Optimized DP

We only need the previous two Fibonacci numbers, so we don't need an entire array.

```

class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        int prev2 = 0;
        int prev1 = 1;

        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}

```

For each iteration:

1. Calculate the next number using `prev1 + prev2`.
2. Move `prev1` into `prev2`.
3. Store the new number in `prev1`.

### Complexity comparison

| Approach     | Time         | Extra space |
| ------------ | ------------ | ----------- |
| Recursion    | \\(O(2^n)\\) | \\(O(n)\\)  |
| DP array     | \\(O(n)\\)   | \\(O(n)\\)  |
| Optimized DP | \\(O(n)\\)   | \\(O(1)\\)  |

## 6. Test your understanding

What does `fib(7)` return?

8

13

21

7

Interview takeaway: LeetCode 509 teaches the fundamentals of dynamic programming: define base cases, identify a recurrence relation, and reuse previous results. For an interview, understand the recursive approach first, then use the optimized \\(O(n)\\)-time, \\(O(1)\\)-space solution.
