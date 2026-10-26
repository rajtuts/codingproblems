# LeetCode 70 — Climbing Stairs

Easy

Topic: Dynamic Programming

Problem: You are climbing a staircase with `n` steps. Each time, you can climb either 1 step or 2 steps. How many distinct ways can you reach the top?

## 1. Understand the problem

Suppose `n = 3`. You need to reach the third step.

There are exactly 3 ways:

`1 + 1 + 1`

Three single steps

`1 + 2`

One step, then two

`2 + 1`

Two steps, then one

Answer: 3

## 2. The key observation

To reach step `n`, your last move must be one of two possibilities:

- You came from step `n - 1` by taking 1 step.
- You came from step `n - 2` by taking 2 steps.

Therefore, the number of ways to reach step `n` is:

\\[ \boxed{dp[n]=dp[n-1]+dp[n-2]} \\]

This is the same recurrence as the Fibonacci sequence.

## 3. Build the DP table

Let `dp[i]` be the number of distinct ways to reach step `i`.

Base cases:

- `dp[1] = 1`
- `dp[2] = 2`

Then calculate each subsequent value by adding the previous two.

| Step `i` | Calculation     | Ways |
| -------- | --------------- | ---- |
| 1        | Base case       | 1    |
| 2        | Base case       | 2    |
| 3        | `dp[2] + dp[1]` | 3    |
| 4        | `dp[3] + dp[2]` | 5    |
| 5        | `dp[4] + dp[3]` | 8    |

For `n = 5`, the answer is 8.

## 4. Java solution — Dynamic Programming

```

class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n + 1];

        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}

```

### How the code works

For `n = 5`:

1. Initialize `dp[1] = 1` and `dp[2] = 2`.
2. Calculate `dp[3] = 2 + 1 = 3`.
3. Calculate `dp[4] = 3 + 2 = 5`.
4. Calculate `dp[5] = 5 + 3 = 8`.
5. Return `dp[5]`, which is `8`.

## 5. Optimized Java solution

You don't actually need to store the entire DP array. Each step depends only on the previous two values.

```

class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int prev2 = 1;
        int prev1 = 2;

        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}

```

### Complexity comparison

| Approach     | Time       | Extra space |
| ------------ | ---------- | ----------- |
| DP array     | \\(O(n)\\) | \\(O(n)\\)  |
| Optimized DP | \\(O(n)\\) | \\(O(1)\\)  |

The optimized solution is generally preferable in interviews because it uses constant extra space.

## 6. Test your understanding

Question: If `n = 4`, how many distinct ways can you climb the staircase?

3

5

8

4

Interview takeaway: Whenever a problem asks for the number of ways to reach a position and the last move can come from a small number of previous positions, look for a dynamic programming recurrence. For this problem, the recurrence is `dp[n] = dp[n-1] + dp[n-2]`.
