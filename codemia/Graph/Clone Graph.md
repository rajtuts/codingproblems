# Clone Graph
Given a reference of a node in a connected undirected graph. Return a deep copy (clone) of the graph.

Example 1:
Input: [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]

**Approach: DFS with Hash Map**

**Key Insight:** Use a hash map to track already-cloned nodes. This:

1.  Prevents infinite loops in cyclic graphs
    
2.  Ensures each node is cloned exactly once
    
3.  Maintains correct neighbor references
    

**Algorithm:**

1.  If node already cloned → return the clone
    
2.  Create new clone node with same value
    
3.  Store in hash map: original → clone
    
4.  Recursively clone all neighbors
    
5.  Add cloned neighbors to clone's neighbor list
    

**Example:** Graph with nodes 1-2-3-4 forming a square

`Original: Clone: 1 ─── 2 1' ─── 2' │ │ → │ │ 4 ─── 3 4' ─── 3'`

**DFS Trace:**

`clone(1): create 1', add to map clone(2): create 2', add to map clone(3): create 3', add to map clone(4): create 4', add to map clone(1): already in map, return 1' clone(3): already in map, return 3' ...`

**Why Hash Map is Essential:**

-   Graph may have cycles (node 1 → 2 → 3 → 1)
    
-   Without tracking, we'd clone nodes infinitely
    
-   Hash map ensures we return existing clone for visited nodes
    

**Time Complexity:** O(V + E) - visit each node and edge once **Space Complexity:** O(V) - hash map and recursion stack

**Time: O(V + E)**

**Space: O(V)**


```
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;
        HashMap<Node, Node> oldToNew = new HashMap<>();
        return dfs(node, oldToNew);
    }
    
    public Node dfs(Node node, HashMap<Node, Node> map) {
        if (map.containsKey(node)) return map.get(node);
        
        Node copy = new Node(node.val);
        map.put(node, copy);
        
        for (Node nei : node.neighbors) {
            copy.neighbors.add(dfs(nei, map));
        }
        
        return copy;
    }
}

```
