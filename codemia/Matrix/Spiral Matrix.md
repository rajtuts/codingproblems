# Spiral Matrix
Given an m x n matrix, return all elements of the matrix in spiral order.

Example 1:
Input: [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]

**Approach: Layer-by-Layer Spiral Traversal**

**Key Insight:** Use four boundaries (top, bottom, left, right) to define the current layer. Traverse in spiral order: right along top, down along right, left along bottom, up along left. Shrink boundaries after each direction.

**Algorithm:**

1.  Initialize: top=0, bottom=m-1, left=0, right=n-1
    
2.  While top <= bottom AND left <= right:
    
    -   Go right: for j in \[left, right\]: add matrix\[top\]\[j\]
        
    -   top++
        
    -   Go down: for i in \[top, bottom\]: add matrix\[i\]\[right\]
        
    -   right--
        
    -   If top <= bottom:
        
        -   Go left: for j in \[right, left\]: add matrix\[bottom\]\[j\]
            
        -   bottom--
            
    -   If left <= right:
        
        -   Go up: for i in \[bottom, top\]: add matrix\[i\]\[left\]
            
        -   left++
            
3.  Return result
    

**Example:**

`1 2 3 4 5 6 → [1,2,3,6,9,8,7,4,5] 7 8 9`

-   Right: 1,2,3 → top=1
    
-   Down: 6,9 → right=1
    
-   Left: 8,7 → bottom=1
    
-   Up: 4 → left=1
    
-   Right: 5 → done
    

**Time Complexity:** O(m×n) - visit each cell once **Space Complexity:** O(1) - excluding output

**Time: O(m\*n)**

**Space: O(1)**

```
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int left = 0, right = matrix[0].length;
        int top = 0, bottom = matrix.length;
        
        while (left < right && top < bottom) {
            for (int i = left; i < right; i++) res.add(matrix[top][i]);
            top++;
            
            for (int i = top; i < bottom; i++) res.add(matrix[i][right - 1]);
            right--;
            
            if (!(left < right && top < bottom)) break;
            
            for (int i = right - 1; i >= left; i--) res.add(matrix[bottom - 1][i]);
            bottom--;
            
            for (int i = bottom - 1; i >= top; i--) res.add(matrix[i][left]);
            left++;
        }
        return res;
    }
}
````
