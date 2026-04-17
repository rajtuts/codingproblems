# Max Area of Island
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical). Return the maximum area of an island in grid. If there is no island, return 0.

Example 1:
Input: [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
Output: 6

**Approach: DFS/BFS Grid Traversal**

This is a classic connected components problem on a grid.

**Algorithm:**

1.  Iterate through every cell in the grid.
    
2.  When a land cell (1) is found:
    
    -   Start DFS/BFS to explore the entire island.
        
    -   Count all connected land cells (area).
        
    -   Mark visited cells (change to 0) to avoid recounting.
        
3.  Track the maximum area seen.
    

**DFS Implementation:**

`def dfs(i, j): # Base case: out of bounds or water if i < 0 or i >= rows or j < 0 or j >= cols or grid[i][j] == 0: return 0 grid[i][j] = 0 # Mark visited # Explore all 4 directions and sum areas return 1 + dfs(i+1,j) + dfs(i-1,j) + dfs(i,j+1) + dfs(i,j-1)`

**Why modify the grid?**

-   Simplest way to track visited cells.
    
-   Alternative: use a separate visited set.
    

**BFS Alternative:**

-   Use a queue instead of recursion.
    
-   Same time complexity, but avoids stack overflow for huge islands.
    

**Time Complexity:** O(m × n) - each cell visited at most once **Space Complexity:** O(m × n) - recursion stack in worst case

**Time: O(m \* n)**

**Space: O(m \* n)**

```
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        int maxArea = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, r, c));
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] == 0) {
            return 0;
        }
        grid[r][c] = 0; // Mark as visited
        int area = 1;
        area += dfs(grid, r + 1, c);
        area += dfs(grid, r - 1, c);
        area += dfs(grid, r, c + 1);
        area += dfs(grid, r, c - 1);
        return area;
    }
}
```
