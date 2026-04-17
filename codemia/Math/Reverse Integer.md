# Reverse Integer
Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range, return 0.

Example 1:
Input: 123
Output: 321

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

**Space: O(1)**

```
class Solution {
    public int reverse(int x) {
        int sign = x < 0 ? -1 : 1;
        long num = Math.abs((long) x);
        long result = 0;

        while (num > 0) {
            result = result * 10 + num % 10;
            num /= 10;
        }

        result *= sign;
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) return 0;
        return (int) result;
    }
}
````
