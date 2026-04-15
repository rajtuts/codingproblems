# Search Insert Position
Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be inserted.

Example 1:
Input: {"nums":[1,3,5,6],"target":5}
Output: 2

**Approach: Binary Search**

**Key Insight:** This is finding the "lower bound" - the first position where we could insert target while keeping the array sorted.

**Algorithm:**

1.  Standard binary search on sorted array
    
2.  If target found, return its index
    
3.  If not found, return `left` - the position where target should be inserted
    

**Why Return** `left`**?** When binary search ends without finding target:

-   `left` points to the first element > target (or end of array)
    
-   This is exactly where target should be inserted
    

**Example:** nums = \[1,3,5,6\], target = 2

-   \[0,3\]: mid=1, nums\[1\]=3 > 2 → right=0
    
-   \[0,0\]: mid=0, nums\[0\]=1 < 2 → left=1
    
-   left > right, return left=1 ✓
    

**Example:** nums = \[1,3,5,6\], target = 7

-   All elements < 7, so left keeps moving right
    
-   Eventually left = 4 (past end of array)
    
-   Return 4 ✓
    

**Time Complexity:** O(log n) - binary search **Space Complexity:** O(1) - constant extra space

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }

        return left;
    }
}
```
