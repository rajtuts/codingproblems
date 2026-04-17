# Combination Sum II
Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target. Each number in candidates may only be used once in the combination.

## Example 1: 
Input: {"candidates":[10,1,2,7,6,1,5],"target":8}  
Output: [[1,1,6],[1,2,5],[1,7],[2,6]]  

**Approach: Backtracking with Duplicate Skipping**

**Key Insight:** Unlike Combination Sum I where elements can be reused, each element can only be used once. Sort first, then skip duplicate elements at the same recursion level to avoid duplicate combinations.

**Algorithm:**  
1.  Sort candidates to group duplicates together      
2.  Backtrack with parameters: start index, remaining target, current path      
3.  If target == 0: add path to results      
4.  For each candidate from start:      
    -   Skip if same as previous at same level (i > start and nums\[i\] == nums\[i-1\])          
    -   If candidate > target: break (pruning)          
    -   Add to path, recurse with i+1, remove from path          

**Example:** candidates = \[10,1,2,7,6,1,5\], target = 8  
-   Sorted: \[1,1,2,5,6,7,10\]      
-   \[1,1,6\]=8 ✓      
-   \[1,2,5\]=8 ✓      
-   \[1,7\]=8 ✓      
-   \[2,6\]=8 ✓     

**Why Skip Duplictes:**  
-   At same recursion level, using same value twice creates duplicate combinations      
-   But at different levels (deeper recursion), same value is allowed      

**Time Complexity:** O(2^n) - potentially all subsets **Space Complexity:** O(n) - recursion depth  
**Time: O(2^n)**  
**Space: O(n)**  

```
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(0, new ArrayList<>(), 0, candidates, target, res);
        return res;
    }
    
    private void dfs(int i, List<Integer> cur, int total, int[] candidates, int target, List<List<Integer>> res) {
        if (total == target) {
            res.add(new ArrayList<>(cur));
            return;
        }
        if (total > target || i == candidates.length) {
            return;
        }
        
        cur.add(candidates[i]);
        dfs(i + 1, cur, total + candidates[i], candidates, target, res);
        cur.remove(cur.size() - 1);
        
        while (i + 1 < candidates.length && candidates[i] == candidates[i + 1]) {
            i++;
        }
        dfs(i + 1, cur, total, candidates, target, res);
    }
}
```
