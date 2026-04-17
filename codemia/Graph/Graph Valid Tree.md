# Graph Valid Tree
Given n nodes labeled from 0 to n - 1 and a list of undirected edges, write a function to check whether these edges make up a valid tree.

Example 1:
Input: {"n":5,"edges":[[0,1],[0,2],[0,3],[1,4]]}
Output: true

**Approach: Union-Find**

**Key Insight:** A valid tree has exactly n-1 edges and no cycles. We can check both properties using Union-Find.

**Algorithm:**

1.  If edges.length != n-1: return False (must have exactly n-1 edges)
    
2.  Initialize Union-Find with n components
    
3.  For each edge (u, v):
    
    -   Find roots of u and v
        
    -   If same root: cycle detected → return False
        
    -   Else: union the components
        
4.  Return True (n-1 edges, no cycles implies connected tree)
    

**Example:** n=5, edges=\[\[0,1\],\[0,2\],\[0,3\],\[1,4\]\]

-   4 edges = 5-1 ✓
    
-   No cycle found during union operations ✓
    
-   Result: True
    

**Time Complexity:** O(E α(n)) - process each edge once **Space Complexity:** O(n) - parent array

**Time: O(E α(n))**

**Space: O(n)**

```
class Solution {
    public boolean validTree(int n, int[][] edges) {
        if (n == 0) return true;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        
        Set<Integer> visit = new HashSet<>();
        if (!dfs(0, -1, visit, adj)) return false;
        
        return visit.size() == n;
    }
    
    private boolean dfs(int i, int prev, Set<Integer> visit, List<List<Integer>> adj) {
        if (visit.contains(i)) return false;
        
        visit.add(i);
        for (int j : adj.get(i)) {
            if (j == prev) continue;
            if (!dfs(j, i, visit, adj)) return false;
        }
        return true;
    }
}
```
