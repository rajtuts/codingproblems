# Binary Search
Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.

## Example 1:  
Input: {"nums":[-1,0,3,5,9,12],"target":9}  
Output: 4  

**Approach: Binary Search**

**Key Insight:** In a sorted array, we can eliminate half the search space with each comparison.

**Algorithm:**

1.  Initialize left = 0, right = len(nums) - 1      
2.  While left <= right:      
    -   Calculate mid = (left + right) // 2         
    -   If nums\[mid\] == target: return mid          
    -   If nums\[mid\] < target: search right half (left = mid + 1)          
    -   If nums\[mid\] > target: search left half (right = mid - 1)          
3.  If not found, return -1      
**Why O(log n):** Each iteration cuts the search space in half.  
-   n → n/2 → n/4 → ... → 1      
-   Takes log₂(n) iterations     

**Common Pitfalls:**  
-   Integer overflow: Use `mid = left + (right - left) // 2` instead of `(left + right) // 2`      
-   Off-by-one errors: Be careful with left <= right vs left < right      
-   Infinite loops: Ensure left or right moves each iteration      

**Time: O(log n)**  
**Space: O(1)**

```
class Solution {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] > target) {
                r = m - 1;
            } else if (nums[m] < target) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }
}
```
