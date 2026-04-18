Recover Binary Search Tree
Two nodes of a BST are swapped by mistake. Recover the tree without changing its structure.

Example 1:
Input: {"root":[1,3,null,null,2]}
Output: [3,1,null,null,2]

**Approach: In-Order Traversal to Find Swapped Nodes**

**Key Insight:** In a valid BST, in-order traversal gives sorted values. Two swapped nodes create violations where prev > curr. Find both violating nodes and swap their values back.

**Algorithm:**

1.  Track first, second (swapped nodes), and prev (previous in traversal)
    
2.  In-order traverse:
    
    -   If prev.val > curr.val (violation):
        
        -   If first is None: first = prev (first violation)
            
        -   Always: second = curr (could be second violation)
            
3.  After traversal: swap first.val and second.val
    

**Example:** BST \[1,3,null,null,2\] (3 and 1 are swapped)

-   In-order: 2, 3, 1 (should be 1, 2, 3)
    
-   At 3→2: no violation
    
-   At 2→1: violation! first=2, second=1 (wait, this is wrong node)
    
-   Actually: first=3 (3>2 violation), second=1 (2>1 violation)
    

**Two Cases:**

1.  Adjacent nodes swapped: one violation (first=prev, second=curr)
    
2.  Non-adjacent nodes swapped: two violations (first=first prev, second=last curr)
    

**Time Complexity:** O(n) - traverse all nodes **Space Complexity:** O(h) - recursion stack

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    TreeNode first, second, prev;
    public void recoverTree(TreeNode root) {
        inorder(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }
    private void inorder(TreeNode node) {
        if (node == null) return;
        inorder(node.left);
        if (prev != null && prev.val > node.val) {
            if (first == null) first = prev;
            second = node;
        }
        prev = node;
        inorder(node.right);
    }
}
```
