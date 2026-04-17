# Swim in Rising Water
You are given an n x n integer matrix grid where each value grid[i][j] represents the elevation at that point (i, j). The rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square to another 4-directionally adjacent square if and only if the elevation of both squares individually are at most t. You can swim infinite distances in zero time. Return the least time until you can reach the bottom right square (n - 1, n - 1) starting from the top left square (0, 0).

Example 1:
Input: {"grid":[[0,2],[1,3]]}
Output: 3

**Approach: Modified Dijkstra's**

**Key Insight:** Instead of summing edge weights, track the maximum elevation along the path. The answer is the minimum possible maximum elevation.

**Algorithm:**

1.  Use min-heap with (max\_elevation\_so\_far, row, col)
    
2.  Start at (0,0) with elevation grid\[0\]\[0\]
    
3.  For each cell:
    
    -   Pop minimum max-elevation path
        
    -   If reached (n-1, n-1): return current max elevation
        
    -   For each unvisited neighbor:
        
        -   Push (max(current, neighbor\_elevation), neighbor)
            

**Example:** grid = \[\[0,2\],\[1,3\]\]

-   Start: (0, 0, 0) with elevation 0
    
-   Move right: max(0, 2) = 2
    
-   Move down from (0,0): max(0, 1) = 1
    
-   From (1,0) to (1,1): max(1, 3) = 3
    
-   Result: 3
    

**Time Complexity:** O(n² log n) - each cell in heap **Space Complexity:** O(n²) - visited set and heap

**Time: O(n² log n)**

**Space: O(n²)**

```
class Solution {
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int left = grid[0][0], right = n * n - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (canReach(grid, mid)) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    boolean canReach(int[][] grid, int t) {
        int n = grid.length;
        if (grid[0][0] > t) return false;
        boolean[][] visited = new boolean[n][n];
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0});
        visited[0][0] = true;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            if (cell[0] == n - 1 && cell[1] == n - 1) return true;
            for (int[] d : dirs) {
                int nx = cell[0] + d[0], ny = cell[1] + d[1];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !visited[nx][ny] && grid[nx][ny] <= t) {
                    visited[nx][ny] = true;
                    stack.push(new int[]{nx, ny});
                }
            }
        }
        return false;
    }
}
```
