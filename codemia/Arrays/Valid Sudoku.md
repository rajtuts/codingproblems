# Valid Sudoku
Determine if a 9x9 Sudoku board is valid.

## Example 1:
Input: [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
Output: true

**Approach: Hash Sets for Rows, Columns, Boxes**

**Key Insight:** A valid Sudoku has no duplicates in any row, column, or 3×3 box. Use three sets of 9 hash sets each to track seen numbers.

**Algorithm:**
1.  Create 9 sets for rows, 9 for columns, 9 for boxes    
2.  For each cell (i, j):    
    -   Skip if '.' (empty)        
    -   Calculate box index: (i // 3) \* 3 + (j // 3)        
    -   If num in rows\[i\] or cols\[j\] or boxes\[box\]: return False        
    -   Add num to all three sets        
3.  Return True
**Box Index Formula:**
`0 1 2 Box indices: 3 4 5 (i//3)*3 + (j//3) 6 7 8`
-   (0,0)-(2,2) → box 0
-   (0,3)-(2,5) → box 1
-   (3,0)-(5,2) → box 3, etc.    
**Example:** Checking cell (4,7) = "3"
-   row 4 set: add "3"
-   col 7 set: add "3"    
-   box (4//3)_3 + (7//3) = 1_3 + 2 = 5: add "3"
-   If any already contains "3": invalid
**Time Complexity:** O(81) = O(1) - fixed 9×9 board **Space Complexity:** O(81) = O(1) - 27 sets of at most 9 elements

```
class Solution {
    public boolean isValidSudoku(char[][] board) {
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];
        
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val == '.') continue;

                if (rows[r].contains(val)) return false;
                if (cols[c].contains(val)) return false;
                
                int boxIdx = (r / 3) * 3 + (c / 3);
                if (boxes[boxIdx].contains(val)) return false;

                rows[r].add(val);
                cols[c].add(val);
                boxes[boxIdx].add(val);
            }
        }
        return true;
    }
}
```

**Time: O(81)**

**Space: O(81)**
