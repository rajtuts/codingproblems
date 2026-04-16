# House Robber II
You are a robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Adjacent houses have security systems connected and will automatically contact police if two adjacent houses were broken into on the same night. Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.

Example 1:
Input: [2,3,2]
Output: 3

**Approach: DP with Two Cases**

This extends the House Robber problem to circular houses.

**Key Insight:** Since houses form a circle, if we rob house 0, we cannot rob house n-1.

**Strategy:** Split into two mutually exclusive cases:

1.  **Case 1:** Consider houses 0 to n-2 (exclude last house)
    
2.  **Case 2:** Consider houses 1 to n-1 (exclude first house)
    

The answer is max(Case1, Case2).

**House Robber DP (for linear array):**

`def rob_linear(houses): prev, curr = 0, 0 for h in houses: prev, curr = curr, max(curr, prev + h) return curr`

At each house, we decide: skip it (keep curr) or rob it (prev + h).

**Example: \[2, 3, 2\]**

-   Case 1 (skip last): \[2, 3\] → max is 3
    
-   Case 2 (skip first): \[3, 2\] → max is 3
    
-   Answer: max(3, 3) = 3
    

**Example: \[1, 2, 3, 1\]**

-   Case 1: \[1, 2, 3\] → 1+3=4
    
-   Case 2: \[2, 3, 1\] → 2+1=3 or just 3
    
-   Answer: max(4, 3) = 4
    

**Time Complexity:** O(n) - two passes through ~n elements **Space Complexity:** O(1) - only two variables per case

**Time: O(n)**

**Space: O(1)**




```
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];

        // Case 1: Skip last, Case 2: Skip first
        return Math.max(
            robLinear(nums, 0, nums.length - 2),
            robLinear(nums, 1, nums.length - 1)
        );
    }

    private int robLinear(int[] nums, int start, int end) {
        int prev = 0, curr = 0;
        for (int i = start; i <= end; i++) {
            int temp = curr;
            curr = Math.max(curr, prev + nums[i]);
            prev = temp;
        }
        return curr;
    }
}
```
