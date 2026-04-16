# Burst Balloons
You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked to burst all the balloons. If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds, treat it as if there is a balloon with a 1 painted on it. Return the maximum coins you can collect by bursting the balloons wisely.

Example 1:
Input: {"nums":[3,1,5,8]}
Output: 167

**Approach: Interval DP (Reverse Thinking)**

**Key Insight:** Think in reverse: which balloon is LAST to burst in a range? When it's the last, boundaries are known (nums\[left\] and nums\[right\]).

**Algorithm:**

1.  Pad nums with 1s: \[1\] + nums + \[1\]
    
2.  dp\[left\]\[right\] = max coins for balloons between left and right
    
3.  For each length, for each range \[left, right\]:
    
    -   Try each balloon i as last to burst
        
    -   coins = nums\[left\] × nums\[i\] × nums\[right\] + dp\[left\]\[i\] + dp\[i\]\[right\]
        
4.  Return dp\[0\]\[n+1\]
    

**Example:** nums = \[3,1,5,8\] → \[1,3,1,5,8,1\]

-   For range (0,5), try each as last:
    
    -   Last=3: 1×3×1 + left\_coins + right\_coins
        
-   Build up from small ranges to full range
    
-   Result: 167
    

**Time Complexity:** O(n³) - O(n²) ranges, O(n) choices each **Space Complexity:** O(n²) - dp table

**Time: O(n³)**

**Space: O(n²)**



```
class Solution {
    public int maxCoins(int[] nums) {
        int[] arr = new int[nums.length + 2];
        arr[0] = arr[nums.length + 1] = 1;
        for (int i = 0; i < nums.length; i++) arr[i + 1] = nums[i];
        int n = arr.length;
        int[][] dp = new int[n][n];

        for (int length = 2; length < n; length++) {
            for (int left = 0; left < n - length; left++) {
                int right = left + length;
                for (int k = left + 1; k < right; k++) {
                    int coins = arr[left] * arr[k] * arr[right];
                    dp[left][right] = Math.max(dp[left][right],
                        dp[left][k] + coins + dp[k][right]);
                }
            }
        }

        return dp[0][n - 1];
    }
}
```
