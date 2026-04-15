# Longest Increasing Subsequence
Given an integer array nums, return the length of the longest strictly increasing subsequence.

Example 1:
Input: {"nums":[10,9,2,5,3,7,101,18]}
Output: 4

**Approach: Binary Search (Patience Sorting)**

**Key Insight:** Maintain an array `tails` where tails\[i\] is the smallest possible tail element of an increasing subsequence of length i+1. Use binary search to find where each new element fits.

**Algorithm:**

1.  Initialize empty tails array
    
2.  For each number num:
    
    -   Binary search for position to place num (first element >= num)
        
    -   If position == len(tails): append (extends longest LIS)
        
    -   Otherwise: replace tails\[position\] with num (keeps smaller tail)
        
3.  Return len(tails)
    

**Example:** nums = \[10,9,2,5,3,7,101,18\]

-   10: tails = \[10\]
    
-   9: replace 10 → tails = \[9\]
    
-   2: replace 9 → tails = \[2\]
    
-   5: append → tails = \[2,5\]
    
-   3: replace 5 → tails = \[2,3\]
    
-   7: append → tails = \[2,3,7\]
    
-   101: append → tails = \[2,3,7,101\]
    
-   18: replace 101 → tails = \[2,3,7,18\]
    
-   Result: len(tails) = 4 ✓
    

**Why Replace Works:**

-   Smaller tails give more room for future elements
    
-   Doesn't affect length, just optimizes for future extensions
    

**Alternative: O(n²) DP**

-   dp\[i\] = longest LIS ending at index i
    
-   For each i, check all j < i where nums\[j\] < nums\[i\]
    

**Time Complexity:** O(n log n) - n elements, log n binary search each **Space Complexity:** O(n) - tails array

**Time: O(n log n)**

**Space: O(n)**

```
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        
        for (int i = nums.length - 1; i >= 0; i--) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}
```
