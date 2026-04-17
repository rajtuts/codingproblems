# Palindrome Number
Given an integer x, return true if x is a palindrome, and false otherwise.

Example 1:
Input: 121
Output: true

**Approach: Digit Extraction and Reconstruction**

**Key Insight:** Extract digits from the end using modulo, and build the reversed number by multiplying by 10 and adding each digit.

**Algorithm:**

1.  Store the sign and work with absolute value
    
2.  While x has digits:
    
    -   Extract last digit: `x % 10`
        
    -   Add to result: `result = result * 10 + digit`
        
    -   Remove last digit: `x //= 10`
        
3.  Apply original sign
    
4.  Check for 32-bit overflow before returning
    

**Example:** x = 123

-   x=123, digit=3, result=3, x=12
    
-   x=12, digit=2, result=32, x=1
    
-   x=1, digit=1, result=321, x=0
    
-   Return 321 ✓
    

**Example:** x = -123

-   sign=-1, x=123
    
-   (same process as above)
    
-   result=321, apply sign → -321 ✓
    

**Overflow Handling:** 32-bit signed integer range: \[-2147483648, 2147483647\]

-   Check `result` after applying sign
    
-   Return 0 if outside valid range
    

**Time Complexity:** O(log₁₀ x) - number of digits **Space Complexity:** O(1) - constant extra space

**Time: O(log x)**

**Space: O(1)Approach: Reverse Half of the Number**

**Key Insight:** Instead of reversing the entire number (risk of overflow), reverse only half and compare with the other half. This avoids string conversion and handles overflow naturally.

**Algorithm:**

1.  Quick rejections:
    
    -   Negative numbers: not palindromes (-121 ≠ 121-)
        
    -   Numbers ending in 0 (except 0 itself): 10 reversed is 01
        
2.  Reverse the second half:
    
    -   Extract digits from x while x > reverted
        
    -   This stops when we've processed half (or just past half)
        
3.  Compare halves:
    
    -   Even digits: x == reverted (e.g., 1221 → x=12, rev=12)
        
    -   Odd digits: x == reverted // 10 (e.g., 12321 → x=12, rev=123, ignore middle)
        

**Example:** x = 121

-   x=121, reverted=0
    
-   x=12, reverted=1
    
-   x=1, reverted=12
    
-   x < reverted? No, continue
    
-   x=1, reverted=12, x == reverted//10? 1 == 1 ✓
    

**Example:** x = 1221

-   x=122, reverted=1
    
-   x=12, reverted=12
    
-   x <= reverted, stop
    
-   x == reverted? 12 == 12 ✓
    

**Time Complexity:** O(log₁₀ n) - processes half the digits **Space Complexity:** O(1) - only uses integers

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;

        int reversedHalf = 0;
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }

        return x == reversedHalf || x == reversedHalf / 10;
    }
}
```
