Path Sum II
Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node values in the path equals targetSum.

Example 1:
Input: {"root":[5,4,8,11,null,13,4,7,2,null,null,5,1],"targetSum":22}
Output: [[5,4,11,2],[5,8,4,5]]

**Approach: DFS with Path Tracking and Backtracking**

**Key Insight:** Use DFS to explore all root-to-leaf paths, maintaining the current path. When we reach a leaf with the correct sum, add a copy of the path to results.

**Algorithm:**

1.  DFS with parameters: node, remaining target, current path
    
2.  Add node.val to path
    
3.  If leaf and target == node.val: add path copy to result
    
4.  Recurse on left and right with reduced target
    
5.  Backtrack: remove node.val from path
    

**Example:** target = 22, tree with path 5→4→11→2

-   Visit 5: path=\[5\], target=22
    
-   Visit 4: path=\[5,4\], target=17
    
-   Visit 11: path=\[5,4,11\], target=13
    
-   Visit 2 (leaf): path=\[5,4,11,2\], 2==2 ✓
    
-   Add \[5,4,11,2\] to result
    

**Why Backtrack?**

-   We reuse the path list across all explorations
    
-   Must remove current node before exploring sibling branches
    

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion depth + path length

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum, new ArrayList<>());
        return res;
    }
    
    private void dfs(TreeNode root, int remaining, List<Integer> path) {
        if (root == null) return;
        
        path.add(root.val);
        
        if (root.left == null && root.right == null && remaining == root.val) {
            res.add(new ArrayList<>(path));
        }
        
        dfs(root.left, remaining - root.val, path);
        dfs(root.right, remaining - root.val, path);
        
        path.remove(path.size() - 1);
    }
}
```
