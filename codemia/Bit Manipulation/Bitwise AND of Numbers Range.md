# Bitwise AND of Numbers Range
Given two integers left and right that represent the range [left, right], return the bitwise AND of all numbers in this range, inclusive.

Example 1:
Input: {"left":5,"right":7}
Output: 4

**Approach: Common Prefix**

**Key Insight:** The result is the common binary prefix of left and right. Any differing bit position will have both 0 and 1 in the range, making the AND result 0 for that bit.

**Algorithm:**

1.  Right-shift both left and right until they are equal
    
2.  Count the number of shifts
    
3.  Left-shift the result back by the same amount
    

**Example:** left=5 (101), right=7 (111)

-   Shift 1: left=2 (10), right=3 (11)
    
-   Shift 2: left=1 (1), right=1 (1) - equal!
    
-   Result: 1 << 2 = 4 (100)
    

**Time Complexity:** O(log n) **Space Complexity:** O(1)

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        int shift = 0;
        while (left < right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        return left << shift;
    }
}
```
