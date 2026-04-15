# House Robber
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Example 1:
Input: [1,2,3,1]
Output: 4

**Approach: Dynamic Programming**

**Key Insight:** At each house, decide: rob it (add to profit from 2 houses back) or skip it (keep profit from previous house). The recurrence is dp\[i\] = max(dp\[i-1\], dp\[i-2\] + nums\[i\]).

**Algorithm:**

1.  dp\[0\] = nums\[0\] (only first house)
    
2.  dp\[1\] = max(nums\[0\], nums\[1\]) (better of first two)
    
3.  For i from 2 to n-1: dp\[i\] = max(dp\[i-1\], dp\[i-2\] + nums\[i\])
    
4.  Return dp\[n-1\]
    

**Example:** nums = \[2,7,9,3,1\]

-   dp\[0\] = 2
    
-   dp\[1\] = max(2,7) = 7
    
-   dp\[2\] = max(7, 2+9) = 11
    
-   dp\[3\] = max(11, 7+3) = 11
    
-   dp\[4\] = max(11, 11+1) = 12 ✓
    

**Space Optimization:**

-   Only need previous two values
    
-   O(1) space with variables prev1, prev2
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(n) - or O(1) with optimization

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int rob(int[] nums) {
        int rob1 = 0;
        int rob2 = 0;
        
        for (int n : nums) {
            int temp = Math.max(n + rob1, rob2);
            rob1 = rob2;
            rob2 = temp;
        }
        return rob2;
    }
}
```
