# Coin Change II
You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money. Return the number of combinations that make up that amount. If that amount of money cannot be made up by any combination of the coins, return 0.

Example 1:
Input: {"amount":5,"coins":[1,2,5]}
Output: 4

**Approach: Unbounded Knapsack DP**

**Key Insight:** dp\[i\] = minimum number of coins to make amount i. For each coin, we can use it unlimited times, so iterate coins in outer loop.

**Algorithm:**

1.  Initialize dp\[0\] = 0, dp\[1..amount\] = infinity
    
2.  For each amount i from 1 to target:
    
    -   For each coin:
        
        -   If coin <= i: dp\[i\] = min(dp\[i\], dp\[i-coin\] + 1)
            
3.  Return dp\[amount\] if not infinity, else -1
    

**Example:** coins = \[1,2,5\], amount = 11

-   dp\[0\] = 0
    
-   dp\[1\] = dp\[0\]+1 = 1 (use coin 1)
    
-   dp\[2\] = min(dp\[1\]+1, dp\[0\]+1) = 1 (use coin 2)
    
-   dp\[5\] = min(dp\[4\]+1, dp\[3\]+1, dp\[0\]+1) = 1 (use coin 5)
    
-   dp\[11\] = min(dp\[10\]+1, dp\[9\]+1, dp\[6\]+1) = 3 (5+5+1)
    
-   Result: 3 ✓
    

**Why Unbounded:**

-   Each coin can be used multiple times
    
-   Unlike 0/1 knapsack where items are used once
    

**Time Complexity:** O(amount × n) - n = number of coin types **Space Complexity:** O(amount) - 1D DP array

**Time: O(amount \* n)**

**Space: O(amount)**

```
class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] += dp[a - coin];
            }
        }
        return dp[amount];
    }
}
```
