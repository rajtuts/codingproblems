# Remove Element
Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. Return the number of elements in nums which are not equal to val.

## Example 1:  
Input: {"nums":[3,2,2,3],"val":3}  
Output: 2  

**Approach: Two Pointers (Overwrite)**  
**Key Insight:** Use one pointer to iterate through the array, and another to track where to place the next non-val element.  

**Algorithm:**  
1.  `k` = write pointer (position for next valid element)      
2.  `i` = read pointer (iterates through array)      
3.  For each element:      
    -   If nums\[i\] ≠ val: copy to nums\[k\], increment k          
    -   If nums\[i\] = val: skip it          
4.  Return k (count of valid elements)      

**Example:** nums = \[3,2,2,3\], val = 3  
`i=0: nums[0]=3 == val, skip i=1: nums[1]=2 ≠ val, nums[0]=2, k=1 → [2,2,2,3] i=2: nums[2]=2 ≠ val, nums[1]=2, k=2 → [2,2,2,3] i=3: nums[3]=3 == val, skip Return k=2, first 2 elements are [2,2] ✓`  

**Key Properties:**  
-   In-place modification (no extra array)      
-   Preserves relative order of non-val elements    
-   Elements after index k are irrelevant (can be anything)      

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - in-place  
**Time: O(n)**  **Space: O(1)**

```
class Solution {
    public int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
}
```
