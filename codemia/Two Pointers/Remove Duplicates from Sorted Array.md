# Remove Duplicates from Sorted Array
Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. Return the number of unique elements.

Example 1:
Input: [1,1,2]
Output: 2

**Approach: Two Pointers (In-Place)**

**Key Insight:** Use a write pointer k to track where the next valid (non-val) element should go. Read through the array and copy non-val elements to position k.

**Algorithm:**

1.  Initialize write pointer k = 0
    
2.  For each element (read pointer i):
    
    -   If nums\[i\] != val: copy to nums\[k\], increment k
        
    -   If nums\[i\] == val: skip (don't increment k)
        
3.  Return k (new length)
    

**Example:** nums = \[3,2,2,3\], val = 3

-   i=0: nums\[0\]=3 == val, skip
    
-   i=1: nums\[1\]=2 ≠ val, nums\[0\]=2, k=1
    
-   i=2: nums\[2\]=2 ≠ val, nums\[1\]=2, k=2
    
-   i=3: nums\[3\]=3 == val, skip
    
-   Result: k=2, nums = \[2,2,...\]
    

**Key Points:**

-   Order of remaining elements is preserved
    
-   Elements after index k don't matter
    
-   In-place modification, no extra space
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - in-place

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return slow + 1;
    }
}
```
