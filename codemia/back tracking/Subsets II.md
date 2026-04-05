# Subsets II  
Given an integer array nums that may contain duplicates, return all possible subsets (the power set). The solution set must not contain duplicate subsets. Return the solution in any order.  

## Example 1:  
Input: [1,2,2]  
Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]  

**Approach: Backtracking with Duplicate Skipping**

**Key Insight:** Similar to Subsets I, but array may contain duplicates. Sort first, then skip same elements at the same recursion level to avoid duplicate subsets.

**Algorithm:**  
1.  Sort nums to group duplicates      
2.  Backtrack from each starting position      
3.  At each step, add current subset to result      
4.  For each element from start:      
    -   Skip if same as previous at same level (i > start and nums\[i\] == nums\[i-1\])          
    -   Add to path, recurse with i+1, remove from path          

**Example:** nums = \[1,2,2\]  
-   Start: \[\] added      
-   Add 1: \[1\], recurse      
    -   Add 2: \[1,2\], recurse        
        -   Add 2: \[1,2,2\] ✓              
    -   Skip second 2 at this level (duplicate)        
-   Add 2: \[2\], recurse      
    -   Add 2: \[2,2\] ✓          
-   Skip second 2 at top level      

**Result:** \[\[\],\[1\],\[1,2\],\[1,2,2\],\[2\],\[2,2\]\]  
**Time Complexity:** O(n × 2^n) - 2^n subsets, O(n) to copy each **Space Complexity:** O(n) - recursion depth  
**Time: O(n \* 2^n)**  
**Space: O(n)**  

```
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        backtrack(0, nums, new ArrayList<>(), res);
        return res;
    }
    
    private void backtrack(int i, int[] nums, List<Integer> subset, List<List<Integer>> res) {
        if (i == nums.length) {
            res.add(new ArrayList<>(subset));
            return;
        }
        
        subset.add(nums[i]);
        backtrack(i + 1, nums, subset, res);
        subset.remove(subset.size() - 1);
        
        while (i + 1 < nums.length && nums[i] == nums[i + 1]) {
            i++;
        }
        backtrack(i + 1, nums, subset, res);
    }
}
```
