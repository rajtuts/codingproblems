# Rotate Array
Given an integer array nums, rotate the array to the right by k steps, where k is non-negative. Do it in-place with O(1) extra space.

## Example 1:
Input: {"nums":[1,2,3,4,5,6,7],"k":3}  
Output: [5,6,7,1,2,3,4]  

**Approach: Reversal Algorithm**
The reversal algorithm is an elegant O(1) space solution.
**Key Insight:** Rotating right by k is equivalent to:
1.  Reverse the entire array
2.  Reverse the first k elements
3.  Reverse the remaining n-k elements
**Example: \[1,2,3,4,5,6,7\], k=3**
`Original: [1, 2, 3, 4, 5, 6, 7] Reverse all: [7, 6, 5, 4, 3, 2, 1] Reverse 0..2: [5, 6, 7, 4, 3, 2, 1] Reverse 3..6: [5, 6, 7, 1, 2, 3, 4] ← Result!`
**Why it works:**
-   Reversing all puts elements in opposite order
-   Reversing first k "un-reverses" them, putting the last k elements at the front
-   Reversing the rest fixes the original first (n-k) elements
**Edge Cases:**
-   k > n: Use k = k % n (rotating by n is a full cycle)
-   k = 0: No rotation needed
**Time Complexity:** O(n) - each element moved twice **Space Complexity:** O(1) - in-place reversal
**Time: O(n)**   **Space: O(1)**  
```
class Solution {
    public void rotate(int[] nums, int k) {
        k = k % nums.length;

        reverse(nums, 0, nums.length - 1);  // Reverse all
        reverse(nums, 0, k - 1);             // Reverse first k
        reverse(nums, k, nums.length - 1);   // Reverse rest
    }

    private void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
            l++; r--;
        }
    }
}
```
