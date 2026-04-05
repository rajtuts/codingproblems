# N-Queens
Place n queens on an n x n chessboard such that no two queens attack each other. Return all distinct solutions.  

## Example 1:  
Input: 4  
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]  

Use backtracking placing queens row by row. Track attacked columns and diagonals using sets.

**Time: O(n!)
Space: O(n²)**

```
class Solution {
    public java.util.List<java.util.List<String>> solveNQueens(int n) {
        java.util.List<java.util.List<String>> result = new java.util.ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) java.util.Arrays.fill(board[i], '.');
        backtrack(board, 0, result);
        return result;
    }

    private void backtrack(char[][] board, int row, java.util.List<java.util.List<String>> result) {
        if (row == board.length) {
            java.util.List<String> solution = new java.util.ArrayList<>();
            for (char[] r : board) solution.add(new String(r));
            result.add(solution);
            return;
        }
        for (int col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';
                backtrack(board, row + 1, result);
                board[row][col] = '.';
            }
        }
    }

    private boolean isSafe(char[][] board, int row, int col) {
        for (int i = 0; i < row; i++) if (board[i][col] == 'Q') return false;
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 'Q') return false;
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++)
            if (board[i][j] == 'Q') return false;
        return true;
    }
}
```
