# Strange Printer
A strange printer can only print a sequence of the same character each turn. At each turn, the printer can print new characters starting from and ending at any place and will cover the original existing characters. Given a string s, return the minimum number of turns the printer needs to print it.

Example 1:
Input: {"s":"aaabbb"}
Output: 2

Interval DP. Remove consecutive duplicates. dp[i][j] = min turns for substring. If s[k] == s[j], we can extend the print from position k to cover j, saving one turn.

Time: O(n^3)
Space: O(n^2)

```
class Solution {
    public int strangePrinter(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 || s.charAt(i) != s.charAt(i-1)) {
                sb.append(s.charAt(i));
            }
        }
        s = sb.toString();
        int n = s.length();
        if (n == 0) return 0;

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = dp[i][j-1] + 1;
                for (int k = i; k < j; k++) {
                    if (s.charAt(k) == s.charAt(j)) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + (k+1 <= j-1 ? dp[k+1][j-1] : 0));
                    }
                }
            }
        }

        return dp[0][n-1];
    }
}
```
