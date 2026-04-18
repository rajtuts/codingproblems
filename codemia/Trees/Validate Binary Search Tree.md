Validate Binary Search Tree
Given the root of a binary tree, determine if it is a valid binary search tree (BST).

Example 1:
Input: {"root":[5,1,4,null,null,3,6]}
Output: false

**Approach: Recursive Range Validation**

**Key Insight:** For BST validity, it's not enough to check node > left and node < right. Each node must be within a valid RANGE based on its ancestors.

**Algorithm:**

1.  Start with range (-∞, +∞) at root
    
2.  For left child: update upper bound to parent's value
    
3.  For right child: update lower bound to parent's value
    
4.  At each node, verify: lower < node.val < upper
    

**Why Simple Comparison Fails:**

`5 / \ 1 4 ← 4 is less than 5 (ok locally) / \ 3 6 ← but 3 should be > 5 (invalid!)`

**Recursive Implementation:**

`def isValidBST(root, low=float('-inf'), high=float('inf')): if not root: return True if not (low < root.val < high): return False return (isValidBST(root.left, low, root.val) and isValidBST(root.right, root.val, high))`

**Alternative: In-order Traversal** BST in-order traversal yields sorted sequence. Track previous value and ensure current > previous.

**Time: O(n)**, **Space: O(h)** where h = tree height

**Time: O(n)**

**Space: O(h) where h is tree height**


```
class Solution {
    public boolean isValidBST(TreeNode root) {
        return valid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean valid(TreeNode node, long left, long right) {
        if (node == null) return true;
        if (!(node.val > left && node.val < right)) return false;
        return valid(node.left, left, node.val) && valid(node.right, node.val, right);
    }
}
```
