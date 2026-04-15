# Palindromic Substrings
Given a string s, return the number of palindromic substrings in it. A string is a palindrome when it reads the same backward as forward.

Example 1:
Input: {"s":"abc"}
Output: 3

**Approach: Expand Around Center**

**Key Insight:** Every palindrome has a center. For odd-length palindromes, the center is one character. For even-length, the center is between two characters.

**Algorithm:**

1.  For each position i, try two centers:
    
    -   Odd-length: center at (i, i)
        
    -   Even-length: center at (i, i+1)
        
2.  Expand while s\[left\] == s\[right\]
    
3.  Count each valid expansion as a palindrome
    

**Example:** s = "aaa"

-   Center at 0: "a" (1 palindrome)
    
-   Center at (0,1): "aa" (1 palindrome)
    
-   Center at 1: "a", "aaa" (2 palindromes)
    
-   Center at (1,2): "aa" (1 palindrome)
    
-   Center at 2: "a" (1 palindrome)
    
-   Total: 6
    

**Time Complexity:** O(n²) - n centers, O(n) expansion each **Space Complexity:** O(1) - only pointers

**Time: O(n²)**

**Space: O(1)**

```

class Solution {
    public int countSubstrings(String s) {
        int res = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // odd
            int l = i, r = i;
            while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                res++;
                l--;
                r++;
            }
            
            // even
            l = i;
            r = i + 1;
            while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                res++;
                l--;
                r++;
            }
        }
        return res;
    }
}
```
