# Maximum Depth of Binary Tree
Given the root of a binary tree, return its maximum depth.

Example 1:
Input: {"root":[3,9,20,null,null,15,7]}
Output: 3

**Approach: Recursive DFS**

**Key Insight:** The depth of a tree = 1 + max(depth of left subtree, depth of right subtree).

**Recursive Solution:**

`def maxDepth(root): if not root: return 0 return 1 + max(maxDepth(root.left), maxDepth(root.right))`

**Base Case:** Empty tree (None) has depth 0.

**Visual Example:**

`3 ← depth 1 / \ 9 20 ← depth 2 / \ 15 7 ← depth 3`

maxDepth = 1 + max(depth(9), depth(20)) = 1 + max(1, 2) = 3

**Iterative BFS Alternative:** Count the number of levels using level-order traversal:

`from collections import deque if not root: return 0 queue = deque([root]) depth = 0 while queue: depth += 1 for _ in range(len(queue)): node = queue.popleft() if node.left: queue.append(node.left) if node.right: queue.append(node.right) return depth`

**Time: O(n)** - visit each node once **Space: O(h)** for recursion, O(w) for BFS where w = max width

**Time: O(n)**

**Space: O(h) where h is height**

```
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
````
