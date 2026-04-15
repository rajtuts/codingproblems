# Coin Change
You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money. Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

Example 1:
Input: {"coins":[1,2,5],"amount":11}
Output: 3

**Approach: Dynamic Programming (Bottom-Up)**

**Key Insight:** The minimum coins for amount A depends on minimum coins for smaller amounts. If we use coin c, we need 1 + minCoins(A - c).

**Recurrence:**

`dp[amount] = min(dp[amount - coin] + 1) for all valid coins`

**Algorithm:**

1.  Create dp array of size amount+1, initialized to infinity
    
2.  dp\[0\] = 0 (base case: 0 coins for amount 0)
    
3.  For each amount from 1 to target:
    
    -   For each coin:
        
        -   If coin <= amount: dp\[amount\] = min(dp\[amount\], dp\[amount-coin\] + 1)
            
4.  Return dp\[amount\] if valid, else -1
    

**Example:** coins = \[1,2,5\], amount = 11

**amt01234567891011**dp011221223323

dp\[11\] = 3 (using 5+5+1)

**Why DP Works:** Optimal substructure - best solution for amount A uses best solutions for smaller amounts.

**Time: O(amount × coins)**, **Space: O(amount)**

**Greedy Doesn't Work:** \[1,3,4\], amount=6 → Greedy picks 4+1+1=3 coins, but 3+3=2 coins is better.

**Time: O(amount × n)**

**Space: O(amount)**

```
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        
        for (int a = 1; a <= amount; a++) {
            for (int c : coins) {
                if (a - c >= 0) {
                    dp[a] = Math.min(dp[a], 1 + dp[a - c]);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
```
