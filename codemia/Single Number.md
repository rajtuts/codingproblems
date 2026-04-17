# Single Number
Given a non-empty array of integers nums, every element appears twice except for one. Find that single one. You must implement a solution with linear runtime complexity and use only constant extra space.

Example 1:
Input: [2,2,1]
Output: 1

**Approach: XOR Bit Manipulation**

This problem leverages the powerful properties of XOR (exclusive or) operation.

**XOR Properties:**

1.  `a ^ a = 0` - XOR of a number with itself is 0
    
2.  `a ^ 0 = a` - XOR of a number with 0 is the number itself
    
3.  XOR is commutative: `a ^ b = b ^ a`
    
4.  XOR is associative: `(a ^ b) ^ c = a ^ (b ^ c)`
    

**Algorithm:**

1.  Initialize result = 0
    
2.  XOR every number in the array with result
    
3.  Duplicates cancel out (a ^ a = 0), leaving only the single number
    

**Example: \[4, 1, 2, 1, 2\]**

`result = 0 result = 0 ^ 4 = 4 (binary: 0000 ^ 0100 = 0100) result = 4 ^ 1 = 5 (binary: 0100 ^ 0001 = 0101) result = 5 ^ 2 = 7 (binary: 0101 ^ 0010 = 0111) result = 7 ^ 1 = 6 (binary: 0111 ^ 0001 = 0110) result = 6 ^ 2 = 4 (binary: 0110 ^ 0010 = 0100)`

The pairs (1,1) and (2,2) cancel out, leaving 4!

**Time Complexity:** O(n) - single pass through array **Space Complexity:** O(1) - only one variable used

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num; // XOR cancels out duplicates
        }
        return result;
    }
}
```
