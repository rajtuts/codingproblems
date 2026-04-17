# Reconstruct Itinerary
You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.

Example 1:
Input: [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
Output: ["JFK","MUC","LHR","SFO","SJC"]

**Approach: Hierholzer's Algorithm (Eulerian Path)**

**Key Insight:** This is finding an Eulerian path - visiting every edge exactly once. Use Hierholzer's: DFS and add to result when no more edges to traverse.

**Algorithm:**

1.  Build adjacency list, sorted in reverse (for lexicographic order)
    
2.  DFS from "JFK":
    
    -   While current airport has outgoing flights:
        
        -   Pop next destination, recurse
            
    -   When stuck, add current airport to result
        
3.  Reverse result (we built it backward)
    

**Example:** tickets = \[\["JFK","MUC"\],\["MUC","LHR"\],\["LHR","SFO"\],\["SFO","SJC"\]\]

-   From JFK→MUC→LHR→SFO→SJC (no more edges)
    
-   Build result backward: SJC, SFO, LHR, MUC, JFK
    
-   Reverse: JFK→MUC→LHR→SFO→SJC
    

**Time Complexity:** O(E log E) - sorting tickets **Space Complexity:** O(E) - graph and result

**Time: O(E log E)**

**Space: O(E)**

```
class Solution {
    public List<String> findItinerary(String[][] tickets) {
        Map<String, PriorityQueue<String>> adj = new HashMap<>();
        for (String[] ticket : tickets) {
            adj.putIfAbsent(ticket[0], new PriorityQueue<>());
            adj.get(ticket[0]).add(ticket[1]);
        }

        LinkedList<String> res = new LinkedList<>();
        dfs("JFK", adj, res);
        return res;
    }

    private void dfs(String curr, Map<String, PriorityQueue<String>> adj, LinkedList<String> res) {
        PriorityQueue<String> pq = adj.get(curr);
        while (pq != null && !pq.isEmpty()) {
            dfs(pq.poll(), adj, res);
        }
        res.addFirst(curr);
    }
}
```
