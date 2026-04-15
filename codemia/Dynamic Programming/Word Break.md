# Word Break
Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.

Example 1:
Input: {"s":"leetcode","wordDict":["leet","code"]}
Output: true

**Approach: Dynamic Programming**

**Key Insight:** dp\[i\] = true if s\[0:i\] can be segmented into dictionary words. For each position, check all possible word endings by looking back at valid starting points.

**Algorithm:**

1.  Convert wordDict to set for O(1) lookup
    
2.  Initialize dp\[0\] = True (empty string can be segmented)
    
3.  For i from 1 to n:
    
    -   For j from 0 to i-1:
        
        -   If dp\[j\] is True AND s\[j:i\] is in wordDict:
            
            -   dp\[i\] = True, break
                
4.  Return dp\[n\]
    

**Example:** s = "leetcode", wordDict = \["leet", "code"\]

-   dp\[0\] = True (base case)
    
-   dp\[1-3\] = False (no words)
    
-   dp\[4\] = True (dp\[0\] True, "leet" in dict)
    
-   dp\[5-7\] = False
    
-   dp\[8\] = True (dp\[4\] True, "code" in dict) ✓
    

**Optimization:**

-   Can limit inner loop to max word length
    
-   Use Trie for prefix matching
    

**Time Complexity:** O(n²) - for each position, check all prior positions **Space Complexity:** O(n) - dp array

**Time: O(n²)**

**Space: O(n)**

```
class Solution {
    public boolean wordBreak(String s, String[] wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[s.length()] = true;
        
        for (int i = s.length() - 1; i >= 0; i--) {
            for (String w : wordDict) {
                if (i + w.length() <= s.length() && s.substring(i, i + w.length()).equals(w)) {
                    dp[i] = dp[i + w.length()];
                }
                if (dp[i]) break;
            }
        }
        return dp[0];
    }
}
```
