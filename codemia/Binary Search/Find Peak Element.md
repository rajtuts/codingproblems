# Find Peak Element
A peak element is an element that is strictly greater than its neighbors. Given a 0-indexed integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.

Example 1:
Input: {"nums":[1,2,3,1]}
Output: 2

**Approach: Binary Search**

**Key Insight:** If nums\[mid\] < nums\[mid+1\], we're on an ascending slope, so a peak must exist on the right. If nums\[mid\] > nums\[mid+1\], we're on a descending slope or at a peak, so search left.

**Algorithm:**

1.  Initialize left = 0, right = len(nums) - 1
    
2.  While left < right:
    
    -   mid = (left + right) // 2
        
    -   If nums\[mid\] > nums\[mid+1\]: right = mid (peak is mid or left of mid)
        
    -   Else: left = mid + 1 (peak is right of mid)
        
3.  Return left (left == right when loop ends)
    

**Example:** nums = \[1,2,3,1\]

-   left=0, right=3, mid=1: nums\[1\]=2 < nums\[2\]=3 → left=2
    
-   left=2, right=3, mid=2: nums\[2\]=3 > nums\[3\]=1 → right=2
    
-   left=2, right=2 → return 2 ✓
    

**Why It Works:**

-   Array has no duplicates, and nums\[-1\] = nums\[n\] = -∞
    
-   Always moving toward a higher point guarantees finding a peak
    

**Time Complexity:** O(log n) - binary search **Space Complexity:** O(1) - only pointers

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int findPeakElement(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (m > 0 && nums[m] < nums[m - 1]) {
                r = m - 1;
            } else if (m < nums.length - 1 && nums[m] < nums[m + 1]) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }
}
```
