# Binary Tree Level Order Traversal
Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).

Example 1:
Input: {"root":[3,9,20,null,null,15,7]}
Output: [[3],[9,20],[15,7]]

**Approach: BFS with Level Tracking**

**Key Insight:** BFS naturally processes nodes level by level. We just need to track where one level ends and the next begins.

**Algorithm:**

1.  Initialize queue with root
    
2.  While queue not empty:
    
    -   Get current level size (before adding children)
        
    -   Process exactly that many nodes
        
    -   Add all children to queue
        
    -   Append level values to result
        

`from collections import deque def levelOrder(root): if not root: return [] result = [] queue = deque([root]) while queue: level_size = len(queue) level_values = [] for _ in range(level_size): node = queue.popleft() level_values.append(node.val) if node.left: queue.append(node.left) if node.right: queue.append(node.right) result.append(level_values) return result`

**Visual Example:**

`3 → Level 0: [3] / \ 9 20 → Level 1: [9, 20] / \ 15 7 → Level 2: [15, 7]`

**DFS Alternative:** Use recursion with depth parameter:

`def dfs(node, depth, result): if not node: return if depth == len(result): result.append([]) result[depth].append(node.val) dfs(node.left, depth + 1, result) dfs(node.right, depth + 1, result)`

**Time: O(n)**, **Space: O(n)** for queue/result

**Time: O(n)**

**Space: O(n)**


```
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        if (root != null) q.add(root);
        
        while (!q.isEmpty()) {
            int len = q.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            res.add(level);
        }
        return res;
    }
}
```
