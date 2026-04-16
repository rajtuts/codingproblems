# Wildcard Matching
Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '' where: '?' Matches any single character. '' Matches any sequence of characters (including the empty sequence).

Example 1:
Input: {"s":"aa","p":"*"}
Output: true

**Approach: 2D Dynamic Programming**

**Key Insight:** `dp[i][j]` = True if s\[0:i\] matches p\[0:j\]. Handle '\*' specially since it can match zero or more characters.

**DP Transitions:**

1.  **Exact match or '?':** `dp[i][j] = dp[i-1][j-1]`
    
    -   Both consume one character
        
2.  **'\*' pattern:** `dp[i][j] = dp[i][j-1] OR dp[i-1][j]`
    
    -   `dp[i][j-1]`: '\*' matches empty string
        
    -   `dp[i-1][j]`: '\*' matches one more character from s
        

**Base Cases:**

-   `dp[0][0] = True` (empty matches empty)
    
-   `dp[0][j] = True` only if p\[0:j\] is all '\*'s
    

**Example:** s = "adceb", p = "_a_b"

`"" * a * b "" T T F F F a F T T T F d F T F T F c F T F T F e F T F T F b F T F T T ✓`

**Why '\*' Transition Works:**

-   `dp[i][j-1]`: '\*' matches nothing (skip it)
    
-   `dp[i-1][j]`: '\*' already matched previous chars, extend to match s\[i-1\]
    

**Visual:**

`s = "abc", p = "*" '*' can match: "" → "a" → "ab" → "abc" Each step: dp[i][j] = dp[i-1][j] (extend match)`

**Time Complexity:** O(m × n) - fill m×n table **Space Complexity:** O(m × n), can optimize to O(n)

**Time: O(m\*n)**

**Space: O(m\*n)**

```
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // Handle leading *
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }
}
```
