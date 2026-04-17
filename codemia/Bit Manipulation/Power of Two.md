# Power of Two
Given an integer n, return true if it is a power of two. Otherwise, return false.

Example 1:
Input: 16
Output: true

**Approach: Bit-by-Bit Check**

**Key Insight:** Use bitwise AND with 1 to check the least significant bit (LSB), then right-shift to process the next bit.

**Algorithm:**

1.  Initialize count = 0
    
2.  While n is not zero:
    
    -   Add (n & 1) to count (1 if LSB is 1, else 0)
        
    -   Right shift n by 1 (n >>= 1)
        
3.  Return count
    

**Example:** n = 11 (binary: 1011)

-   n=1011, n&1=1, count=1, n>>1=101
    
-   n=101, n&1=1, count=2, n>>1=10
    
-   n=10, n&1=0, count=2, n>>1=1
    
-   n=1, n&1=1, count=3, n>>1=0
    
-   n=0, exit loop, return 3 ✓
    

**Alternative (Brian Kernighan's Algorithm):**

`while n: count += 1 n &= (n - 1) # Clears the lowest set bit`

This runs in O(k) where k is the number of 1 bits (faster for sparse numbers).

**Time Complexity:** O(32) = O(1) for 32-bit integers **Space Complexity:** O(1) - single counter variable

**Time: O(1)**

**Space: O(1)Approach: Bit Manipulation**

**Key Insight:** Powers of two have exactly ONE bit set in binary:

-   1 = 0001, 2 = 0010, 4 = 0100, 8 = 1000, etc.
    

**The n & (n-1) Trick:**

-   `n - 1` flips all bits from the rightmost 1-bit to the end
    
-   `n & (n-1)` clears the lowest set bit
    
-   If n is a power of two (only one bit set), result is 0
    

**Example:** n = 8 (binary: 1000)

-   n = 1000
    
-   n-1 = 0111
    
-   n & (n-1) = 0000 ✓ (power of two)
    

**Example:** n = 6 (binary: 0110)

-   n = 0110
    
-   n-1 = 0101
    
-   n & (n-1) = 0100 ≠ 0 (not power of two)
    

**Why Check n > 0?**

-   n = 0: 0 is not a power of two
    
-   n < 0: Negative numbers are not powers of two
    

**Alternative Approaches:**

-   Loop: Keep dividing by 2, check if reaches 1
    
-   Count bits: Exactly one bit should be set
    
-   Math: `log2(n)` should be an integer
    

**Time Complexity:** O(1) - single bitwise operation **Space Complexity:** O(1) - no extra space

**Time: O(1)**

**Space: O(1)**

```
class Solution {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
```
