# Construct Binary Tree from Preorder and Inorder
Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.

Example 1:
Input: {"preorder":[3,9,20,15,7],"inorder":[9,3,15,20,7]}
Output: [3,9,20,null,null,15,7]

**Approach: Recursive Divide and Conquer**

**Key Insight:**

-   Preorder: root is always FIRST
    
-   Inorder: root divides left and right subtrees
    

**Algorithm:**

1.  Root = first element of preorder
    
2.  Find root's position in inorder (gives left subtree size)
    
3.  Recursively build left subtree with corresponding preorder/inorder slices
    
4.  Recursively build right subtree with remaining elements
    

**Example:** preorder = \[3,9,20,15,7\], inorder = \[9,3,15,20,7\]

`Root = 3 (first in preorder) Find 3 in inorder at index 1: Left inorder: [9] (1 element) Right inorder: [15,20,7] (3 elements) Left preorder: [9] (next 1 element) Right preorder: [20,15,7] (remaining 3) Recurse on both sides...`

**Visual:**

`preorder: [3, 9, 20,15,7] ↑root ↑left ↑right subtree inorder: [9, 3, 15,20,7] left ↑root right`

**Optimization:** Use hash map for O(1) root lookup in inorder:

`index_map = {val: i for i, val in enumerate(inorder)}`

**Time Complexity:** O(n²) naive, O(n) with hash map **Space Complexity:** O(n) for recursion and array slices

**Time: O(n²)**

**Space: O(n)**

```
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(0, 0, inorder.length - 1, preorder, inorder);
    }
    
    public TreeNode helper(int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
        if (preStart > preorder.length - 1 || inStart > inEnd) return null;
        
        TreeNode root = new TreeNode(preorder[preStart]);
        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == root.val) {
                inIndex = i;
            }
        }
        
        root.left = helper(preStart + 1, inStart, inIndex - 1, preorder, inorder);
        root.right = helper(preStart + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder);
        return root;
    }
}
```
