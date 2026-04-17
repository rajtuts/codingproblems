# Redundant Connection II
In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which has no parents. The given input is a directed graph that started as a rooted tree with n nodes (with distinct values from 1 to n), with one additional directed edge added. Return an edge that can be removed so that the resulting graph is a rooted tree of n nodes.

Example 1:
Input: [[1,2],[1,3],[2,3]]
Output: [2,3]

**Approach: Union-Find with Two Cases**

**Case 1:** Node with two parents - one edge must be removed **Case 2:** Cycle exists - cycle edge must be removed

**Algorithm:**

1.  Find if any node has two parents (candidate1, candidate2)
    
2.  Skip candidate2 and run Union-Find
    
3.  If cycle found: return candidate1 if exists, else the cycle edge
    
4.  If no cycle: return candidate2
    

**Time Complexity:** O(n α(n)) **Space Complexity:** O(n)

**Time: O(n α(n))**

**Space: O(n)**

```
class Solution {
    int[] root;

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        int[] candidate1 = null, candidate2 = null;

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (parent[v] != 0) {
                candidate1 = new int[]{parent[v], v};
                candidate2 = new int[]{u, v};
            } else {
                parent[v] = u;
            }
        }

        root = new int[n + 1];
        for (int i = 0; i <= n; i++) root[i] = i;

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (candidate2 != null && u == candidate2[0] && v == candidate2[1]) continue;
            int pu = find(u), pv = find(v);
            if (pu == pv) return candidate1 != null ? candidate1 : edge;
            root[pu] = pv;
        }

        return candidate2;
    }

    int find(int x) {
        if (root[x] != x) root[x] = find(root[x]);
        return root[x];
    }
}
```
