# First Missing Positive  
Given an unsorted integer array nums, return the smallest missing positive integer. Must run in O(n) time and O(1) auxiliary space.  

## Example 1:  
Input: [3,4,-1,1]  
Output: 2  

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
**Space: O(1)**  

```
class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        int i = 0;
        while (i < n) {
            int correctIdx = nums[i] - 1;
            if (nums[i] > 0 && nums[i] <= n && nums[i] != nums[correctIdx]) {
                int temp = nums[i];
                nums[i] = nums[correctIdx];
                nums[correctIdx] = temp;
            } else {
                i++;
            }
        }
        for (i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
```
