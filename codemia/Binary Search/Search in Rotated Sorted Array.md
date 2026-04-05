# Search in Rotated Sorted Array
Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.  

## Example 1:  
Input: {"nums":[4,5,6,7,0,1,2],"target":0}  
Output: 4  

**Approach: Modified Binary Search**

**Key Insight:** In a rotated array, at least one half is always sorted. Determine which half is sorted, then check if target is in that sorted half.

**How to Identify Sorted Half:**

-   If nums\[left\] <= nums\[mid\]: LEFT half is sorted
    
-   Else: RIGHT half is sorted
    

**Algorithm:**

1.  If nums\[mid\] == target: return mid
    
2.  If left half is sorted (nums\[left\] <= nums\[mid\]):
    
    -   If target is in \[left, mid): search left
        
    -   Else: search right
        
3.  Else (right half is sorted):
    
    -   If target is in (mid, right\]: search right
        
    -   Else: search left
        

**Example:** nums = \[4,5,6,7,0,1,2\], target = 0

`left=0, right=6, mid=3: nums[3]=7 Left half [4,5,6,7] is sorted (4 <= 7) Is 0 in [4,7)? No → search right left=4, right=6, mid=5: nums[5]=1 Left half [0,1] not sorted (0 > 1)... wait Actually nums[4]=0 <= nums[5]=1, left sorted Is 0 in [0,1)? Yes! → search left left=4, right=4, mid=4: nums[4]=0 == target ✓`

**Why This Works:** The rotation creates exactly one "break point". One half is always contiguous (sorted), making it easy to determine if target could be there.

**Time Complexity:** O(log n) - binary search **Space Complexity:** O(1) - constant space

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) return mid;
            
            // Left sorted
            if (nums[l] <= nums[mid]) {
                if (target > nums[mid] || target < nums[l]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } 
            // Right sorted
            else {
                if (target < nums[mid] || target > nums[r]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }
        return -1;
    }
}
```
