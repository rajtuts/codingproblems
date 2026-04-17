# Critical Connections in a Network
There are n servers numbered from 0 to n - 1 connected by undirected server-to-server connections forming a network where connections[i] = [ai, bi] represents a connection between servers ai and bi. Any server can reach other servers directly or indirectly through the network. A critical connection is a connection that, if removed, will make some servers unable to reach some other server. Return all critical connections in the network in any order.

Example 1:
Input: {"n":4,"connections":[[0,1],[1,2],[2,0],[1,3]]}
Output: [[1,3]]

**Approach: Tarjan's Algorithm for Bridges**

**Key Insight:** An edge (u,v) is a bridge if there's no back edge from the subtree rooted at v to an ancestor of u.

**Algorithm:**

1.  DFS traversal tracking discovery time (disc) and lowest reachable time (low)
    
2.  For each edge (u,v) where v is a child: if low\[v\] > disc\[u\], it's a bridge
    
3.  Update low\[u\] = min(low\[u\], low\[v\]) after visiting children
    

**Time Complexity:** O(V + E) **Space Complexity:** O(V + E)

**Time: O(V + E)**

**Space: O(V + E)**

```
class Solution {
    private List<Integer>[] graph;
    private int[] disc, low;
    private List<List<Integer>> bridges;
    private int time;

    public List<List<Integer>> criticalConnections(int n, int[][] connections) {
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] conn : connections) {
            graph[conn[0]].add(conn[1]);
            graph[conn[1]].add(conn[0]);
        }

        disc = new int[n];
        low = new int[n];
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
        bridges = new ArrayList<>();
        time = 0;

        dfs(0, -1);
        return bridges;
    }

    private void dfs(int node, int parent) {
        disc[node] = low[node] = time++;

        for (int neighbor : graph.get(node)) {
            if (neighbor == parent) continue;
            if (disc[neighbor] == -1) {
                dfs(neighbor, node);
                low[node] = Math.min(low[node], low[neighbor]);
                if (low[neighbor] > disc[node]) {
                    bridges.add(Arrays.asList(node, neighbor));
                }
            } else {
                low[node] = Math.min(low[node], disc[neighbor]);
            }
        }
    }
}
```
