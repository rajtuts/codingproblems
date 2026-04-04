# Next Permutation  
A permutation of an array of integers is an arrangement of its members into a sequence or linear order. The next permutation of an array is the next lexicographically greater permutation. If such arrangement is not possible, it must rearrange it as the lowest possible order (i.e., sorted in ascending order).  

## Example 1:  
Input: [1,2,3]  
Output: [1,3,2]  

**Approach: Three-Step Algorithm**  

**Key Insight:** To get the next larger permutation: (1) find the rightmost ascending pair, (2) swap with the smallest element larger than it on the right, (3) reverse the suffix to minimize it.  

**Algorithm:**  
1.  From right, find first i where nums\[i\] < nums\[i+1\] (descending break)      
2.  If found: find smallest j > i where nums\[j\] > nums\[i\], swap them      
3.  Reverse nums\[i+1:\] to get smallest suffix      
4.  If no i found (fully descending), reverse entire array      

**Example:** nums = \[1,2,3\]  
-   Find i: 2 < 3? No. 1 < 2? Yes, i = 1 (value 2)      
-   Find j: rightmost > 2 is index 2 (value 3)      
-   Swap: \[1,3,2\]      
-   Reverse suffix (just 2): \[1,3,2\] ✓      

**Example:** nums = \[3,2,1\] (max permutation)  
-   No ascending pair found      
-   Reverse all: \[1,2,3\] ✓      

**Why This Works:**  
-   We find the rightmost "small" element that can be made larger      
-   We increase it minimally (smallest larger element)      
-   We minimize the suffix (reverse descending → ascending)      

**Time Complexity:** O(n) - at most 3 passes **Space Complexity:** O(1) - in-place  
**Time: O(n)**  
**Space: O(1)**  

```
class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;

        // Step 1: Find largest i where nums[i] < nums[i+1]
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Step 2: Find largest j where nums[j] > nums[i]
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            // Step 3: Swap nums[i] and nums[j]
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

        // Step 4: Reverse the suffix after i
        int left = i + 1, right = n - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```
