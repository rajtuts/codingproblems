# Surrounded Regions
Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'. A region is captured by flipping all 'O's into 'X's in that surrounded region.

Example 1:
Input: [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]

Mark border-connected O's as safe (T), flip remaining O's to X, restore T's to O.

Time: O(m*n)
Space: O(m*n)

```
class Solution {
    public void solve(char[][] board) {
        if (board.length == 0) return;
        int m = board.length, n = board[0].length;

        for (int r = 0; r < m; r++) { dfs(board, r, 0); dfs(board, r, n - 1); }
        for (int c = 0; c < n; c++) { dfs(board, 0, c); dfs(board, m - 1, c); }

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 'O') board[r][c] = 'X';
                else if (board[r][c] == 'T') board[r][c] = 'O';
            }
        }
    }

    private void dfs(char[][] board, int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != 'O') return;
        board[r][c] = 'T';
        dfs(board, r + 1, c);
        dfs(board, r - 1, c);
        dfs(board, r, c + 1);
        dfs(board, r, c - 1);
    }
}
```
