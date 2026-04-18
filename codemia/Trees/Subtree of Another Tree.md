# Subtree of Another Tree
Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same structure and node values of subRoot and false otherwise.

Example 1:
Input: {"root":[3,4,5,1,2],"subRoot":[4,1,2]}
Output: true

**Approach: DFS with Same-Tree Helper**

**Key Insight:** For each node in the main tree, check if the subtree rooted there matches subRoot using a same-tree comparison. If any node matches, return true.

**Algorithm:**

1.  Define is\_same(p, q):
    
    -   Both null: return True
        
    -   One null: return False
        
    -   Values differ: return False
        
    -   Return is\_same(p.left, q.left) AND is\_same(p.right, q.right)
        
2.  Main function:
    
    -   If root is null: return False
        
    -   If is\_same(root, subRoot): return True
        
    -   Return isSubtree(root.left, subRoot) OR isSubtree(root.right, subRoot)
        

**Example:** root = \[3,4,5,1,2\], subRoot = \[4,1,2\]

`Main tree: SubRoot: 3 4 / \ / \ 4 5 1 2 / \ 1 2`

-   is\_same(3, 4)? No (3 ≠ 4)
    
-   is\_same(4, 4)? 4==4, check children
    
    -   is\_same(1, 1)? Yes
        
    -   is\_same(2, 2)? Yes
        
    -   Return True ✓
        

**Optimization:**

-   Can use tree serialization or hashing for O(m+n) time
    
-   String matching (KMP) on serialized trees
    

**Time Complexity:** O(m × n) - check each node, each check is O(n) **Space Complexity:** O(h) - recursion depth of main tree

**Time: O(m\*n)**

**Space: O(h)**


```
class Solution {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) return true;
        if (root == null) return false;
        
        if (isSameTree(root, subRoot)) return true;
        
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }
    
    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null || p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```
