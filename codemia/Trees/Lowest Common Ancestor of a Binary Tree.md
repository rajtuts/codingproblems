Lowest Common Ancestor of a Binary Tree
Find the lowest common ancestor (LCA) of two nodes in a binary tree. The LCA is the lowest node that has both p and q as descendants.

Example 1:
Input: {"root":[3,5,1,6,2,0,8,null,null,7,4],"p":5,"q":1}
Output: 3

**Approach: Recursive Search**

**Key Insight:** If we find p and q in different subtrees of a node, that node is the LCA. If both are in the same subtree, the LCA is in that subtree.

**Algorithm:**

1.  Base cases: null → return null; root == p or root == q → return root
    
2.  Recursively search left and right subtrees
    
3.  If both return non-null: current root is LCA
    
4.  If only one returns non-null: return that result
    

**Example:** Find LCA of 5 and 1 in tree \[3,5,1,6,2,0,8\]

-   At 3: search left (finds 5), search right (finds 1)
    
-   Both non-null → 3 is LCA ✓
    

**Example:** Find LCA of 5 and 4 in same tree

-   At 3: left returns 5 (found both 5 and 4 in left subtree)
    
-   Right returns null
    
-   Return left result: 5 is LCA ✓
    

**Time Complexity:** O(n) - visit each node at most once **Space Complexity:** O(h) - recursion depth

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    int p, q;
    public int lowestCommonAncestor(TreeNode root, int p, int q) {
        this.p = p;
        this.q = q;
        return dfs(root).val;
    }
    private TreeNode dfs(TreeNode node) {
        if (node == null) return null;
        if (node.val == p || node.val == q) return node;
        TreeNode left = dfs(node.left);
        TreeNode right = dfs(node.right);
        if (left != null && right != null) return node;
        return left != null ? left : right;
    }
}
```
