# Rotting Oranges
You are given an m x n grid where each cell can have one of three values: 0 (empty), 1 (fresh orange), or 2 (rotten orange). Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten. Return the minimum number of minutes until no cell has a fresh orange. If impossible, return -1.

Example 1:
Input: [[2,1,1],[1,1,0],[0,1,1]]
Output: 4

**Approach: Multi-source BFS**

**Key Insight:** Start BFS simultaneously from all rotten oranges. Each BFS step represents one minute. Track time until all fresh oranges are rotten.

**Algorithm:**

1.  Add all rotten oranges to queue, count fresh oranges
    
2.  BFS: for each rotten orange, rot adjacent fresh oranges
    
3.  Increment time after each complete level
    
4.  If fresh count > 0 at end: return -1 (impossible)
    
5.  Return time (or 0 if no fresh oranges initially)
    

**Example:** grid = \[\[2,1,1\],\[1,1,0\],\[0,1,1\]\]

-   Initial: queue=\[(0,0)\], fresh=6
    
-   Minute 1: (0,1) and (1,0) rot, fresh=4
    
-   Minute 2: (0,2), (1,1) rot, fresh=2
    
-   Minute 3: (2,1) rots, fresh=1
    
-   Minute 4: (2,2) rots, fresh=0
    
-   Return 4 ✓
    

**Time Complexity:** O(m × n) - visit each cell once **Space Complexity:** O(m × n) - queue size

**Time: O(m\*n)**

**Space: O(m\*n)**

```
class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                else if (grid[i][j] == 1) fresh++;
            }
        }

        int minutes = 0;
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        while (!queue.isEmpty() && fresh > 0) {
            minutes++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                for (int[] d : dirs) {
                    int nr = curr[0] + d[0], nc = curr[1] + d[1];
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        fresh--;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }
}
```
