# Binary Tree Maximum Path Sum
A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root. The path sum is the sum of the node's values in the path. Given the root of a binary tree, return the maximum path sum of any non-empty path.

Example 1:
Input: {"root":[-10,9,20,null,null,15,7]}
Output: 42

**Approach: DFS with Two Returns**

**Key Insight:** At each node, compute two things:

1.  **Path through node** (can use both children) → update global max
    
2.  **Path to return** (can only use one child) → for parent's path
    

**Algorithm:**

1.  Recursively get max\_gain from left and right children
    
2.  Ignore negative gains (max with 0)
    
3.  path\_sum = node.val + left\_gain + right\_gain (path through node)
    
4.  Update global max\_sum
    
5.  Return node.val + max(left\_gain, right\_gain) to parent
    

**Example:** \[-10, 9, 20, null, null, 15, 7\]

`-10 / \ 9 20 / \ 15 7 At node 20: left_gain=15, right_gain=7 path_sum = 20 + 15 + 7 = 42 ← max path! return 20 + max(15, 7) = 35 to parent At node -10: left_gain=9, right_gain=35 path_sum = -10 + 9 + 35 = 34 < 42 (global max stays 42)`

**Why Two Different Values?**

-   Path THROUGH node: can "turn" at this node (use both children)
    
-   Path TO parent: can only extend one branch (no turning)
    

**Negative Path Handling:** max(child\_gain, 0) → don't include negative paths

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion depth (height of tree)

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    int res;
    public int maxPathSum(TreeNode root) {
        res = Integer.MIN_VALUE;
        dfs(root);
        return res;
    }
    
    public int dfs(TreeNode root) {
        if (root == null) return 0;
        
        int leftMax = Math.max(dfs(root.left), 0);
        int rightMax = Math.max(dfs(root.right), 0);
        
        res = Math.max(res, root.val + leftMax + rightMax);
        
        return root.val + Math.max(leftMax, rightMax);
    }
}
```
