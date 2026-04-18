# Invert Binary Tree
Given the root of a binary tree, invert the tree, and return its root.

Example 1:
Input: {"root":[4,2,7,1,3,6,9]}
Output: [4,7,2,9,6,3,1]

**Approach: Recursive DFS**

**Key Insight:** To invert a tree, swap the left and right children at every node, recursively.

**Algorithm:**

`def invertTree(root): if not root: return None # Swap children root.left, root.right = root.right, root.left # Recursively invert subtrees invertTree(root.left) invertTree(root.right) return root`

**Visual Example:**

`Before: 4 After: 4 / \ / \ 2 7 → 7 2 / \ / \ / \ / \ 1 3 6 9 9 6 3 1`

**Order of Operations:**

-   Pre-order: swap first, then recurse (shown above)
    
-   Post-order: recurse first, then swap (also works)
    
-   In-order: swap, recurse left, swap back, recurse right (confusing, avoid)
    

**Iterative BFS Alternative:**

`queue = deque([root]) while queue: node = queue.popleft() if node: node.left, node.right = node.right, node.left queue.extend([node.left, node.right])`

**Time: O(n)**, **Space: O(h)** for recursion or O(w) for BFS

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
    }
}
```
