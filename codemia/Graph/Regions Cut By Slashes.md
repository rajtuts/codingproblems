# Regions Cut By Slashes
An n x n grid is composed of 1 x 1 squares where each 1 x 1 square consists of a '/', '', or blank space ' '. These characters divide the square into contiguous regions. Given the grid grid represented as a string array, return the number of regions.

Example 1:
Input: [" /","/ "]
Output: 2

**Approach: Grid Expansion + DFS**

**Key Insight:** Expand each cell to 3x3 to properly represent diagonal slashes as walls.

**Algorithm:**

1.  Create 3n x 3n expanded grid
    
2.  For '/', mark anti-diagonal cells as 1
    
3.  For '', mark diagonal cells as 1
    
4.  Count connected components of 0s using DFS
    

**Time Complexity:** O(n²) **Space Complexity:** O(n²)

**Time: O(n²)**

**Space: O(n²)**

```
class Solution {
    int[][] expanded;
    int size;

    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        size = 3 * n;
        expanded = new int[size][size];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '/') {
                    expanded[3*i][3*j+2] = 1;
                    expanded[3*i+1][3*j+1] = 1;
                    expanded[3*i+2][3*j] = 1;
                } else if (grid[i].charAt(j) == '\\') {
                    expanded[3*i][3*j] = 1;
                    expanded[3*i+1][3*j+1] = 1;
                    expanded[3*i+2][3*j+2] = 1;
                }
            }
        }

        int regions = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (expanded[i][j] == 0) {
                    dfs(i, j);
                    regions++;
                }
            }
        }

        return regions;
    }

    void dfs(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size || expanded[i][j] != 0) return;
        expanded[i][j] = 2;
        dfs(i+1, j); dfs(i-1, j); dfs(i, j+1); dfs(i, j-1);
    }
}
```
