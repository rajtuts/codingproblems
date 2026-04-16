Number of Ways to Cut a Pizza
Given a rectangular pizza represented as a rows x cols matrix containing 'A' (apple) and '.', cut the pizza into k pieces using k-1 cuts. For each cut, you choose the direction: horizontal or vertical, then cut the pizza. The piece given away is either the upper part if horizontal or the left part if vertical. Return the number of ways to cut such that each piece contains at least one apple.

Example 1:
Input: {"pizza":["A..","AAA","..."],"k":3}
Output: 3

Use 2D prefix sum to count apples in any subgrid in O(1). DP: dp(r,c,cuts) = ways to partition remaining pizza from (r,c) with 'cuts' cuts left. Try all horizontal and vertical cuts that leave at least one apple in the given piece.

Time: O(k * m * n * (m + n))
Space: O(k * m * n)

```
class Solution {
    private int MOD = 1_000_000_007;
    private int[][] apples;
    private int[][][] memo;
    private int m, n;

    public int ways(String[] pizza, int k) {
        m = pizza.length;
        n = pizza[0].length();
        apples = new int[m + 1][n + 1];
        memo = new int[m][n][k];
        for (int[][] arr2d : memo) for (int[] arr : arr2d) Arrays.fill(arr, -1);

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                apples[i][j] = (pizza[i].charAt(j) == 'A' ? 1 : 0) + apples[i + 1][j] + apples[i][j + 1] - apples[i + 1][j + 1];
            }
        }

        return dp(0, 0, k - 1);
    }

    private int dp(int i, int j, int cuts) {
        if (apples[i][j] == 0) return 0;
        if (cuts == 0) return 1;
        if (memo[i][j][cuts] != -1) return memo[i][j][cuts];

        int result = 0;
        for (int ni = i + 1; ni < m; ni++) {
            if (apples[i][j] - apples[ni][j] > 0) {
                result = (result + dp(ni, j, cuts - 1)) % MOD;
            }
        }
        for (int nj = j + 1; nj < n; nj++) {
            if (apples[i][j] - apples[i][nj] > 0) {
                result = (result + dp(i, nj, cuts - 1)) % MOD;
            }
        }
        return memo[i][j][cuts] = result;
    }
}
```
