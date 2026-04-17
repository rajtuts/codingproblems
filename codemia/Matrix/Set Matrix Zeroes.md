# Set Matrix Zeroes
Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0s. You must do it in place.

Example 1:
Input: [[1,1,1],[1,0,1],[1,1,1]]
Output: [[1,0,1],[0,0,0],[1,0,1]]

**Approach: In-Place Marking with First Row/Column**

**Key Insight:** Use the first row and first column as markers to track which rows/columns need to be zeroed. Two separate flags track if the first row/column themselves contain zeros.

**Algorithm:**

1.  Record if first row/column originally have zeros
    
2.  Mark: for each cell (i,j) where i,j > 0:
    
    -   If matrix\[i\]\[j\] == 0: set matrix\[i\]\[0\] = matrix\[0\]\[j\] = 0
        
3.  Zero based on markers: for i,j > 0:
    
    -   If matrix\[i\]\[0\] == 0 OR matrix\[0\]\[j\] == 0: set matrix\[i\]\[j\] = 0
        
4.  Handle first row/column using saved flags
    

**Example:**

`1 1 1 1 0 1 1 0 1 1 0 1 → 0 0 1 → 0 0 0 1 1 1 1 0 1 1 0 1`

-   (1,1)=0 → mark row 1 (matrix\[1\]\[0\]=0), col 1 (matrix\[0\]\[1\]=0)
    
-   Zero all cells where marker is 0
    

**Why First Row/Column:**

-   They're already allocated space
    
-   Can serve as markers without extra memory
    
-   Process them last to avoid overwriting markers
    

**Time Complexity:** O(m×n) - two passes **Space Complexity:** O(1) - only two boolean flags

**Time: O(m\*n)**

**Space: O(1)**

```
class Solution {
    public void setZeroes(int[][] matrix) {
        int ROWS = matrix.length, COLS = matrix[0].length;
        boolean rowZero = false;
        
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (matrix[r][c] == 0) {
                    matrix[0][c] = 0;
                    if (r > 0) matrix[r][0] = 0;
                    else rowZero = true;
                }
            }
        }
        
        for (int r = 1; r < ROWS; r++) {
            for (int c = 1; c < COLS; c++) {
                if (matrix[0][c] == 0 || matrix[r][0] == 0) {
                    matrix[r][c] = 0;
                }
            }
        }
        
        if (matrix[0][0] == 0) {
            for (int r = 0; r < ROWS; r++) matrix[r][0] = 0;
        }
        
        if (rowZero) {
            for (int c = 0; c < COLS; c++) matrix[0][c] = 0;
        }
    }
}
```
