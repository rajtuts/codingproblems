# Longest Common Subsequence
Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.

Example 1:
Input: {"text1":"abcde","text2":"ace"}
Output: 3

**Approach: Dynamic Programming**

**Key Insight:** dp\[i\]\[j\] = length of LCS of text1\[0..i-1\] and text2\[0..j-1\]. If characters match, extend previous LCS. Otherwise, take the max of excluding either character.

**Algorithm:**

1.  Create (m+1)×(n+1) DP table initialized to 0
    
2.  For each i from 1 to m, j from 1 to n:
    
    -   If text1\[i-1\] == text2\[j-1\]: dp\[i\]\[j\] = 1 + dp\[i-1\]\[j-1\]
        
    -   Else: dp\[i\]\[j\] = max(dp\[i-1\]\[j\], dp\[i\]\[j-1\])
        
3.  Return dp\[m\]\[n\]
    

**Example:** text1 = "abcde", text2 = "ace"

`"" a c e "" 0 0 0 0 a 0 1 1 1 b 0 1 1 1 c 0 1 2 2 d 0 1 2 2 e 0 1 2 3`

LCS = "ace", length = 3 ✓

**Recurrence Logic:**

-   Match: include both characters in LCS
    
-   No match: best of (skip text1 char) or (skip text2 char)
    

**Time Complexity:** O(m×n) - fill entire table **Space Complexity:** O(m×n) - or O(min(m,n)) with 1D array

**Time: O(m\*n)**

**Space: O(m\*n)**

```
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        
        for (int i = text1.length() - 1; i >= 0; i--) {
            for (int j = text2.length() - 1; j >= 0; j--) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return dp[0][0];
    }
}
```
