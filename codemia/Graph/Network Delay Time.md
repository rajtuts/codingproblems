Network Delay Time
You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target. We send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.

Example 1:
Input: {"times":[[2,1,1],[2,3,1],[3,4,1]],"n":4,"k":2}
Output: 2

**Approach: Dijkstra's Algorithm**

**Key Insight:** Signal propagates at shortest path time. Use Dijkstra to find shortest distance to all nodes. The answer is the maximum of these distances.

**Algorithm:**

1.  Build adjacency list from edges
    
2.  Use min-heap with (distance, node), start from k
    
3.  Process nodes in order of increasing distance
    
4.  Track minimum distance to each node
    
5.  Return max distance if all n nodes reached, else -1
    

**Example:** times=\[\[2,1,1\],\[2,3,1\],\[3,4,1\]\], n=4, k=2

-   From 2: dist\[1\]=1, dist\[3\]=1
    
-   From 3: dist\[4\]=2
    
-   All reached: max(0,1,1,2) = 2
    

**Time Complexity:** O((V+E) log V) - Dijkstra with priority queue **Space Complexity:** O(V+E) - graph and distance map

**Time: O((V+E) log V)**

**Space: O(V+E)**

```
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] t : times) {
            adj.putIfAbsent(t[0], new ArrayList<>());
            adj.get(t[0]).add(new int[]{t[1], t[2]});
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[]{0, k});
        Set<Integer> visit = new HashSet<>();
        int t = 0;
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int w1 = curr[0], n1 = curr[1];
            if (visit.contains(n1)) continue;
            
            visit.add(n1);
            t = Math.max(t, w1);
            
            if (adj.containsKey(n1)) {
                for (int[] next : adj.get(n1)) {
                    int n2 = next[0], w2 = next[1];
                    if (!visit.contains(n2)) {
                        pq.add(new int[]{w1 + w2, n2});
                    }
                }
            }
        }
        return visit.size() == n ? t : -1;
    }
}
```
