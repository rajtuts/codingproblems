# Maximum Product Subarray
Given an integer array nums, find a subarray that has the largest product, and return the product.

Example 1:
Input: [2,3,-2,4]
Output: 6

**Approach: Track Both Max and Min Products**

**Key Insight:** Unlike maximum sum subarray, products can be negative. A negative number times a very negative product can become the maximum. Track both max and min products ending at each position.

**Algorithm:**

1.  Initialize max\_prod = min\_prod = result = nums\[0\]
    
2.  For each number from index 1:
    
    -   candidates = (num, max\_prod × num, min\_prod × num)
        
    -   max\_prod = max(candidates)
        
    -   min\_prod = min(candidates)
        
    -   result = max(result, max\_prod)
        
3.  Return result
    

**Example:** nums = \[2,3,-2,4\]

-   Start: max=2, min=2, result=2
    
-   num=3: candidates=(3, 6, 6) → max=6, min=3, result=6
    
-   num=-2: candidates=(-2, -12, -6) → max=-2, min=-12, result=6
    
-   num=4: candidates=(4, -8, -48) → max=4, min=-48, result=6 ✓
    

**Example:** nums = \[-2,3,-4\]

-   Start: max=-2, min=-2, result=-2
    
-   num=3: candidates=(3, -6, -6) → max=3, min=-6, result=3
    
-   num=-4: candidates=(-4, -12, 24) → max=24, result=24 ✓
    

**Why Track Min:**

-   Current min × negative = potential new max
    
-   Must consider all three possibilities each step
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - constant variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxProduct(int[] nums) {
        int res = nums[0];
        for (int n : nums) res = Math.max(res, n);
        
        int curMin = 1, curMax = 1;
        
        for (int n : nums) {
            if (n == 0) {
                curMin = 1;
                curMax = 1;
                continue;
            }
            int tmp = curMax * n;
            curMax = Math.max(n * curMax, Math.max(n * curMin, n));
            curMin = Math.min(tmp, Math.min(n * curMin, n));
            res = Math.max(res, curMax);
        }
        return res;
    }
}
```
