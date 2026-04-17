# Rotate Image
You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise). You have to rotate the image in-place.

Example 1:
Input: [[1,2,3],[4,5,6],[7,8,9]]
Output: [[7,4,1],[8,5,2],[9,6,3]]

**Approach: Transpose + Reverse Rows**

**Key Insight:** 90° clockwise rotation = transpose (swap across diagonal) + reverse each row. This works because transposing swaps rows/columns, and reversing adjusts the order.

**Algorithm:**

1.  Transpose matrix:
    
    -   For i from 0 to n-1:
        
        -   For j from i+1 to n-1:
            
            -   Swap matrix\[i\]\[j\] with matrix\[j\]\[i\]
                
2.  Reverse each row:
    
    -   For each row: row.reverse()
        

**Example:**

`Original: Transpose: Reverse rows: 1 2 3 1 4 7 7 4 1 4 5 6 → 2 5 8 → 8 5 2 7 8 9 3 6 9 9 6 3`

**Rotations:**

-   90° CW: transpose + reverse rows
    
-   90° CCW: reverse rows + transpose (or transpose + reverse columns)
    
-   180°: reverse rows + reverse columns
    

**Why It Works:**

-   Transpose: element (i,j) → (j,i)
    
-   Reverse row: element (i,j) → (i, n-1-j)
    
-   Combined: (i,j) → (j, n-1-i) = 90° clockwise
    

**Time Complexity:** O(n²) - visit each element twice **Space Complexity:** O(1) - in-place

**Time: O(n²)**

**Space: O(1)**

```
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        
        // Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // Reverse rows
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
}
```
