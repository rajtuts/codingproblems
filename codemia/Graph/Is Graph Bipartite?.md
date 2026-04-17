# Is Graph Bipartite?
There is an undirected graph with n nodes, where each node is numbered between 0 and n - 1. You are given a 2D array graph, where graph[u] is an array of nodes that node u is adjacent to. Return true if and only if it is bipartite.

Example 1:
Input: [[1,3],[0,2],[1,3],[0,2]]
Output: true

**Approach: BFS Graph Coloring**

**Key Insight:** A bipartite graph can be 2-colored such that no adjacent nodes share the same color. Use BFS to assign colors; if we find a conflict, graph is not bipartite.

**Algorithm:**

1.  Build adjacency list from edges
    
2.  For each unvisited node (handle disconnected components):
    
    -   BFS: assign color 0 to start node
        
    -   For each neighbor: assign opposite color
        
    -   If neighbor already has same color: return False
        
3.  Return True if no conflicts found
    

**Example:** n=4, edges=\[\[1,2\],\[2,3\],\[3,4\]\]

-   Graph: 1-2, 2-3, 3-4
    
-   Color 1 with 0
    
-   Color 2 with 1 (opposite of 1)
    
-   Color 3 with 0 (opposite of 2)
    
-   Color 4 with 1 (opposite of 3)
    
-   No conflicts → True (bipartite) ✓
    

**Example:** n=3, edges=\[\[1,2\],\[2,3\],\[1,3\]\]

-   Triangle: 1-2, 2-3, 1-3
    
-   Color 1=0, 2=1, 3 should be 0 (from 2) but 1 (from 1) → conflict!
    
-   Result: False (not bipartite) ✗
    

**Time Complexity:** O(V + E) - visit each node and edge **Space Complexity:** O(V) - color array and queue

**Time: O(V + E)**

**Space: O(V)**

```
class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];

        for (int i = 0; i < n; i++) {
            if (color[i] == 0 && !bfs(graph, color, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean bfs(int[][] graph, int[] color, int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        color[start] = 1;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph[node]) {
                if (color[neighbor] == color[node]) return false;
                if (color[neighbor] == 0) {
                    color[neighbor] = -color[node];
                    queue.add(neighbor);
                }
            }
        }
        return true;
    }
}
````
