# Find All Duplicates in an Array
Given an integer array nums of length n where all the integers are in the range [1, n] and each integer appears once or twice, return an array of all the integers that appear twice. You must write an algorithm that runs in O(n) time and uses only constant extra space.

## Example 1:  
Input: [4,3,2,7,8,2,3,1]  
Output: [2,3]  

**Approach: Negation as Visited Marker**    
**Key Insight:** Since values are in range \[1, n\] and array has n elements, we can use the array itself as a hash table. Use the value as an index, and negate to mark as "seen".  
**Algorithm:**  
1.  For each number num:    
    -   Calculate index = |num| - 1 (absolute value because it might be negated)       
    -   If nums\[index\] is already negative → we've seen this number before (duplicate!)        
    -   Otherwise, negate nums\[index\] to mark as seen        
2.  Return all duplicates found     

**Example:** \[4,3,2,7,8,2,3,1\]  
`num=4: index=3, nums[3]=7>0, negate → [4,3,2,-7,8,2,3,1]`  
`num=3: index=2, nums[2]=2>0, negate → [4,3,-2,-7,8,2,3,1]`  
`num=2: index=1, nums[1]=3>0, negate → [4,-3,-2,-7,8,2,3,1]`   
`num=-7: index=6, nums[6]=3>0, negate → [4,-3,-2,-7,8,2,-3,1]`   
`num=8: index=7, nums[7]=1>0, negate → [4,-3,-2,-7,8,2,-3,-1]`   
`num=2: index=1, nums[1]=-3<0, DUPLICATE! Add 2`   
`num=-3: index=2, nums[2]=-2<0, DUPLICATE! Add 3`   
`num=-1: index=0, nums[0]=4>0, negate → [-4,-3,-2,-7,8,2,-3,-1] Result: [2, 3] ✓`
**Why This Works:**  
-   Each number maps to unique index (value-1)    
-   First visit: negate to mark      
-   Second visit: already negative = duplicate      

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - reusing input array (excluding output)  
**Time: O(n)**  
**Space: O(1)**

```
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;

            if (nums[index] < 0) {
                result.add(index + 1);
            } else {
                nums[index] = -nums[index];
            }
        }

        return result;
    }
}
```
