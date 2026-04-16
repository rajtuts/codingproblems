# Longest Palindromic Substring
Given a string s, return the longest palindromic substring in s.

Example 1:
Input: "babad"
Output: "bab"

**Approach: Expand Around Center**

**Key Insight:** Every palindrome has a center (single char for odd length, between two chars for even length). Expand outward from each possible center to find all palindromes.

**Algorithm:**

1.  For each index i (and pair i, i+1):
    
    -   Expand outward while characters match
        
    -   Track the longest palindrome found
        
2.  Return the longest substring
    

**Example:** s = "babad"

-   Center at 'a' (index 1): expand to "bab" ← longest so far
    
-   Center at 'b' (index 2): expand to "aba" ← same length
    
-   Result: "bab" or "aba" (both valid)
    

**Two Cases:**

-   Odd length: center at s\[i\], expand (i-1, i+1)
    
-   Even length: center between s\[i\] and s\[i+1\], expand (i, i+1)
    

**Time Complexity:** O(n²) - n centers, O(n) expansion each **Space Complexity:** O(1) - only tracking indices

**Time: O(n²)**

**Space: O(1)**

```
class Solution {
    private int start = 0, maxLen = 1;

    public String longestPalindrome(String s) {
        if (s.length() < 2) return s;

        for (int i = 0; i < s.length(); i++) {
            expand(s, i, i);
            expand(s, i, i + 1);
        }

        return s.substring(start, start + maxLen);
    }

    private void expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            if (right - left + 1 > maxLen) {
                start = left;
                maxLen = right - left + 1;
            }
            left--;
            right++;
        }
    }
}
```
