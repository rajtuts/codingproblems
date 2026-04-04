# Find All Numbers Disappeared in an Array
Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums. You must write an algorithm that runs in O(n) time and uses only constant extra space.  

## Example 1:  
Input: [4,3,2,7,8,2,3,1]  
Output: [5,6]  

**Approach: Cyclic Sort (In-Place Hash Table)**  
**Key Insight:** The answer must be in range \[1, n+1\]. Use the array itself as a hash table where index i stores value i+1.  

**Algorithm:**  
1.  For each position i, swap nums\[i\] to its correct position (nums\[i\]-1) while it's a valid positive number      
2.  Scan array: first position where nums\[i\] != i+1 is the answer      
3.  If all positions correct, answer is n+1      

**Example:** nums = \[3,4,-1,1\]  
`i=0: nums[0]=3 → swap to index 2 [3,4,-1,1] → [-1,4,3,1] nums[0]=-1 (invalid, stop) i=1: nums[1]=4 → swap to index 3 [-1,4,3,1] → [-1,1,3,4] nums[1]=1 → swap to index 0 [-1,1,3,4] → [1,-1,3,4] nums[1]=-1 (invalid, stop) i=2: nums[2]=3 (already correct) i=3: nums[3]=4 (already correct) Scan: [1,-1,3,4] i=0: nums[0]=1 ✓ i=1: nums[1]=-1 ≠ 2 → return 2 ✓`  

**Why O(n)?** Each number is swapped at most once to its final position. Even with nested while loop, total swaps ≤ n.  
**Time Complexity:** O(n) - at most 2n operations **Space Complexity:** O(1) - in-place rearrangement  
**Time: O(n)**  
**Space: O(1)Approach: In-Place Marking**  
**Key Insight:** Since all values are in range \[1, n\], use array indices as markers. For each value v, mark index v-1 as visited by negating it. Collect indices that weren't marked (still positive).  

**Algorithm:** 
1.  For each number num:      
    -   index = |num| - 1 (use absolute value)          
    -   If nums\[index\] > 0: negate it (mark as seen)          
2.  Collect all indices where nums\[i\] > 0      
3.  Return these indices + 1 (convert back to 1-indexed)      

**Example:** nums = \[4,3,2,7,8,2,3,1\]  
-   Process each: negate at indices 3,2,1,6,7,1,2,0      
-   After marking: \[-4,-3,-2,-7,8,2,-3,-1\]      
-   Positive at indices 4,5 → missing numbers 5,6 ✓      

**Why This Works:**  
-   Each number "checks in" at its corresponding index      
-   Numbers that never check in → their indices stay positive      
-   Those are the missing numbers      

**Time Complexity:** O(n) - two passes **Space Complexity:** O(1) - in-place modification  
**Time: O(n)**  
**Space: O(1)**  

```
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();

        // Phase 1: Mark visited numbers by negating value at index (num - 1)
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }

        // Phase 2: Collect indices with positive values (missing numbers)
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }

        return result;
    }
}
```
