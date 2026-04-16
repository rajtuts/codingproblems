# Triangle
Given a triangle array, return the minimum path sum from top to bottom. For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, you may move to either index i or index i + 1 on the next row.

Example 1:
Input: [[2],[3,4],[6,5,7],[4,1,8,3]]
Output: 11

**Approach: Top-Down DP**

**Key Insight:** For each cell, the minimum path sum equals the cell's value plus the minimum of the two cells above it that can reach this cell (directly above or diagonally left).

**Algorithm:**

1.  Initialize dp = copy of triangle
    
2.  For each row i from 1 to n-1:
    
    -   For each cell j in row:
        
        -   If j=0: dp\[i\]\[j\] = dp\[i-1\]\[0\] + triangle\[i\]\[j\]
            
        -   If j=last: dp\[i\]\[j\] = dp\[i-1\]\[j-1\] + triangle\[i\]\[j\]
            
        -   Else: dp\[i\]\[j\] = min(dp\[i-1\]\[j-1\], dp\[i-1\]\[j\]) + triangle\[i\]\[j\]
            
3.  Return min(dp\[n-1\])
    

**Example:** \[\[2\],\[3,4\],\[6,5,7\],\[4,1,8,3\]\]

`2 → 2 3 4 → 5 6 6 5 7 → 11 10 13 4 1 8 3 → 15 11 18 16`

Minimum of last row = 11 ✓ Path: 2 → 3 → 5 → 1

**Space Optimization:**

-   Use bottom-up: start from bottom row, work up
    
-   Only need previous row → O(n) space
    

**Time Complexity:** O(n²) - visit each cell **Space Complexity:** O(n²) - or O(n) with optimization

**Time: O(n^2)**

**Space: O(n^2)**

```
class Solution {
    public int minimumTotal(int[][] triangle) {
        int n = triangle.length;
        int[][] dp = new int[n][];
        for (int i = 0; i < n; i++) {
            dp[i] = triangle[i].clone();
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                if (j == 0) {
                    dp[i][j] = dp[i-1][j] + triangle[i][j];
                } else if (j == triangle[i].length - 1) {
                    dp[i][j] = dp[i-1][j-1] + triangle[i][j];
                } else {
                    dp[i][j] = Math.min(dp[i-1][j-1], dp[i-1][j]) + triangle[i][j];
                }
            }
        }

        int min = dp[n-1][0];
        for (int val : dp[n-1]) min = Math.min(min, val);
        return min;
    }
}
```
