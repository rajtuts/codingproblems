# Minimum Falling Path Sum
Given an n x n matrix, return the minimum sum of any falling path through the matrix. A falling path starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left/right.

Example 1:
Input: [[2,1,3],[6,5,4],[7,8,9]]
Output: 13

**Approach: Top-Down DP with 3 Choices**

**Key Insight:** Each cell can be reached from 3 cells in the previous row: directly above (j), diagonally left (j-1), or diagonally right (j+1). Choose the minimum.

**Algorithm:**

1.  Initialize dp = copy of matrix
    
2.  For each row i from 1 to n-1:
    
    -   For each column j:
        
        -   left = dp\[i-1\]\[j-1\] if j > 0 else infinity
            
        -   up = dp\[i-1\]\[j\]
            
        -   right = dp\[i-1\]\[j+1\] if j < n-1 else infinity
            
        -   dp\[i\]\[j\] = matrix\[i\]\[j\] + min(left, up, right)
            
3.  Return min(dp\[n-1\])
    

**Example:** matrix = \[\[2,1,3\],\[6,5,4\],\[7,8,9\]\]

`Row 0: [2, 1, 3] Row 1: j=0: 6 + min(∞, 2, 1) = 7 j=1: 5 + min(2, 1, 3) = 6 j=2: 4 + min(1, 3, ∞) = 5 → [7, 6, 5] Row 2: j=0: 7 + min(∞, 7, 6) = 13 j=1: 8 + min(7, 6, 5) = 13 j=2: 9 + min(6, 5, ∞) = 14 → [13, 13, 14]`

Minimum = 13, path: 1→5→7 ✓

**Space Optimization:**

-   Only need previous row → O(n) space
    

**Time Complexity:** O(n²) - visit each cell **Space Complexity:** O(n²) - or O(n) with rolling array

**Time: O(n^2)**

**Space: O(n^2)**

```
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];
        for (int j = 0; j < n; j++) dp[0][j] = matrix[0][j];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int left = j > 0 ? dp[i-1][j-1] : Integer.MAX_VALUE;
                int up = dp[i-1][j];
                int right = j < n-1 ? dp[i-1][j+1] : Integer.MAX_VALUE;
                dp[i][j] = matrix[i][j] + Math.min(left, Math.min(up, right));
            }
        }

        int min = dp[n-1][0];
        for (int j = 1; j < n; j++) min = Math.min(min, dp[n-1][j]);
        return min;
    }
}
```
