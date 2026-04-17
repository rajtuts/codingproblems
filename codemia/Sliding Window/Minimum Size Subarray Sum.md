# Minimum Size Subarray Sum
Given an array of positive integers nums and a positive integer target, return the minimal length of a subarray whose sum is greater than or equal to target. If there is no such subarray, return 0.

Example 1:
Input: {"target":7,"nums":[2,3,1,2,4,3]}
Output: 2

**Approach: Sliding Window (Two Pointers)**

This is a classic sliding window problem where we need to find the minimum window that satisfies a condition.

**Algorithm:**

1.  Initialize two pointers `left` and `right` at the start.
    
2.  Expand the window by moving `right` and adding `nums[right]` to our sum.
    
3.  When `sum >= target`, we have a valid window:
    
    -   Update `min_len` if current window is smaller.
        
    -   Try to shrink from the left by removing `nums[left]` and incrementing `left`.
        
    -   Keep shrinking while the window remains valid.
        
4.  Continue until `right` reaches the end.
    

**Why it works:**

-   Since all numbers are positive, adding elements only increases the sum.
    
-   Once we have a valid window, shrinking from the left is the only way to find a smaller valid window.
    
-   We never need to revisit elements we've removed from the left.
    

**Example with target=7, nums=\[2,3,1,2,4,3\]:**

-   right=0: sum=2, not valid
    
-   right=1: sum=5, not valid
    
-   right=2: sum=6, not valid
    
-   right=3: sum=8 >= 7, valid! len=4, shrink: remove 2, sum=6
    
-   right=4: sum=10 >= 7, valid! len=4, shrink: remove 3, sum=7 >= 7, len=3, shrink: remove 1, sum=6
    
-   right=5: sum=9 >= 7, valid! len=3, shrink: remove 2, sum=7 >= 7, len=2 (answer!)
    

**Time Complexity:** O(n) - each element is added and removed at most once **Space Complexity:** O(1) - only using a few variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0, sum = 0;
        int minLen = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
```
