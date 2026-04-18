# Symmetric Tree
Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).

Example 1:
Input: {"root":[1,2,2,3,4,4,3]}
Output: true

**Approach: Recursive Mirror Check**

**Key Insight:** A tree is symmetric if the left subtree is a mirror of the right subtree. Two nodes are mirrors if their values match and their outer children mirror each other and their inner children mirror each other.

**Algorithm:**

1.  Define mirror(left, right):
    
    -   Both null: return True
        
    -   One null: return False
        
    -   Values differ: return False
        
    -   Return mirror(left.left, right.right) AND mirror(left.right, right.left)
        
2.  Return mirror(root.left, root.right)
    

**Example:** \[1,2,2,3,4,4,3\]

`1 / \ 2 2 / \ / \ 3 4 4 3`

-   mirror(2, 2): values match
    
-   mirror(3, 3) and mirror(4, 4): all match ✓
    

**Iterative Alternative:**

-   Use queue with pairs (left, right)
    
-   Process pairs, checking mirror conditions
    

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion depth = tree height

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return dfs(root.left, root.right);
    }
    
    private boolean dfs(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        
        return (left.val == right.val &&
            dfs(left.left, right.right) &&
            dfs(left.right, right.left));
    }
}
```
