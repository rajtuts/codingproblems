# Min Cost to Connect All Points
You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi, yi]. The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi - yj|. Return the minimum cost to make all points connected.

Example 1:
Input: [[0,0],[2,2],[3,10],[5,2],[7,0]]
Output: 20

**Approach: Prim's Algorithm (MST)**

**Key Insight:** Connect all points with minimum total cost is the Minimum Spanning Tree problem. Edge weight is Manhattan distance: |x1-x2| + |y1-y2|.

**Algorithm:**

1.  Start from any point (e.g., point 0)
    
2.  Use min-heap with (distance, point\_index)
    
3.  Pop minimum, add to MST if not visited
    
4.  Push all edges from new point to unvisited points
    
5.  Repeat until n points in MST
    

**Example:** points = \[\[0,0\],\[2,2\],\[3,10\],\[5,2\],\[7,0\]\]

-   Start at 0, add edges to all others
    
-   Pick closest, continue until all connected
    
-   Total: 20
    

**Time Complexity:** O(n² log n) - complete graph, heap operations **Space Complexity:** O(n²) - potential edges in heap

**Time: O(n² log n)**

**Space: O(n²)**

```
class Solution {
    public int minCostConnectPoints(int[][] points) {
        int N = points.length;
        List<int[]>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();
        
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int dist = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                adj[i].add(new int[]{dist, j});
                adj[j].add(new int[]{dist, i});
            }
        }
        
        int res = 0;
        Set<Integer> visit = new HashSet<>();
        PriorityQueue<int[]> minH = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        minH.add(new int[]{0, 0});
        
        while (visit.size() < N) {
            int[] curr = minH.poll();
            if (visit.contains(curr[1])) continue;
            res += curr[0];
            visit.add(curr[1]);
            for (int[] nei : adj[curr[1]]) {
                if (!visit.contains(nei[1])) {
                    minH.add(nei);
                }
            }
        }
        return res;
    }
}
```
