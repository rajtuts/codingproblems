# Super Egg Drop
You are given k identical eggs and you have access to a building with n floors. Your goal is to determine the minimum number of moves needed to find the critical floor f where eggs start breaking.

Example 1:
Input: {"k":2,"n":6}
Output: 3

Reverse the question: max floors checkable with m moves and k eggs. dp[m][k] = floors if break + floors if survive + 1. Find minimum m where dp[m][k] >= n.

Time: O(k * n)
Space: O(k * n)

```
class Solution {
    public int superEggDrop(int k, int n) {
        // dp[m][j] = max floors checkable with m moves and j eggs
        int[][] dp = new int[n + 1][k + 1];

        int m = 0;
        while (dp[m][k] < n) {
            m++;
            for (int j = 1; j <= k; j++) {
                dp[m][j] = dp[m - 1][j - 1] + dp[m - 1][j] + 1;
            }
        }

        return m;
    }
}
```
