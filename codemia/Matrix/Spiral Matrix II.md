# Spiral Matrix II
Given a positive integer n, generate an n x n matrix filled with elements from 1 to n² in spiral order.

Example 1:
Input: 3
Output: [[1,2,3],[8,9,4],[7,6,5]]

**Approach: Layer-by-Layer Spiral Fill**

**Key Insight:** Use four boundaries (top, bottom, left, right) to track the unfilled area. Fill in spiral order: right along top, down along right, left along bottom, up along left. Shrink boundaries after each direction.

**Algorithm:**

1.  Initialize n×n matrix with zeros
    
2.  Set boundaries: top=0, bottom=n-1, left=0, right=n-1
    
3.  Set num = 1
    
4.  While num ≤ n²:
    
    -   Fill top row left→right, increment top
        
    -   Fill right column top→bottom, decrement right
        
    -   Fill bottom row right→left, decrement bottom
        
    -   Fill left column bottom→top, increment left
        
5.  Return matrix
    

**Example:** n = 3

`Step 1: →→→ [1,2,3] [_,_,_] [_,_,_] Step 2: ↓ [1,2,3] ↓ [_,_,4] [_,_,5] Step 3: ←← [1,2,3] [_,_,4] [7,6,5] Step 4: ↑ [1,2,3] [8,_,4] [7,6,5] Step 5: → [1,2,3] [8,9,4] [7,6,5]`

**Time Complexity:** O(n²) - fill each cell once **Space Complexity:** O(n²) - output matrix (O(1) extra)

**Time: O(n^2)**

**Space: O(n^2)**

```
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int top = 0, bottom = n - 1, left = 0, right = n - 1;
        int num = 1;
        while (num <= n * n) {
            for (int i = left; i <= right; i++) matrix[top][i] = num++;
            top++;
            for (int i = top; i <= bottom; i++) matrix[i][right] = num++;
            right--;
            for (int i = right; i >= left; i--) matrix[bottom][i] = num++;
            bottom--;
            for (int i = bottom; i >= top; i--) matrix[i][left] = num++;
            left++;
        }
        return matrix;
    }
}
```
