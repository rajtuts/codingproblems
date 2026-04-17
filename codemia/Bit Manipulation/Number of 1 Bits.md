# Number of 1 Bits
Write a function that takes the binary representation of an unsigned integer and returns the number of 1 bits it has (also known as the Hamming weight).

Example 1:
Input: 11
Output: 3

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

**Space: O(1)**

```
public class Solution {
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>>= 1;
        }
        return count;
    }
}
