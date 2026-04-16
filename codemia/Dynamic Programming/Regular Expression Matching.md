# Regular Expression Matching
Given an input string s and a pattern p, implement regular expression matching with support for '.' and '' where: '.' Matches any single character. '' Matches zero or more of the preceding element. The matching should cover the entire input string (not partial).

Example 1:
Input: {"s":"aa","p":"a*"}
Output: true

**Approach: Dynamic Programming**

**Key Insight:** dp\[i\]\[j\] = True if s\[0:i\] matches p\[0:j\]. Handle '\*' by considering zero matches or one+ matches.

**Algorithm:**

1.  Initialize dp\[0\]\[0\] = True
    
2.  Handle patterns like "a_b_" matching empty string
    
3.  For each (i, j):
    
    -   If p\[j-1\] == '\*':
        
        -   Zero matches: dp\[i\]\[j-2\] (skip "c\*")
            
        -   One+ matches: dp\[i-1\]\[j\] if s\[i-1\] matches p\[j-2\]
            
    -   Else if p\[j-1\] == '.' or p\[j-1\] == s\[i-1\]:
        
        -   dp\[i\]\[j\] = dp\[i-1\]\[j-1\]
            

**Example:** s = "aab", p = "c_a_b"

-   c\* matches empty ✓
    
-   a\* matches "aa" ✓
    
-   b matches "b" ✓
    
-   Result: True
    

**Key cases for '\*':**

-   Zero occurrences: look at dp\[i\]\[j-2\]
    
-   One+ occurrences: if current char matches, check dp\[i-1\]\[j\]
    

**Time Complexity:** O(mn) - fill m×n table **Space Complexity:** O(mn) - dp table

**Time: O(mn)**

**Space: O(mn)**

```
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') dp[0][j] = dp[0][j - 2];
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2] || (
                        (p.charAt(j - 2) == '.' || p.charAt(j - 2) == s.charAt(i - 1)) && dp[i - 1][j]
                    );
                } else if (p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }
}
```
