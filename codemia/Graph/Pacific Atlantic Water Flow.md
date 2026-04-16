Pacific Atlantic Water Flow
Return a list of grid coordinates where water can flow to both the Pacific and Atlantic oceans.

Example 1:
Input: [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]

**Approach: Multi-source DFS/BFS**

**Key Insight:** Instead of checking each cell, start from ocean borders and find cells reachable from each ocean. The answer is the intersection.

**Algorithm:**

1.  DFS/BFS from Pacific border (top row + left column)
    
    -   Go uphill (neighbor height >= current)
        
2.  DFS/BFS from Atlantic border (bottom row + right column)
    
3.  Return cells in both reachable sets
    

**Example:** heights = \[\[1,2,2,3,5\],\[3,2,3,4,4\],...\]

-   From Pacific: cells that can flow to top/left
    
-   From Atlantic: cells that can flow to bottom/right
    
-   Intersection: cells like \[0,4\], \[2,2\], etc.
    

**Time Complexity:** O(m×n) - visit each cell at most twice **Space Complexity:** O(m×n) - visited sets

**Time: O(m\*n)**

**Space: O(m\*n)**

```
class Solution {
    int[][] heights;
    int ROWS, COLS;
    
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        this.heights = heights;
        ROWS = heights.length;
        COLS = heights[0].length;
        
        Set<String> pac = new HashSet<>();
        Set<String> atl = new HashSet<>();
        
        for (int c = 0; c < COLS; c++) {
            dfs(0, c, pac, heights[0][c]);
            dfs(ROWS - 1, c, atl, heights[ROWS - 1][c]);
        }
        for (int r = 0; r < ROWS; r++) {
            dfs(r, 0, pac, heights[r][0]);
            dfs(r, COLS - 1, atl, heights[r][COLS - 1]);
        }
        
        List<List<Integer>> res = new ArrayList<>();
        for (String key : pac) {
            if (atl.contains(key)) {
                String[] parts = key.split(",");
                res.add(Arrays.asList(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        }
        return res;
    }
    
    void dfs(int r, int c, Set<String> visit, int prevHeight) {
        String key = r + "," + c;
        if (r < 0 || c < 0 || r >= ROWS || c >= COLS || visit.contains(key) || heights[r][c] < prevHeight) return;
        visit.add(key);
        dfs(r + 1, c, visit, heights[r][c]);
        dfs(r - 1, c, visit, heights[r][c]);
        dfs(r, c + 1, visit, heights[r][c]);
        dfs(r, c - 1, visit, heights[r][c]);
    }
}
```
