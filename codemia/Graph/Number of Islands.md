# Number of Islands
Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.

Example 1:
Input: [["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]
Output: 1

**Approach: DFS/BFS Flood Fill**

**Key Insight:** Each connected component of '1's is one island. When we find an unvisited '1', we've found a new island. Mark all connected land as visited to avoid counting it again.

**Algorithm (DFS):**

1.  Iterate through each cell in the grid
    
2.  When we find a '1':
    
    -   Increment island count
        
    -   DFS to mark all connected '1's as visited (change to '0' or use visited set)
        
3.  Return count
    

**DFS Helper:**

`def dfs(grid, i, j): if i < 0 or i >= rows or j < 0 or j >= cols or grid[i][j] != '1': return grid[i][j] = '0' # Mark visited dfs(grid, i+1, j) # Down dfs(grid, i-1, j) # Up dfs(grid, i, j+1) # Right dfs(grid, i, j-1) # Left`

**BFS Alternative:** Use queue instead of recursion. Better for very deep grids (avoids stack overflow).

**Time: O(m×n)** - visit each cell once **Space: O(m×n)** - worst case recursion depth for DFS, or queue size for BFS

**Related Problems:** Max Area of Island, Surrounded Regions, Pacific Atlantic Water Flow

**Time: O(m × n)**

**Space: O(m × n)**

```
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int rows = grid.length, cols = grid[0].length;
        int islands = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    bfs(grid, r, c);
                    islands++;
                }
            }
        }
        return islands;
    }
    
    private void bfs(char[][] grid, int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{r, c});
        grid[r][c] = '0'; // Mark visited
        
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int row = curr[0], col = curr[1];
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            
            for (int[] d : dirs) {
                int nr = row + d[0], nc = col + d[1];
                if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length &&
                    grid[nr][nc] == '1') {
                    q.add(new int[]{nr, nc});
                    grid[nr][nc] = '0';
                }
            }
        }
    }
}
