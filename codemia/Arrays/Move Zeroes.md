# Move Zeroes  
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements. Note that you must do this in-place without making a copy of the array.  

## Example 1:  
Input: [0,1,0,3,12]  
Output: [1,3,12,0,0]  

**Approach: Two Pointers (Swap)**  
Use a write pointer to track where the next non-zero element should go.  
**Algorithm:**  
1.  Initialize insert\_pos = 0      
2.  Iterate through array with index i:      
    -   If nums\[i\] != 0:          
        -   Swap nums\[insert\_pos\] and nums\[i\]              
        -   Increment insert\_pos              
3.  All zeros naturally end up at the end      

**Visual Example: \[0,1,0,3,12\]**    
`i=0: nums[0]=0, skip i=1: nums[1]=1≠0, swap(0,1) → [1,0,0,3,12], insert_pos=1 i=2: nums[2]=0, skip i=3: nums[3]=3≠0, swap(1,3) → [1,3,0,0,12], insert_pos=2 i=4: nums[4]=12≠0, swap(2,4) → [1,3,12,0,0], insert_pos=3`  
**Why Swap Works:**   
-   Non-zeros move to front sequentially      
-   Zeros are implicitly pushed to back      
-   Maintains relative order of non-zeros     
**Alternative (Two Pass):**  
1.  First pass: copy non-zeros to front  
2.  Second pass: fill remaining with zeros But swapping is more elegant.    
**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - in-place
**Time: O(n)**  **Space: O(1)**  
```
class Solution {
    public void moveZeroes(int[] nums) {
        int insertPos = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[insertPos];
                nums[insertPos] = nums[i];
                nums[i] = temp;
                insertPos++;
            }
        }
    }
}
```
