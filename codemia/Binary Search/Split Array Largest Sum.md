# Split Array Largest Sum
Given an integer array nums and an integer m, split the array into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.

Example 1:
Input: {"nums":[7,2,5,10,8],"m":2}
Output: 18

**Approach: Binary Search on Answer**

**Key Insight:** If we can split with max sum M, we can also do it with any max > M. Binary search for minimum valid max sum.

**Algorithm:**

1.  Search range: \[max(nums), sum(nums)\]
    
2.  For each candidate max\_sum:
    
    -   Greedily split: start new subarray when exceeding max\_sum
        
    -   Count subarrays needed
        
3.  If count ≤ k: valid, try smaller max\_sum
    
4.  Else: need larger max\_sum
    

**Example:** nums = \[7,2,5,10,8\], k = 2

-   max\_sum=18: \[7,2,5\], \[10,8\] = 2 subarrays ✓
    
-   max\_sum=17: \[7,2,5\], \[10\], \[8\] = 3 subarrays ✗
    
-   Result: 18
    

**Time Complexity:** O(n log S) - S = sum of array **Space Complexity:** O(1) - only variables

**Time: O(n log S)**

**Space: O(1)**

```
class Solution {
    public int splitArray(int[] nums, int m) {
        int l = 0, r = 0;
        for (int n : nums) {
            l = Math.max(l, n);
            r += n;
        }
        int res = r;
        
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (canSplit(mid, nums, m)) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }
    
    private boolean canSplit(int largest, int[] nums, int m) {
        int subarray = 1;
        int curSum = 0;
        for (int n : nums) {
            curSum += n;
            if (curSum > largest) {
                subarray++;
                curSum = n;
            }
        }
        return subarray <= m;
    }
}
```
