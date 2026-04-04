# Walls and Gates
Given a m x n grid where -1 is a wall, 0 is a gate, and INF (2147483647) is an empty room. Fill each empty room with the distance to its nearest gate. If impossible to reach a gate, leave it as INF.  

## Example 1:  
Input: {"rooms":[[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]}  
Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]  

**Approach: Multi-source BFS**  

**Key Insight:** Instead of BFS from each room to find nearest gate, start BFS from all gates simultaneously. Each room gets the distance from whichever gate reaches it first.  

**Algorithm:**  
1.  Find all gates (cells with value 0) and add to queue      
2.  BFS from all gates simultaneously:      
    -   For each cell, check neighbors          
    -   If neighbor is empty room (INF), update distance and add to queue          
3.  Each room gets distance from nearest gate      

**Example:** (INF = 2147483647, -1 = wall, 0 = gate)  
-   Start: queue = all cells with 0      
-   BFS level 1: rooms adjacent to gates get value 1      
-   BFS level 2: rooms 2 steps from nearest gate get value 2      
-   Continue until all reachable rooms updated      

**Time Complexity:** O(m×n) - each cell visited once **Space Complexity:** O(m×n) - queue size  
**Time: O(m \* n)**  
**Space: O(m \* n)**  

```
class Solution {
    public void wallsAndGates(int[][] rooms) {
        if (rooms.length == 0) return;
        int INF = Integer.MAX_VALUE;
        int m = rooms.length, n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) queue.offer(new int[]{i, j});
            }
        }

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0], c = cell[1];
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && rooms[nr][nc] == INF) {
                    rooms[nr][nc] = rooms[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }
}
```
