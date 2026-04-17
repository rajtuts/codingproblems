Evaluate Division
You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable. You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?. Return the answers to all queries.

Example 1:
Input: {"equations":[["a","b"],["b","c"]],"values":[2,3],"queries":[["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]}
Output: [6.0,0.5,-1.0,1.0,-1.0]


**Approach: Graph + BFS**

**Key Insight:** Build a directed weighted graph where A/B = k means edge A→B with weight k and edge B→A with weight 1/k. Use BFS to find path and multiply weights.

**Algorithm:**

1.  Build graph: for A/B = k, add A→B (weight k) and B→A (weight 1/k)
    
2.  For each query \[C, D\]:
    
    -   If C or D not in graph: return -1
        
    -   BFS from C to D, multiplying edge weights along path
        
    -   If path found: return product, else -1
        

**Example:** equations = \[\["a","b"\],\["b","c"\]\], values = \[2.0,3.0\]

-   Graph: a→b (2), b→a (0.5), b→c (3), c→b (0.333)
    
-   Query \["a","c"\]:
    
    -   BFS: a→b (×2) → b→c (×3) = 6.0 ✓
        
-   Query \["b","a"\]:
    
    -   BFS: b→a (×0.5) = 0.5 ✓
        

**Why BFS Works:**

-   Find any path between variables
    
-   Multiply weights gives the ratio
    
-   Graph allows transitive calculations
    

**Time Complexity:** O(Q × (V + E)) - Q queries, BFS each **Space Complexity:** O(V + E) - graph storage

**Time: O(Q \* (V + E))**

**Space: O(V + E)**

```
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        // Build graph
        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            graph.putIfAbsent(a, new HashMap<>());
            graph.putIfAbsent(b, new HashMap<>());
            graph.get(a).put(b, values[i]);
            graph.get(b).put(a, 1.0 / values[i]);
        }

        double[] result = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            result[i] = bfs(graph, queries.get(i).get(0), queries.get(i).get(1));
        }
        return result;
    }

    private double bfs(Map<String, Map<String, Double>> graph, String src, String dst) {
        if (!graph.containsKey(src) || !graph.containsKey(dst)) return -1.0;
        if (src.equals(dst)) return 1.0;

        Set<String> visited = new HashSet<>();
        Queue<double[]> queue = new LinkedList<>();
        Map<String, Integer> nodeIndex = new HashMap<>();
        int idx = 0;
        for (String key : graph.keySet()) nodeIndex.put(key, idx++);

        queue.add(new double[]{nodeIndex.get(src), 1.0});
        visited.add(src);

        while (!queue.isEmpty()) {
            double[] curr = queue.poll();
            String node = "";
            for (Map.Entry<String, Integer> e : nodeIndex.entrySet()) {
                if (e.getValue() == (int)curr[0]) { node = e.getKey(); break; }
            }
            if (node.equals(dst)) return curr[1];
            for (Map.Entry<String, Double> neighbor : graph.get(node).entrySet()) {
                if (!visited.contains(neighbor.getKey())) {
                    visited.add(neighbor.getKey());
                    queue.add(new double[]{nodeIndex.get(neighbor.getKey()), curr[1] * neighbor.getValue()});
                }
            }
        }
        return -1.0;
    }
}
```
