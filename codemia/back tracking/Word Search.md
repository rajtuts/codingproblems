# Word Search
Given an m x n grid of characters board and a string word, return true if word exists in the grid. The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

## Example 1:  
Input: {"board":[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]],"word":"ABCCED"}  
Output: true  

**Approach: DFS with Backtracking**

**Key Insight:** For each cell matching word\[0\], try to find the remaining word by exploring 4 directions. Mark cells as visited during exploration, restore when backtracking.

**Algorithm:**  
1.  Define dfs(i, j, k): can we find word\[k:\] starting at (i,j)?      
    -   If k == len(word): found complete word, return True          
    -   If out of bounds or board\[i\]\[j\] != word\[k\]: return False          
    -   Mark cell as visited (board\[i\]\[j\] = '#')          
    -   Recurse in 4 directions with k+1          
    -   Restore cell (backtrack)          
    -   Return if any direction found the word          
2.  Try starting DFS from each cell      

**Example:** word = "ABCCED"  
`A B C E S F C S A D E E`  
-   Start at A(0,0) → B(0,1) → C(0,2) → C(1,2) → E(2,2) → D(2,1) ✓      

**Why Restore Cells:**  
-   Same cell can't be used twice in same path      
-   But can be used in different DFS attempts      
-   Must restore for backtracking to work      

**Time Complexity:** O(m×n×4^L) - each cell, each direction, word length L **Space Complexity:** O(L) - recursion depth  
**Time: O(m\*n\*4^L)**  
**Space: O(L)**  

```
class Solution {
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, i, j, 0)) return true;
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int i, int j, int k) {
        if (k == word.length()) return true;
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(k)) return false;
        
        char tmp = board[i][j];
        board[i][j] = '/';
        boolean res = dfs(board, word, i + 1, j, k + 1) ||
                      dfs(board, word, i - 1, j, k + 1) ||
                      dfs(board, word, i, j + 1, k + 1) ||
                      dfs(board, word, i, j - 1, k + 1);
        board[i][j] = tmp;
        return res;
    }
}
```
