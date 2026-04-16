# Palindrome Partitioning II
Given a string s, partition s such that every substring of the partition is a palindrome. Return the minimum cuts needed for a palindrome partitioning of s.

Example 1:
Input: {"s":"aab"}
Output: 1

DP with isPalin[i][j] for palindrome checking. dp[i] = min cuts for s[0..i]. Update when s[j..i] is palindrome.

Time: O(n^2)
Space: O(n^2)

```
class Solution {
    public int minCut(String s) {
        int n = s.length();
        int[] dp = new int[n];
        boolean[][] isPalin = new boolean[n][n];

        for (int i = 0; i < n; i++) dp[i] = i;

        for (int end = 0; end < n; end++) {
            for (int start = 0; start <= end; start++) {
                if (s.charAt(start) == s.charAt(end) &&
                    (end - start <= 2 || isPalin[start + 1][end - 1])) {
                    isPalin[start][end] = true;
                    if (start == 0) {
                        dp[end] = 0;
                    } else {
                        dp[end] = Math.min(dp[end], dp[start - 1] + 1);
                    }
                }
            }
        }

        return dp[n - 1];
    }
}
```
