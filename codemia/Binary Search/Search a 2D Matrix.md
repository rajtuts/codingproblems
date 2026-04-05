# Search a 2D Matrix
Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix.  

## Example 1:  
Input: {"matrix":[[1,3,5,7],[10,11,16,20],[23,30,34,60]],"target":3}  
Output: true  

**Approach: Binary Search on Virtual 1D Array**

**Key Insight:** The matrix is effectively a sorted 1D array. Map 1D index to 2D coordinates: row = idx / n, col = idx % n.

**Algorithm:**

1.  Treat as 1D array of size m×n
    
2.  Binary search from 0 to m×n-1
    
3.  Convert mid to 2D: matrix\[mid // n\]\[mid % n\]
    
4.  Compare with target, adjust bounds
    

**Example:** matrix = \[\[1,3,5\],\[10,11,16\],\[23,30,34\]\], target = 11

-   m=3, n=3, search \[0, 8\]
    
-   mid=4: matrix\[4//3\]\[4%3\] = matrix\[1\]\[1\] = 11 ✓
    

**Index conversion:**

-   1D index 5 → row=5//3=1, col=5%3=2 → matrix\[1\]\[2\]=16
    
-   Works because rows are fully sorted and consecutive
    

**Time Complexity:** O(log(m×n)) - binary search **Space Complexity:** O(1) - only pointers

**Time: O(log(m\*n))**

**Space: O(1)**

```
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) return false;
        int m = matrix.length, n = matrix[0].length;
        int l = 0, r = m * n - 1;
        
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int midVal = matrix[mid / n][mid % n];
            
            if (midVal == target) return true;
            else if (midVal < target) l = mid + 1;
            else r = mid - 1;
        }
        return false;
    }
}
```
