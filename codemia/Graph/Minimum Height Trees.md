# Minimum Height Trees
A tree is an undirected graph in which any two vertices are connected by exactly one path. Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges, you can choose any node of the tree as the root. The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf. Return a list of all MHTs root labels. You can return the answer in any order.

Example 1:
Input: {"n":4,"edges":[[1,0],[1,2],[1,3]]}
Output: [1]

**Approach: Topological Sort (Leaf Removal)**

**Key Insight:** The center of a tree minimizes the maximum distance to all nodes. Remove leaves layer by layer until 1-2 nodes remain.

**Algorithm:**

1.  Build adjacency list and find initial leaves (degree 1)
    
2.  Remove leaves and update neighbors' degrees
    
3.  Add new leaves to the queue
    
4.  Repeat until ≤2 nodes remain
    

**Time Complexity:** O(n) **Space Complexity:** O(n)

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int[] findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return new int[]{0};

        Set<Integer>[] graph = new HashSet[n];
        for (int i = 0; i < n; i++) graph[i] = new HashSet<>();
        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (graph[i].size() == 1) leaves.add(i);
        }

        int remaining = n;
        while (remaining > 2) {
            remaining -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for (int leaf : leaves) {
                int neighbor = graph[leaf].iterator().next();
                graph[neighbor].remove(leaf);
                if (graph[neighbor].size() == 1) newLeaves.add(neighbor);
            }
            leaves = newLeaves;
        }

        return leaves.stream().mapToInt(i -> i).toArray();
    }
}
```
