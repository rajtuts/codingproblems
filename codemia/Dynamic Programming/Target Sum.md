# Target Sum
You are given an integer array nums and an integer target. You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums. Return the number of different expressions that you can build, which evaluates to target.

Example 1:
Input: {"nums":[1,1,1,1,1],"target":3}
Output: 5

**Approach: Convert to Subset Sum Problem**

**Key Insight:** Let P = sum of positive elements, N = sum of negative elements.

-   P + N = total (sum of all elements)
    
-   P - N = target (desired result)
    
-   Solving: P = (total + target) / 2
    

Finding ways to make target = counting subsets that sum to P!

**Mathematical Derivation:**

`P - N = target ... (1) P + N = total ... (2) Adding: 2P = total + target So: P = (total + target) / 2`

**Algorithm:**

1.  Check if (total + target) is even and non-negative
    
2.  Find number of subsets summing to (total + target) / 2
    
3.  Use 1D DP: dp\[j\] = ways to achieve sum j
    
4.  Iterate backwards to avoid reusing elements
    

**Example:** nums = \[1,1,1,1,1\], target = 3

-   total = 5, P = (5+3)/2 = 4
    
-   Count subsets summing to 4
    
-   Subsets: {1,1,1,1} (indices 0,1,2,3), (0,1,2,4), (0,1,3,4), (0,2,3,4), (1,2,3,4)
    
-   5 ways ✓
    

**DP Transition:**

`for num in nums: for j in range(subset_sum, num-1, -1): dp[j] += dp[j - num] # Add ways using current num`

**Time Complexity:** O(n × sum) where sum = (total + target) / 2 **Space Complexity:** O(sum) for 1D DP array

**Time: O(n \* sum)**

**Space: O(sum)**

```
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) sum += num;

        // (sum + target) must be even and non-negative
        if ((sum + target) % 2 != 0 || sum + target < 0) return 0;

        int subsetSum = (sum + target) / 2;
        int[] dp = new int[subsetSum + 1];
        dp[0] = 1;

        for (int num : nums) {
            for (int j = subsetSum; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[subsetSum];
    }
}
```
