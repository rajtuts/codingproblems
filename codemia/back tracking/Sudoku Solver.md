# Sudoku Solver
Write a program to solve a Sudoku puzzle by filling the empty cells. A sudoku solution must satisfy all of the following rules: Each of the digits 1-9 must occur exactly once in each row, column, and 3x3 sub-box.

## Example 1:  
Input: [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]  
Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]  

**Approach: Backtracking with Constraint Checking**  

**Key Insight:** Try each digit 1-9 in empty cells, validating against Sudoku rules. If a placement leads to an unsolvable state, backtrack and try the next digit.  

**Three Constraints to Check:**  
1.  **Row:** No duplicate in the same row      
2.  **Column:** No duplicate in the same column      
3.  **3×3 Box:** No duplicate in the 3×3 sub-grid      

**Box Index Formula:** For cell (row, col), box starts at:  
-   boxRow = 3 × (row // 3)      
-   boxCol = 3 × (col // 3)      

**Algorithm:**  
1.  Find first empty cell ('.')      
2.  Try digits '1' to '9':      
    -   If valid placement → place digit, recurse          
    -   If recursion succeeds → done!          
    -   If fails → backtrack (reset to '.'), try next digit          
3.  If no valid digit → return False (trigger backtrack)      
4.  If no empty cells → solved!      

**Backtracking Tree:**  
`Empty cell (0,2): ├─ Try '1': Invalid (already in box) ├─ Try '2': Invalid (already in col) ├─ Try '3': Invalid (already in row) ├─ Try '4': Valid! → Recurse to next empty cell │ ├─ ... (eventually fails) │ └─ Backtrack: reset to '.' ├─ Try '5': Valid! → Recurse... │ └─ ... (succeeds!) └─ Done!`  
**Time Complexity:** O(9^81) worst case, but pruning makes it much faster **Space Complexity:** O(81) for recursion stack
**Time: O(9^(n\*n)) where n=9**  
**Space: O(n\*n) for recursion stack**  

```
class Solution {
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char num = '1'; num <= '9'; num++) {
                        if (isValid(board, i, j, num)) {
                            board[i][j] = num;
                            if (solve(board)) return true;
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) return false;
            if (board[i][col] == num) return false;
            int boxRow = 3 * (row / 3) + i / 3;
            int boxCol = 3 * (col / 3) + i % 3;
            if (board[boxRow][boxCol] == num) return false;
        }
        return true;
    }
}
```
