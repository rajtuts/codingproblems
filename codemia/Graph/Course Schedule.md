Course Schedule
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai. Return true if you can finish all courses. Otherwise, return false.

Example 1:
Input: {"numCourses":2,"prerequisites":[[1,0]]}
Output: true

**Approach: Topological Sort / Cycle Detection**

**Key Insight:** Courses can be completed if and only if there are no circular dependencies. This is equivalent to checking if the prerequisite graph is a DAG (Directed Acyclic Graph).

**Method 1: DFS with Three States**

-   WHITE (0): unvisited
    
-   GRAY (1): currently in DFS stack (being processed)
    
-   BLACK (2): completely processed
    

If we encounter a GRAY node during DFS, we found a cycle.

**Method 2: Kahn's Algorithm (BFS)**

1.  Build adjacency list and compute in-degrees
    
2.  Add all nodes with in-degree 0 to queue
    
3.  Process queue: for each node, reduce in-degrees of neighbors
    
4.  If nodes with in-degree 0 appear, add to queue
    
5.  If all nodes processed, no cycle exists
    

**DFS Implementation:**

`def canFinish(numCourses, prerequisites): graph = defaultdict(list) for course, prereq in prerequisites: graph[prereq].append(course) state = [0] * numCourses # 0=white, 1=gray, 2=black def hasCycle(node): if state[node] == 1: return True # Back edge = cycle if state[node] == 2: return False # Already processed state[node] = 1 for neighbor in graph[node]: if hasCycle(neighbor): return True state[node] = 2 return False return not any(hasCycle(i) for i in range(numCourses))`

###### **There was an issue generating the diagram for the code above.**

**Time: O(V + E)**, **Space: O(V + E)**

**Time: O(V + E)**

**Space: O(V + E)**

```
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<Integer>[] adj = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) adj[i] = new ArrayList<>();
        for (int[] pre : prerequisites) adj[pre[0]].add(pre[1]);
        
        int[] visit = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
        
        for (int i = 0; i < numCourses; i++) {
            if (dfs(i, adj, visit)) return false;
        }
        return true;
    }
    
    private boolean dfs(int curr, ArrayList<Integer>[] adj, int[] visit) {
        if (visit[curr] == 1) return true; // Cycle
        if (visit[curr] == 2) return false;
        
        visit[curr] = 1;
        for (int next : adj[curr]) {
            if (dfs(next, adj, visit)) return true;
        }
        visit[curr] = 2;
        return false;
    }
}
```
