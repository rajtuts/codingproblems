# Longest Increasing Path in a Matrix
Given an m x n integers matrix, return the length of the longest increasing path in matrix. From each cell, you can either move in four directions: left, right, up, or down. You may not move diagonally or move outside the boundary.

Example 1:
Input: [[9,9,4],[6,6,8],[2,1,1]]
Output: 4

**Approach: DFS with Memoization**

**Key Insight:** For each cell, find the longest increasing path starting from it. Use memoization to avoid recomputing—once a cell's longest path is known, it's the same regardless of how we reached it.

**Algorithm:**

1.  Create memo dictionary
    
2.  Define dfs(r, c):
    
    -   If (r,c) in memo: return memo\[(r,c)\]
        
    -   result = 1 (at least this cell)
        
    -   For each of 4 neighbors:
        
        -   If neighbor in bounds AND neighbor.val > current.val:
            
            -   result = max(result, 1 + dfs(neighbor))
                
    -   memo\[(r,c)\] = result
        
    -   Return result
        
3.  Return max(dfs(r,c) for all cells)
    

**Example:** matrix = \[\[9,9,4\],\[6,6,8\],\[2,1,1\]\]

-   From cell (2,1)=1: → (1,1)=6 → (0,0)=9 or (0,1)=9
    
    -   Path: 1→6→9, length 3
        
-   From cell (1,2)=8: → (0,2)=4? No (4<8)
    
    -   Path: 8→9, length 2
        
-   Best: 1→2→6→9 (starting from 2,0), length 4 ✓
    

**Why No Visited Set Needed:**

-   Paths are strictly increasing
    
-   Can never revisit a cell (would violate increasing)
    

**Time Complexity:** O(m×n) - each cell computed once **Space Complexity:** O(m×n) - memoization table

**Time: O(m \* n)**

**Space: O(m \* n)**

```
class Solution {
    int[][] memo;
    int[][] matrix;
    int m, n;
    int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0) return 0;
        this.matrix = matrix;
        m = matrix.length;
        n = matrix[0].length;
        memo = new int[m][n];

        int result = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                result = Math.max(result, dfs(r, c));
            }
        }
        return result;
    }

    private int dfs(int r, int c) {
        if (memo[r][c] != 0) return memo[r][c];

        int maxLen = 1;
        for (int[] d : directions) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n && matrix[nr][nc] > matrix[r][c]) {
                maxLen = Math.max(maxLen, 1 + dfs(nr, nc));
            }
        }

        memo[r][c] = maxLen;
        return maxLen;
    }
}
```
