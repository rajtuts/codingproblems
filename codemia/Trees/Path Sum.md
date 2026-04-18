@ Path Sum
Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such that adding up all the values along the path equals targetSum.

Example 1:
Input: {"root":[5,4,8,11,null,13,4,7,2,null,null,null,1],"targetSum":22}
Output: true

**Approach: DFS with Target Reduction**

**Key Insight:** Recursively subtract each node's value from the target sum. At a leaf node, if the remaining target equals the node's value, we've found a valid path.

**Algorithm:**

1.  Base case: null node → False
    
2.  Leaf node (no children): return targetSum == node.val
    
3.  Recursive: check left or right subtree with reduced target
    

**Example:** target = 22, path 5→4→11→2

-   At 5: remaining = 22 - 5 = 17
    
-   At 4: remaining = 17 - 4 = 13
    
-   At 11: remaining = 13 - 11 = 2
    
-   At 2 (leaf): 2 == 2 ✓
    

**Common Mistake:**

-   Checking sum at non-leaf nodes (path must end at leaf)
    
-   Not handling null nodes properly
    

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion depth = tree height

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
}
```
```
