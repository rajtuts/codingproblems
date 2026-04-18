# Flatten Binary Tree to Linked List
Given the root of a binary tree, flatten the tree into a "linked list": The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null. The "linked list" should be in the same order as a pre-order traversal of the binary tree.

Example 1:
Input: {"root":[1,2,5,3,4,null,6]}
Output: [1,null,2,null,3,null,4,null,5,null,6]

**Approach: Post-order DFS with Tail Tracking**

**Key Insight:** Flatten recursively: flatten left subtree, flatten right subtree, then attach flattened left between root and flattened right. Return the tail of the flattened list.

**Algorithm:**

1.  Base case: if null or leaf, return node
    
2.  Recursively flatten left → get left\_tail
    
3.  Recursively flatten right → get right\_tail
    
4.  If left exists:
    
    -   left\_tail.right = node.right (attach right subtree)
        
    -   node.right = node.left (move left to right)
        
    -   node.left = null (clear left)
        
5.  Return right\_tail if exists, else left\_tail
    

**Example:** tree \[1,2,5,3,4,null,6\]

`1 1 / \ \ 2 5 → 2 / \ \ \ 3 4 6 3 \ 4 \ 5 \ 6`

-   Flatten left \[2,3,4\] → 2→3→4 (tail=4)
    
-   Flatten right \[5,6\] → 5→6 (tail=6)
    
-   Connect: 4.right = 5, 1.right = 2, 1.left = null
    
-   Result: 1→2→3→4→5→6 ✓
    

**Pre-order Result:** The flattened list follows pre-order: root → left subtree → right subtree

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion stack

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode runner = curr.left;
                while (runner.right != null) {
                    runner = runner.right;
                }
                
                runner.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
            curr = curr.right;
        }
    }
}
```
