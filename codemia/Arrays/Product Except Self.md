# Product Except Self
Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].

## Example 1:
Input: [1,2,3,4]
Output: [24,12,8,6]

## Approach: Prefix and Suffix Products

Key Insight: Product except self = (product of all elements to the left) × (product of all elements to the right). No division needed.

Algorithm:

First pass (left to right): result[i] = prefix product (product of nums[0..i-1])
Second pass (right to left): multiply result[i] by suffix product (product of nums[i+1..n-1])
Example: nums = [1, 2, 3, 4]

Prefix pass: [1, 1, 2, 6] (empty, 1, 1×2, 1×2×3)
Suffix pass: multiply by [24, 12, 4, 1]
Result: [24, 12, 8, 6]
Why it works:

For index i: left product × right product = all except nums[i]
No division, handles zeros correctly
Time Complexity: O(n) - two passes Space Complexity: O(1) - output array doesn't count

## Time: O(n)  Space: O(1)

```
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        int prefix = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] = prefix;
            prefix *= nums[i];
        }
        int postfix = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= postfix;
            postfix *= nums[i];
        }
        return res;
    }
}
```
