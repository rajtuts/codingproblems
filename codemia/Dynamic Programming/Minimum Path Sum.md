# Minimum Path Sum
Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path. You can only move either down or right at any point in time.

Example 1:
Input: [[1,3,1],[1,5,1],[4,2,1]]
Output: 7

**Approach: Dynamic Programming**

**Key Insight:** The minimum path sum to reach any cell is its value plus the minimum of the path sums from the cell above or the cell to the left.

**Algorithm:**

1.  Initialize dp\[0\]\[0\] = grid\[0\]\[0\]
    
2.  Fill first row: dp\[0\]\[j\] = dp\[0\]\[j-1\] + grid\[0\]\[j\]
    
3.  Fill first column: dp\[i\]\[0\] = dp\[i-1\]\[0\] + grid\[i\]\[0\]
    
4.  For each cell: dp\[i\]\[j\] = grid\[i\]\[j\] + min(dp\[i-1\]\[j\], dp\[i\]\[j-1\])
    
5.  Return dp\[m-1\]\[n-1\]
    

**Example:** grid = \[\[1,3,1\],\[1,5,1\],\[4,2,1\]\]

`[1, 4, 5] (first row: cumulative sum) [2, 7, 6] (dp[1][1] = 5 + min(4,2) = 7) [6, 8, 7] (dp[2][2] = 1 + min(6,8) = 7)`

Minimum path: 1→3→1→1→1 = 7 ✓

**Space Optimization:**

-   Can modify grid in-place
    
-   Or use 1D array of size n
    

**Time Complexity:** O(m × n) - visit each cell once **Space Complexity:** O(1) - if modifying in-place

**Time: O(m\*n)**

**Space: O(m\*n)**

```
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }
}
```
