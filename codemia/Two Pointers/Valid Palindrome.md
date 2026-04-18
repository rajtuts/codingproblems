# Valid Palindrome
A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward.

Example 1:
Input: "A man, a plan, a canal: Panama"
Output: true

**Approach: Two Pointers**

**Key Insight:** A palindrome reads the same forwards and backwards. Use two pointers from both ends moving inward.

**Algorithm:**

1.  Initialize left = 0, right = len(s) - 1
    
2.  While left < right:
    
    -   Skip non-alphanumeric characters from left
        
    -   Skip non-alphanumeric characters from right
        
    -   Compare characters (case-insensitive)
        
    -   If mismatch, return False
        
    -   Move pointers inward
        
3.  If all characters matched, return True
    

**Why Two Pointers:** O(n) time, O(1) space - no need to create a cleaned copy of the string.

**Alternative Approach:** Clean string first, then compare with reverse

`cleaned = ''.join(c.lower() for c in s if c.isalnum()) return cleaned == cleaned[::-1]`

This is simpler but uses O(n) extra space.

**Edge Cases:**

-   Empty string or only special characters → valid palindrome
    
-   Single character → valid palindrome
    

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public boolean isPalindrome(String s) {
        int l = 0;
        int r = s.length() - 1;

        while (l < r) {
            while (l < r && !Character.isLetterOrDigit(s.charAt(l))) {
                l++;
            }
            while (l < r && !Character.isLetterOrDigit(s.charAt(r))) {
                r--;
            }

            if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
```
