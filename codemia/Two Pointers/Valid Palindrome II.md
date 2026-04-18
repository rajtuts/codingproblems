# Valid Palindrome II
Given a string s, return true if the s can be palindrome after deleting at most one character from it.

Example 1:
Input: "abca"
Output: true

**Approach: Two Pointers with One Deletion**

**Key Insight:** Use two pointers from both ends. When mismatch found, try deleting either character and check if the remaining substring is a palindrome.

**Algorithm:**

1.  Initialize left=0, right=len(s)-1
    
2.  While left < right:
    
    -   If s\[left\] == s\[right\]: move both pointers inward
        
    -   If mismatch: try two options
        
        -   Delete left char: check if s\[left+1:right+1\] is palindrome
            
        -   Delete right char: check if s\[left:right\] is palindrome
            
        -   Return True if either works
            
3.  Return True (already a palindrome)
    

**Example:** s = "abca"

-   left=0 ('a'), right=3 ('a'): match, move inward
    
-   left=1 ('b'), right=2 ('c'): mismatch!
    
    -   Delete 'b': check "ca" → not palindrome
        
    -   Delete 'c': check "bc" → not palindrome... wait
        
    -   Actually: delete 'b': check s\[2:3\]="c" → palindrome ✓
        
-   Result: True ✓
    

**Helper Function:**

-   isPalindrome(s, left, right): check if substring is palindrome
    

**Time Complexity:** O(n) - at most two passes **Space Complexity:** O(1) - only pointers

**Time: O(n)**

**Space: O(1)**

````
class Solution {
    public boolean validPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return isPalin(s, left + 1, right) || isPalin(s, left, right - 1);
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean isPalin(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}
````
