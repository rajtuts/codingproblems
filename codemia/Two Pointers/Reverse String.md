Reverse String
Write a function that reverses a string. The input string is given as an array of characters s. You must do this by modifying the input array in-place with O(1) extra memory.

Example 1:
Input: ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]

**Approach: Two Pointers**

This is the classic in-place reversal using two pointers.

**Algorithm:**

1.  Initialize left = 0, right = n - 1
    
2.  While left < right:
    
    -   Swap s\[left\] and s\[right\]
        
    -   Increment left, decrement right
        
3.  When pointers meet/cross, we're done
    

**Visual Example: \["h", "e", "l", "l", "o"\]**

`Step 1: left=0, right=4 [h, e, l, l, o] → [o, e, l, l, h] ↑ ↑ Step 2: left=1, right=3 [o, e, l, l, h] → [o, l, l, e, h] ↑ ↑ Step 3: left=2, right=2 (pointers meet) Done! [o, l, l, e, h]`

**Why Two Pointers?**

-   Each element is swapped exactly once
    
-   No extra array needed
    
-   Simple and intuitive
    

**Alternative: Recursion**

`def reverse(l, r): if l >= r: return s[l], s[r] = s[r], s[l] reverse(l + 1, r - 1)`

But this uses O(n) stack space.

**Time Complexity:** O(n) - each element visited once **Space Complexity:** O(1) - only two pointers used

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;

        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
}
```
