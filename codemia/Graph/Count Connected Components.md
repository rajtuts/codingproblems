# Count Connected Components
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
    public int countComponents(int n, int[][] edges) {
        int[] par = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            par[i] = i;
            rank[i] = 1;
        }
        
        int res = n;
        for (int[] edge : edges) {
            res -= union(edge[0], edge[1], par, rank);
        }
        return res;
    }
    
    private int find(int n1, int[] par) {
        int res = n1;
        while (res != par[res]) {
            par[res] = par[par[res]];
            res = par[res];
        }
        return res;
    }
    
    private int union(int n1, int n2, int[] par, int[] rank) {
        int p1 = find(n1, par);
        int p2 = find(n2, par);
        if (p1 == p2) return 0;
        
        if (rank[p1] > rank[p2]) {
            par[p2] = p1;
            rank[p1] += rank[p2];
        } else {
            par[p1] = p2;
            rank[p2] += rank[p1];
        }
        return 1;
    }
}
```
