# Cherry Pickup II
You are given a rows x cols matrix grid representing a field of cherries where grid[i][j] represents the number of cherries you can collect from cell (i, j). Two robots are located at (0, 0) and (0, cols-1). Return the maximum number of cherries collected using both robots by moving towards the bottom.

Example 1:
Input: {"grid":[[3,1,1],[2,5,1],[1,5,5],[2,1,1]]}
Output: 24

Use 3D DP where state is (row, col1, col2). Both robots move down together. At each step, try all 9 combinations of moves. If same cell, only count cherries once. Memoize for efficiency.

Time: O(m * n^2)
Space: O(m * n^2)

```
class Solution {
    public int cherryPickup(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        dp[0][n - 1] = grid[0][0] + grid[0][n - 1];

        for (int row = 1; row < m; row++) {
            int[][] newDp = new int[n][n];
            for (int[] r : newDp) Arrays.fill(r, -1);
            for (int c1 = 0; c1 < n; c1++) {
                for (int c2 = 0; c2 < n; c2++) {
                    for (int dc1 = -1; dc1 <= 1; dc1++) {
                        for (int dc2 = -1; dc2 <= 1; dc2++) {
                            int pc1 = c1 - dc1, pc2 = c2 - dc2;
                            if (pc1 >= 0 && pc1 < n && pc2 >= 0 && pc2 < n && dp[pc1][pc2] != -1) {
                                int cherries = grid[row][c1];
                                if (c1 != c2) cherries += grid[row][c2];
                                newDp[c1][c2] = Math.max(newDp[c1][c2], dp[pc1][pc2] + cherries);
                            }
                        }
                    }
                }
            }
            dp = newDp;
        }

        int result = 0;
        for (int[] row : dp) for (int val : row) result = Math.max(result, val);
        return result;
    }
}
```
