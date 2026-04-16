# Edit Distance
Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2. Operations: insert, delete, or replace a character.

Example 1:
Input: {"word1":"horse","word2":"ros"}
Output: 3

**Approach: Dynamic Programming**

**Key Insight:** dp\[i\]\[j\] = minimum operations to convert word1\[0:i\] to word2\[0:j\]. At each position, we can insert, delete, or replace.

**Algorithm:**

1.  Initialize dp\[i\]\[0\] = i (delete all), dp\[0\]\[j\] = j (insert all)
    
2.  For each cell dp\[i\]\[j\]:
    
    -   If word1\[i-1\] == word2\[j-1\]: dp\[i\]\[j\] = dp\[i-1\]\[j-1\] (no operation)
        
    -   Else: dp\[i\]\[j\] = 1 + min(dp\[i-1\]\[j\], dp\[i\]\[j-1\], dp\[i-1\]\[j-1\])
        
        -   dp\[i-1\]\[j\] + 1: delete from word1
            
        -   dp\[i\]\[j-1\] + 1: insert into word1
            
        -   dp\[i-1\]\[j-1\] + 1: replace
            
3.  Return dp\[m\]\[n\]
    

**Example:** word1 = "horse", word2 = "ros"

`"" r o s "" 0 1 2 3 h 1 1 2 3 o 2 2 1 2 r 3 2 2 2 s 4 3 3 2 e 5 4 4 3`

Answer: 3 (replace h→r, delete r, delete e)

**Time Complexity:** O(m × n) - fill entire table **Space Complexity:** O(m × n) - or O(n) with 1D array

**Time: O(m\*n)**

**Space: O(m\*n)**

```
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]));
                }
            }
        }
        return dp[m][n];
    }
}
