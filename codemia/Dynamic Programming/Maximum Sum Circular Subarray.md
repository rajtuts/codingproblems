Maximum Sum Circular Subarray
Given a circular integer array nums, return the maximum possible sum of a non-empty subarray. A circular array means the end connects to the beginning.

Example 1:
Input: [5,-3,5]
Output: 10

**Approach: Kadane's Algorithm for Both Max and Min**

**Key Insight:** A circular subarray that wraps around is equivalent to:

-   Total sum MINUS the minimum subarray in the middle
    
-   If we exclude the smallest portion, we get the largest wrap-around portion
    

**Two Cases:**

1.  **Non-wrapping:** Max subarray doesn't cross the boundary → use regular Kadane
    
2.  **Wrapping:** Max subarray wraps around → equals total - minSubarray
    

**Algorithm:**

1.  Run Kadane for maximum subarray sum
    
2.  Run Kadane for minimum subarray sum
    
3.  Calculate circular max = total - min\_sum
    
4.  Return max(max\_sum, circular\_max)
    

**Edge Case (All Negative):** If all numbers are negative, max\_sum < 0 and min\_sum = total.

-   circular\_max = total - total = 0 (empty subarray, invalid!)
    
-   Return max\_sum instead
    

**Example:** \[5,-3,5\]

-   max\_sum = 7 (from \[5,-3,5\])? No, Kadane gives 5 or 5
    
-   Actually max\_sum = 7, min\_sum = -3, total = 7
    
-   circular\_max = 7 - (-3) = 10 (wrap: \[5\] + \[5\])
    
-   Return max(7, 10) = 10 ✓
    

**Time Complexity:** O(n) - single pass computes both max and min **Space Complexity:** O(1) - constant extra space

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int total = 0, maxSum = nums[0], minSum = nums[0];
        int currentMax = 0, currentMin = 0;
        for (int num : nums) {
            currentMax = Math.max(currentMax + num, num);
            maxSum = Math.max(maxSum, currentMax);
            currentMin = Math.min(currentMin + num, num);
            minSum = Math.min(minSum, currentMin);
            total += num;
        }
        return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
    }
}
```
