# Reverse Bits
Reverse bits of a given 32 bits unsigned integer.

Example 1:
Input: 43261596
Output: 964176192

**Approach: Bit-by-Bit Reversal**

**Key Insight:** For a 32-bit integer, the bit at position i should move to position 31-i. Extract each bit and place it in the reversed position.

**Algorithm:**

1.  Initialize result = 0
    
2.  For i from 0 to 31:
    
    -   Extract bit at position i: (n >> i) & 1
        
    -   Place it at position (31 - i): bit << (31 - i)
        
    -   Add to result: result |= shifted\_bit
        
3.  Return result
    

**Example:** n = 43261596 (00000010100101000001111010011100)

-   Bit 0 (0) → position 31
    
-   Bit 1 (0) → position 30
    
-   ... (reverse all 32 bits)
    
-   Result: 964176192 (00111001011110000010100101000000)
    

**Alternative: Shift Both Ways**

`result = 0 for _ in range(32): result = (result << 1) | (n & 1) n >>= 1`

**Time Complexity:** O(1) - always 32 iterations **Space Complexity:** O(1) - single result variable

**Time: O(1)**

**Space: O(1)**

```
public class Solution {
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int bit = (n >>> i) & 1;
            result |= bit << (31 - i);
        }
        return result;
    }
}
