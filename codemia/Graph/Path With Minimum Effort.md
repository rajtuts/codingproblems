# Path With Minimum Effort
You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1). The effort required to travel from one cell to an adjacent cell is the absolute difference of their heights. Return the minimum effort required to travel from the top-left cell to the bottom-right cell.

Example 1:
Input: [[1,2,2],[3,8,2],[5,3,5]]
Output: 2

**Approach: Modified Dijkstra's Algorithm**

**Key Insight:** Path effort = maximum height difference along the path, not sum. Use Dijkstra's but track max effort instead of cumulative distance.

**Algorithm:**

1.  Use min-heap with (effort, row, col)
    
2.  Start at (0,0) with effort 0
    
3.  For each cell popped:
    
    -   If destination: return current effort
        
    -   For each neighbor:
        
        -   new\_effort = max(current\_effort, abs(height\_diff))
            
        -   If better than known, update and push to heap
            

**Example:** heights = \[\[1,2,2\],\[3,8,2\],\[5,3,5\]\]

-   From (0,0)=1 to (0,1)=2: effort = max(0, |2-1|) = 1
    
-   From (0,1)=2 to (0,2)=2: effort = max(1, |2-2|) = 1
    
-   From (0,2)=2 to (1,2)=2: effort = max(1, |2-2|) = 1
    
-   Path with effort 2 reaches destination
    

**Time Complexity:** O(m×n×log(m×n)) - Dijkstra with heap **Space Complexity:** O(m×n) - distance array and heap

**Time: O(m\*n\*log(m\*n))**

**Space: O(m\*n)**

```
class Solution {
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] dist = new int[m][n];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = 0;
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        heap.offer(new int[]{0, 0, 0});
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!heap.isEmpty()) {
            int[] curr = heap.poll();
            int effort = curr[0], r = curr[1], c = curr[2];
            if (r == m - 1 && c == n - 1) return effort;
            if (effort > dist[r][c]) continue;
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int newEffort = Math.max(effort, Math.abs(heights[nr][nc] - heights[r][c]));
                    if (newEffort < dist[nr][nc]) {
                        dist[nr][nc] = newEffort;
                        heap.offer(new int[]{newEffort, nr, nc});
                    }
                }
            }
        }

        return dist[m - 1][n - 1];
    }
}
```
