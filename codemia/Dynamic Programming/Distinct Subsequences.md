# Distinct Subsequences
Given two strings s and t, return the number of distinct subsequences of s which equals t. A subsequence of a string is a new string formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters.

Example 1:
Input: {"s":"rabbbit","t":"rabbit"}
Output: 3

DP where dp[i][j] = ways to form t[0..j-1] from s[0..i-1]. Either skip s[i-1] or use it if it matches.

Time: O(m*n)
Space: O(m*n)


```
class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];

        // Empty t is a subsequence of any prefix of s
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j]; // Skip s[i-1]
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1]; // Use s[i-1]
                }
            }
        }

        return dp[m][n];
    }
}
```
