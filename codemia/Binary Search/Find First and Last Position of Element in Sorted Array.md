# Find First and Last Position of Element in Sorted Array
Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value. If target is not found, return [-1, -1].

Example 1:
Input: {"nums":[5,7,7,8,8,10],"target":8}
Output: [3, 4]

**Approach: Two Binary Searches**

**Key Insight:** Use two modified binary searches: one to find the leftmost occurrence (first position), one to find the rightmost occurrence (last position).

**Algorithm:**

1.  Find leftmost: when nums\[mid\] == target, continue searching left (right = mid - 1)
    
2.  Find rightmost: when nums\[mid\] == target, continue searching right (left = mid + 1)
    
3.  Return \[leftmost, rightmost\] or \[-1, -1\] if not found
    

**Example:** nums = \[5,7,7,8,8,10\], target = 8

-   Find leftmost 8:
    
    -   mid=2 (val=7 < 8), left=3
        
    -   mid=4 (val=8 == 8), right=3, save ans=4
        
    -   mid=3 (val=8 == 8), right=2, save ans=3
        
    -   Leftmost = 3
        
-   Find rightmost 8:
    
    -   mid=2 (val=7 < 8), left=3
        
    -   mid=4 (val=8 == 8), left=5, save ans=4
        
    -   mid=5 (val=10 > 8), right=4
        
    -   Rightmost = 4
        
-   Result: \[3, 4\] ✓
    

**Time Complexity:** O(log n) - two binary searches **Space Complexity:** O(1) - only pointers

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int[] searchRange(int[] nums, int target) {
        return new int[]{findFirst(nums, target), findLast(nums, target)};
    }

    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                if (nums[mid] == target) result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                if (nums[mid] == target) result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
}
```
