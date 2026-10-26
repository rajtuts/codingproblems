LeetCode 62 — Unique Paths

Medium
Topic: Dynamic Programming, Combinatorics

Problem: A robot starts at the top-left corner of an m × n grid and wants to reach the bottom-right corner.

It can move only right or down. Find the number of unique paths.


1. Understand the problem
Example:
m = 3, n = 3
Output = 6


<img width="648" height="661" alt="image" src="https://github.com/user-attachments/assets/9c91448c-103d-4bcd-99cf-c277dac274f5" />

2. The key DP idea
To reach any cell (i, j), the robot must arrive from one of two cells:
- From above: (i - 1, j) by moving down.
- From the left: (i, j - 1) by moving right.
Therefore, the number of ways to reach a cell is the sum of the ways to reach those two cells.
\[
\boxed{dp[i][j]=dp[i-1][j]+dp[i][j-1]}
\]

Base cases:
- The first row contains only 1s because there is only one way to move right along it.
- The first column contains only 1s because there is only one way to move down along it.
3. Build the DP table
For m = 3 and n = 3, initialize the first row and column to 1, then fill each remaining cell using the recurrence.
Number of ways to reach each cell
Row / Col	0	1	2
0	1	1	1
1	1	2	3
2	1	3	6

Each cell combines the value directly above and the value directly to its left.

Let's calculate the cells:
1. dp[1][1] = 1 + 1 = 2
2. dp[1][2] = 1 + 2 = 3
3. dp[2][1] = 2 + 1 = 3
4. dp[2][2] = 3 + 3 = 6
The answer is the bottom-right cell: dp[m - 1][n - 1] = 6.
4. Java solution — 2D Dynamic Programming

class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // First row: only one way to move right
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        // First column: only one way to move down
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        // Fill the remaining cells
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j]
                         + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }
}



Complexity
- Time: \(O(mn)\), because every cell is calculated once.
- Space: \(O(mn)\), for the DP table.
5. Optimized Java solution — 1D DP
We don't need the entire grid in memory. We can keep only one row of results.

class Solution {
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];

        // First row has one way to reach each cell
        for (int j = 0; j < n; j++) {
            dp[j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }

        return dp[n - 1];
    }
}



Why does this work?
- dp[j] before updating represents the cell above.
- dp[j - 1] represents the cell to the left, already updated for the current row.
- Adding them gives the new number of paths to the current cell.
Optimized complexity: Time \(O(mn)\), extra space \(O(n)\).
6. Test your understanding
Question: What is the number of unique paths for a 3 × 2 grid?

2

3

4

6


Interview takeaway: For grid path problems, ask where you could have come from. If each cell can be reached from above or from the left, the recurrence is dp[i][j] = dp[i-1][j] + dp[i][j-1].
