# Redundant Connection
In this problem, a tree is an undirected graph that is connected and has no cycles. You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added. The added edge has two different vertices chosen from 1 to n, and was not an edge that already existed. The graph is represented as an array edges of length n where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the graph. Return an edge that can be removed so that the resulting graph is a tree of n nodes. If there are multiple answers, return the answer that occurs last in the input.

Example 1:
Input: [[1,2],[1,3],[2,3]]
Output: [2,3]

**Approach: Union-Find**

**Key Insight:** A tree has no cycles. Adding an edge between already-connected nodes creates a cycle. That edge is redundant.

**Algorithm:**

1.  Initialize parent array where parent\[i\] = i
    
2.  For each edge (u, v):
    
    -   Find roots of u and v
        
    -   If same root: already connected → this edge is redundant
        
    -   Else: union the components
        
3.  Return the first edge that creates a cycle
    

**Example:** edges = \[\[1,2\],\[1,3\],\[2,3\]\]

-   Edge \[1,2\]: union 1,2
    
-   Edge \[1,3\]: union 1,3
    
-   Edge \[2,3\]: 2 and 3 already in same component → return \[2,3\]
    

**Time Complexity:** O(n α(n)) - Union-Find with path compression **Space Complexity:** O(n) - parent array

**Time: O(n α(n))**

**Space: O(n)**

```
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int[] par = new int[edges.length + 1];
        for (int i = 0; i < par.length; i++) par[i] = i;
        
        for (int[] edge : edges) {
            if (find(edge[0], par) == find(edge[1], par)) return edge;
            union(edge[0], edge[1], par);
        }
        return new int[0];
    }
    
    private int find(int n, int[] par) {
        while (n != par[n]) {
            par[n] = par[par[n]];
            n = par[n];
        }
        return n;
    }
    
    private void union(int n1, int n2, int[] par) {
        int p1 = find(n1, par);
        int p2 = find(n2, par);
        par[p1] = p2;
    }
}
```
