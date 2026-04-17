Number of Connected Components
You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph. Return the number of connected components in the graph.

Example 1:
Input: {"n":5,"edges":[[0,1],[1,2],[3,4]]}
Output: 2

**Approach: Union-Find**

**Key Insight:** Start with n isolated components. Each edge that connects two different components reduces the count by 1.

**Algorithm:**

1.  Initialize n components (parent\[i\] = i)
    
2.  For each edge (u, v):
    
    -   Find roots of u and v
        
    -   If different roots: union them, decrement n
        
3.  Return n (remaining components)
    

**Example:** n=5, edges=\[\[0,1\],\[1,2\],\[3,4\]\]

-   Initially: 5 components
    
-   Edge \[0,1\]: merge → 4 components
    
-   Edge \[1,2\]: merge → 3 components
    
-   Edge \[3,4\]: merge → 2 components
    
-   Result: 2
    

**Time Complexity:** O(E α(n)) - α is inverse Ackermann, nearly constant **Space Complexity:** O(n) - parent array

**Time: O(E α(n))**

**Space: O(n)**

```
class Solution {
    int[] parent, rank;

    public int countComponents(int n, int[][] edges) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int components = n;
        for (int[] edge : edges) {
            if (union(edge[0], edge[1])) components--;
        }
        return components;
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false;
        if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
        parent[py] = px;
        if (rank[px] == rank[py]) rank[px]++;
        return true;
    }
}
```
