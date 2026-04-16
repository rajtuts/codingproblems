# Min Cost Climbing Stairs
You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps. You can either start from the step with index 0, or the step with index 1. Return the minimum cost to reach the top of the floor.

Example 1:
Input: [10,15,20]
Output: 15

**Approach: Dynamic Programming**

**Key Insight:** At each step, you can come from either of the two previous steps. Choose the one with minimum cost. dp\[i\] = cost\[i\] + min(dp\[i-1\], dp\[i-2\]).

**Algorithm:**

1.  Initialize dp\[0\] = cost\[0\], dp\[1\] = cost\[1\]
    
2.  For i from 2 to n-1:
    
    -   dp\[i\] = cost\[i\] + min(dp\[i-1\], dp\[i-2\])
        
3.  Return min(dp\[n-1\], dp\[n-2\]) (can reach top from either)
    

**Example:** cost = \[10,15,20\]

-   dp\[0\] = 10, dp\[1\] = 15
    
-   dp\[2\] = 20 + min(10, 15) = 30
    
-   Top is after index 2, can reach from dp\[1\]=15 or dp\[2\]=30
    
-   Return min(15, 30) = 15 ✓
    

**Space Optimization:**

-   Only need two previous values
    
-   Use variables prev1, prev2 instead of array
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - with optimization

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);
        }
        return dp[n];
    }
}
```
