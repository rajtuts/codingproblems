# Search a 2D Matrix II
Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix. This matrix has the following properties: Integers in each row are sorted in ascending from left to right. Integers in each column are sorted in ascending from top to bottom.

Example 1:
Input: {"matrix":[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]],"target":5}
Output: true

**Approach: Staircase Search**

**Key Insight:** Start from top-right corner. The matrix has sorted rows and columns, so from any cell: going left decreases value, going down increases value. This creates a binary search-like elimination.

**Algorithm:**

1.  Start at (0, n-1) — top-right corner
    
2.  While within bounds:
    
    -   If current == target: return True
        
    -   If current > target: go left (col--)
        
    -   If current < target: go down (row++)
        
3.  Return False (not found)
    

**Example:** matrix, target = 5

`[1, 4, 7, 11] Start at 11 [2, 5, 8, 12] 11>5 → left → 7 [3, 6, 9, 16] 7>5 → left → 4 [10,13,14, 17] 4<5 → down → 5 ✓`

**Why Top-Right (or Bottom-Left):**

-   Top-left: both directions increase → can't eliminate
    
-   Bottom-right: both directions decrease → can't eliminate
    
-   Top-right or bottom-left: one direction increases, one decreases → can eliminate row or column
    

**Alternative: Binary Search Each Row**

-   O(m log n) time, but staircase is simpler
    

**Time Complexity:** O(m + n) - at most m+n steps **Space Complexity:** O(1) - only position variables

**Time: O(m+n)**

**Space: O(1)**

```
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return false;
        int r = 0, c = matrix[0].length - 1;
        
        while (r < matrix.length && c >= 0) {
            if (matrix[r][c] == target) return true;
            else if (matrix[r][c] > target) c--;
            else r++;
        }
        return false;
    }
}
```
