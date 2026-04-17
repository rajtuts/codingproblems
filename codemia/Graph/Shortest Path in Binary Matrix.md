Shortest Path in Binary Matrix
Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. If there is no clear path, return -1. A clear path is a path from the top-left cell (0, 0) to the bottom-right cell (n-1, n-1) such that all the visited cells are 0. You may move 8-directionally (horizontal, vertical, and diagonal).

Example 1:
Input: [[0,0,0],[1,1,0],[1,1,0]]
Output: 4

**Approach: BFS with 8-Directional Movement**

**Key Insight:** BFS finds the shortest path in an unweighted graph. Each cell is a node, edges connect to 8 neighbors (including diagonals). First time we reach destination is the shortest path.

**Algorithm:**

1.  If start or end is blocked (1), return -1
    
2.  Initialize queue with (0, 0, 1) — start position with distance 1
    
3.  Mark (0, 0) as visited
    
4.  BFS: for each cell:
    
    -   If at destination: return distance
        
    -   For each of 8 directions:
        
        -   If in bounds, is 0, and unvisited: add to queue with distance+1
            
5.  If queue empty: return -1
    

**Example:** grid = \[\[0,1\],\[1,0\]\]

-   Start (0,0), distance 1
    
-   Check 8 neighbors, only (1,1) valid (diagonally)
    
-   Add (1,1) with distance 2
    
-   (1,1) is destination → return 2 ✓
    

**8 Directions:** (-1,-1), (-1,0), (-1,1) (0,-1), (0,1) (1,-1), (1,0), (1,1)

**Why BFS:**

-   Explores all paths of length k before length k+1
    
-   First path to destination is shortest
    
-   Unlike DFS which may find longer paths first
    

**Time Complexity:** O(n²) - visit each cell at most once **Space Complexity:** O(n²) - queue and visited set

**Time: O(n^2)**

**Space: O(n^2)**


```
class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;

        int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0, 1});
        boolean[][] visited = new boolean[n][n];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1], dist = curr[2];
            if (r == n-1 && c == n-1) return dist;

            for (int[] d : directions) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, dist + 1});
                }
            }
        }

        return -1;
    }
}
```
