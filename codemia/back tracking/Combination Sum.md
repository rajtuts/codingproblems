# Combination Sum  
Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target.  

## Example 1:  
Input: {"candidates":[2,3,6,7],"target":7}  
Output: [[2,2,3],[7]]  

** Backtracking: try including each candidate (can reuse) and recursively find remaining sum.** 

**Time: O(n^(target/min))  
Space: O(target/min)**  

```
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(0, new ArrayList<>(), 0, candidates, target, res);
        return res;
    }
    
    private void dfs(int i, List<Integer> cur, int total, int[] candidates, int target, List<List<Integer>> res) {
        if (total == target) {
            res.add(new ArrayList<>(cur));
            return;
        }
        if (i >= candidates.length || total > target) {
            return;
        }
        
        cur.add(candidates[i]);
        dfs(i, cur, total + candidates[i], candidates, target, res);
        cur.remove(cur.size() - 1);
        dfs(i + 1, cur, total, candidates, target, res);
    }
}
```
