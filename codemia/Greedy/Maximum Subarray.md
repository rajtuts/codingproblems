# Maximum Subarray
Given an integer array nums, find the subarray with the largest sum, and return its sum.

Example 1:
Input: [-2,1,-3,4,-1,2,1,-5,4]
Output: 6

**Approach: Kadane's Algorithm**

**Key Insight:** At each position, we have two choices:

1.  Extend the current subarray (add current element)
    
2.  Start a new subarray from current element
    

We choose whichever gives a larger sum.

**Algorithm:**

1.  Initialize current\_sum = max\_sum = nums\[0\]
    
2.  For each element from index 1:
    
    -   current\_sum = max(num, current\_sum + num)
        
    -   max\_sum = max(max\_sum, current\_sum)
        
3.  Return max\_sum
    

**Why It Works:** If current\_sum becomes negative, starting fresh is always better. We're essentially tracking the best ending position for each index.

**Example:** nums = \[-2,1,-3,4,-1,2,1,-5,4\]

**IndexNumcurrent\_summax\_sum**0-2-2-211max(1,-1)=112-3max(-3,-2)=-2134max(4,2)=444-1max(-1,3)=3452max(2,5)=5561max(1,6)=6**6**

**Variant:** To find the actual subarray, track start and end indices when max\_sum updates.

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxSubArray(int[] nums) {
        int maxSub = nums[0];
        int curSum = 0;
        
        for (int n : nums) {
            if (curSum < 0) {
                curSum = 0;
            }
            curSum += n;
            maxSub = Math.max(maxSub, curSum);
        }
        return maxSub;
    }
}
