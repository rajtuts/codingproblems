# Sort Colors
Given an array with n objects colored red, white, or blue (represented by 0, 1, and 2), sort them in-place so that objects of the same color are adjacent (Dutch National Flag problem).

Example 1:
Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]

**Approach: Dutch National Flag Algorithm**

**Key Insight:** Use three pointers: low (boundary for 0s), mid (current element), high (boundary for 2s). Process elements and swap to correct positions.

**Algorithm:**

1.  Initialize low=0, mid=0, high=n-1
    
2.  While mid <= high:
    
    -   If nums\[mid\] == 0: swap with low, increment both
        
    -   If nums\[mid\] == 1: just increment mid
        
    -   If nums\[mid\] == 2: swap with high, decrement high only
        
3.  Array is partitioned: \[0s\]\[1s\]\[2s\]
    

**Example:** nums = \[2,0,2,1,1,0\]

-   mid=0, nums\[0\]=2: swap with high, \[0,0,2,1,1,2\], high=4
    
-   mid=0, nums\[0\]=0: swap with low, \[0,0,2,1,1,2\], low=1, mid=1
    
-   mid=1, nums\[1\]=0: swap with low, \[0,0,2,1,1,2\], low=2, mid=2
    
-   mid=2, nums\[2\]=2: swap with high, \[0,0,1,1,2,2\], high=3
    
-   mid=2, nums\[2\]=1: mid=3
    
-   mid=3, nums\[3\]=1: mid=4 > high, done
    
-   Result: \[0,0,1,1,2,2\] ✓
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - in-place swaps

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;
                high--;
            }
        }
    }
}
```
