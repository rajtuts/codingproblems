# Course Schedule II
There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai. Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.

Example 1:
Input: {"numCourses":4,"prerequisites":[[1,0],[2,0],[3,1],[3,2]]}
Output: [0,1,2,3]

**Approach: Kahn's Algorithm (BFS Topological Sort)**

**Key Insight:** Build a graph from prerequisites, then use BFS to process courses with no remaining prerequisites. If we can process all courses, ordering is possible.

**Algorithm:**

1.  Build adjacency list and in-degree array
    
2.  Add all courses with in-degree 0 to queue
    
3.  BFS: process course, reduce in-degrees of neighbors
    
4.  When neighbor's in-degree becomes 0, add to queue
    
5.  If processed count == numCourses: return True
    

**Example:** numCourses=4, prerequisites=\[\[1,0\],\[2,0\],\[3,1\],\[3,2\]\]

-   Graph: 0→1, 0→2, 1→3, 2→3
    
-   In-degrees: \[0,1,1,2\]
    
-   Queue: \[0\], process 0 → in-degrees=\[0,0,0,2\], queue=\[1,2\]
    
-   Process 1 → in-degrees=\[0,0,0,1\], queue=\[2\]
    
-   Process 2 → in-degrees=\[0,0,0,0\], queue=\[3\]
    
-   Process 3 → done, count=4
    
-   Result: True ✓
    

**Time Complexity:** O(V + E) - visit each course and prerequisite **Space Complexity:** O(V + E) - graph storage

**Time: O(V+E)**

**Space: O(V+E)**

```
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());

        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);
            indegree[pre[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }

        int[] result = new int[numCourses];
        int idx = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result[idx++] = course;
            for (int next : graph.get(course)) {
                indegree[next]--;
                if (indegree[next] == 0) queue.offer(next);
            }
        }
        return idx == numCourses ? result : new int[0];
    }
}
```
