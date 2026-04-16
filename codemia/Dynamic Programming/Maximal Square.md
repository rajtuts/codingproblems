# Maximal Square
Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

Example 1:
Input: [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4

**Approach: Dynamic Programming**

**Key Insight:** dp\[i\]\[j\] = side length of largest square with bottom-right corner at (i,j). If cell is '1', dp\[i\]\[j\] = 1 + min(top, left, diagonal).

**Algorithm:**

1.  Initialize dp with same dimensions as matrix
    
2.  For each cell (i, j):
    
    -   If matrix\[i\]\[j\] == '1':
        
        -   dp\[i\]\[j\] = 1 + min(dp\[i-1\]\[j\], dp\[i\]\[j-1\], dp\[i-1\]\[j-1\])
            
    -   Track maximum side length seen
        
3.  Return max\_side²
    

**Example:** matrix = \[\["1","0","1","0"\],\["1","0","1","1"\],\["1","1","1","1"\]\]

`DP: [1, 0, 1, 0] [1, 0, 1, 1] [1, 1, 1, 2] ← dp[2][3] = 1 + min(1,1,1) = 2`

Largest square side = 2, area = 4 ✓

**Why min(top, left, diagonal):**

-   Square needs all three neighbors to be valid
    
-   Smallest one limits how large current square can be
    
-   If any is 0, current square can only be 1×1
    

**Time Complexity:** O(m × n) - visit each cell **Space Complexity:** O(m × n) - or O(n) with 1D optimization

**Time: O(m \* n)**

**Space: O(m \* n)**

```
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int rows = matrix.length, cols = matrix[0].length;
        int[][] dp = new int[rows + 1][cols + 1];
        int maxSide = 0;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i-1][j-1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        return maxSide * maxSide;
    }
}
```
