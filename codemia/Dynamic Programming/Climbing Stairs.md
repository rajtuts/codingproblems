# Climbing Stairs
You are climbing a staircase. It takes n steps to reach the top. Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Example 1:
Input: 5
Output: 8

**Approach: Dynamic Programming (Fibonacci Pattern)**

**Key Insight:** To reach step n, you must have come from either step n-1 (took 1 step) or step n-2 (took 2 steps). So: ways(n) = ways(n-1) + ways(n-2).

**This is the Fibonacci sequence!**

-   ways(1) = 1: just one step
    
-   ways(2) = 2: either 1+1 or 2
    
-   ways(3) = ways(2) + ways(1) = 3
    
-   ways(4) = ways(3) + ways(2) = 5
    
-   And so on...
    

**Space-Optimized Solution:** Since we only need the last two values, use two variables instead of an array:

`a, b = 1, 2 # ways(1), ways(2) for _ in range(2, n): a, b = b, a + b return b`

**Why DP Works:** The problem has:

-   **Optimal substructure:** Solution depends on solutions to subproblems
    
-   **Overlapping subproblems:** Same subproblems are solved multiple times (without memoization)
    

**Time: O(n)**, **Space: O(1)** with optimization

**Bonus - O(log n) Solution:** Use matrix exponentiation with Fibonacci matrix.

**Time: O(n)**

**Space: O(1)**



```
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) return n;
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
