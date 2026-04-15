# Unique Paths
There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time. Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.

Example 1:
Input: {"m":3,"n":7}
Output: 28

**Approach: Dynamic Programming**

**Key Insight:** The number of ways to reach any cell equals the sum of ways to reach the cell above and the cell to the left, since those are the only moves allowed.

**Algorithm:**

1.  Initialize grid with dp\[i\]\[j\] = 1 for first row and column (only one way)
    
2.  For each cell (i,j) where i>0 and j>0: dp\[i\]\[j\] = dp\[i-1\]\[j\] + dp\[i\]\[j-1\]
    
3.  Return dp\[m-1\]\[n-1\]
    

**Example:** m=3, n=3

`[1, 1, 1] [1, 2, 3] [1, 3, 6]`

Answer: 6 paths

**Mathematical Solution:**

-   Total moves needed: (m-1) down + (n-1) right = m+n-2 moves
    
-   Choose which moves are "down": C(m+n-2, m-1)
    
-   Formula: (m+n-2)! / ((m-1)! × (n-1)!)
    

**Time Complexity:** O(m×n) - fill entire grid **Space Complexity:** O(m×n) - or O(n) with 1D array

**Time: O(m\*n)**

**Space: O(m\*n)**

```
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, 1);
        
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                dp[r][c] = dp[r - 1][c] + dp[r][c - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
```
