# Partition Equal Subset Sum
Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal.

Example 1:
Input: [1,5,11,5]
Output: true

**Approach: 0/1 Knapsack DP**

**Key Insight:** The problem reduces to: can we find a subset that sums to total/2? If yes, the remaining elements also sum to total/2, giving equal partition.

**Algorithm:**

1.  If total is odd: return False (can't split evenly)
    
2.  Target = total / 2
    
3.  Use 1D DP: dp\[j\] = True if sum j is achievable
    
4.  For each num, update dp\[j\] = dp\[j\] or dp\[j-num\]
    
5.  Return dp\[target\]
    

**Example:** nums = \[1,5,11,5\], total = 22

-   Target = 11
    
-   dp\[0\] = True initially
    
-   Process 1: dp\[1\] = True
    
-   Process 5: dp\[5\], dp\[6\] = True
    
-   Process 11: dp\[11\], dp\[16\] = True
    
-   Process 5: dp\[10\], dp\[11\] already True
    
-   Return dp\[11\] = True ✓
    

**Why Iterate j Backwards:**

-   Prevents using same element twice
    
-   Each element used at most once (0/1 knapsack)
    

**Time Complexity:** O(n × sum) where sum = total/2 **Space Complexity:** O(sum) - 1D DP array

**Time: O(n\*target)**

**Space: O(target)**

```
class Solution {
    public boolean canPartition(int[] nums) {
        int total = 0;
        for (int num : nums) total += num;
        if (total % 2 != 0) return false;

        int target = total / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
        }

        return dp[target];
    }
}
````
