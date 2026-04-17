# Number of Provinces
There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c. A province is a group of directly or indirectly connected cities and no other cities outside of the group. Return the total number of provinces.

Example 1:
Input: [[1,1,0],[1,1,0],[0,0,1]]
Output: 2

**Approach: DFS for Connected Components**

**Key Insight:** This is a classic connected components problem. Each province is a connected component in the graph. Count how many times you need to start a new DFS traversal.

**Algorithm:**

1.  Maintain visited array for all cities
    
2.  For each unvisited city:
    
    -   Start DFS to mark all connected cities as visited
        
    -   Increment province count
        
3.  Return total count
    

**Example:** isConnected = \[\[1,1,0\],\[1,1,0\],\[0,0,1\]\]

`City 0 ─── City 1 City 2 (isolated) Start DFS from city 0: - Visit 0, mark visited - Check neighbors: city 1 connected, visit it - City 2 not connected to 0 Province count = 1 City 1: already visited, skip Start DFS from city 2: - Visit 2, mark visited - No unvisited neighbors Province count = 2 Result: 2 provinces ✓`

**Understanding the Matrix:**

-   isConnected\[i\]\[j\] = 1 means cities i and j are directly connected
    
-   Matrix is symmetric (undirected graph)
    
-   Diagonal is always 1 (city connected to itself)
    

**Alternative: Union-Find**

`def find(parent, i): if parent[i] != i: parent[i] = find(parent, parent[i]) return parent[i] # Union all connected cities, count distinct roots`

**Time Complexity:** O(n²) - check all edges in matrix **Space Complexity:** O(n) - visited array and recursion stack

**Time: O(n^2)**

**Space: O(n)**


```
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int city = 0; city < n; city++) {
            if (!visited[city]) {
                dfs(isConnected, visited, city);
                provinces++;
            }
        }

        return provinces;
    }

    private void dfs(int[][] isConnected, boolean[] visited, int city) {
        visited[city] = true;
        for (int neighbor = 0; neighbor < isConnected.length; neighbor++) {
            if (isConnected[city][neighbor] == 1 && !visited[neighbor]) {
                dfs(isConnected, visited, neighbor);
            }
        }
    }
}
```
