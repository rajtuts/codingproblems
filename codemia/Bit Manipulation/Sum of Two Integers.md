# Sum of Two Integers
Given two integers a and b, return the sum of the two integers without using the operators + and -. Use bitwise operations instead.

Example 1:
Input: {"a":1,"b":2}
Output: 3

XOR gives sum without carry, AND + shift gives carry. Repeat until no carry.

Time: O(1)
Space: O(1)

```
class Solution {
    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }
}
```
