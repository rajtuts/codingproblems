# Find Minimum in Rotated Sorted Array  
Suppose an array of length n sorted in ascending order is rotated between 1 and n times. Find the minimum element of this array.

Example 1:
Input: {"nums":[3,4,5,1,2]}
Output: 1

**Approach: Binary Search with Rotation Awareness**

**Key Insight:** In a rotated sorted array, the minimum is at the rotation point. Compare mid with right to determine which half contains the minimum.

**Property:**

-   If nums\[mid\] > nums\[right\]: rotation point (minimum) is in right half
    
-   Otherwise: minimum is in left half (including mid)
    

**Algorithm:**

1.  Binary search with left < right (not <=)
    
2.  If nums\[mid\] > nums\[right\]: search right half (left = mid + 1)
    
3.  Else: search left half including mid (right = mid)
    
4.  When left == right, we found the minimum
    

**Example:** \[4,5,6,7,0,1,2\]

`left=0, right=6, mid=3: nums[3]=7 > nums[6]=2 → min in right half, left=4 left=4, right=6, mid=5: nums[5]=1 < nums[6]=2 → min in left half, right=5 left=4, right=5, mid=4: nums[4]=0 < nums[5]=1 → min in left half, right=4 left=right=4, return nums[4]=0 ✓`

**Why Compare with Right, Not Left?**

`[3,4,5,1,2]: mid=5 > right=2 → min in right ✓ [1,2,3,4,5]: mid=3 < right=5 → min in left ✓`

Comparing with right correctly identifies the unsorted half.

**Time Complexity:** O(log n) - binary search **Space Complexity:** O(1) - constant space

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int findMin(int[] nums) {
        int res = nums[0];
        int l = 0, r = nums.length - 1;
        
        while (l <= r) {
            if (nums[l] < nums[r]) {
                res = Math.min(res, nums[l]);
                break;
            }
            
            int m = l + (r - l) / 2;
            res = Math.min(res, nums[m]);
            
            if (nums[m] >= nums[l]) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return res;
    }
}
```
