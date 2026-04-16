# Dungeon Game
The demons had captured the princess and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of m x n rooms. Our valiant knight was initially positioned in the top-left room and must fight through the dungeon to rescue the princess. The knight has an initial health point. Some rooms have demons that decrease health, others have magic orbs that increase health. Determine the minimum initial health needed so the knight can rescue the princess.

Example 1:
Input: [[-2,-3,3],[-5,-10,1],[10,30,-5]]
Output: 7

DP working backwards. dp[i][j] = minimum HP needed at cell (i,j) to reach princess.

Time: O(m*n)
Space: O(m*n)

```
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
        dp[m][n - 1] = dp[m - 1][n] = 1;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = Math.max(1, need);
            }
        }

        return dp[0][0];
    }
}
```
