# Lowest Common Ancestor of a BST
Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.

Example 1:
Input: {"root":[6,2,8,0,4,7,9,null,null,3,5],"p":2,"q":8}
Output: 6

**Approach: BST Property**

**Key Insight:** In a BST, if p and q are on different sides of a node (one smaller, one larger), that node is their LCA. If both are smaller, go left. If both are larger, go right.

**Algorithm:**

1.  Start at root
    
2.  While searching:
    
    -   If both p,q < root: go left (both in left subtree)
        
    -   If both p,q > root: go right (both in right subtree)
        
    -   Else: root is LCA (p,q split here)
        
3.  Return root
    

**Example:** root = \[6,2,8,...\], p=2, q=8

-   At 6: p=2<6, q=8>6 → split occurs here
    
-   Return 6
    

**Example:** p=2, q=4 (both in left subtree)

-   At 6: both < 6 → go left
    
-   At 2: p=2≤2, q=4>2 → split at 2
    
-   Return 2
    

**Time Complexity:** O(h) - traverse down tree **Space Complexity:** O(1) - iterative, no extra space

**Time: O(h)**

**Space: O(1)**

```
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode cur = root;
        
        while (cur != null) {
            if (p.val > cur.val && q.val > cur.val) {
                cur = cur.right;
            } else if (p.val < cur.val && q.val < cur.val) {
                cur = cur.left;
            } else {
                return cur;
            }
        }
        return null;
    }
}
```
