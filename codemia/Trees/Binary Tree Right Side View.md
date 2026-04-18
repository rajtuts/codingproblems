# Binary Tree Right Side View
Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

Example 1:
Input: {"root":[1,2,3,null,5,null,4]}
Output: [1,3,4]

BFS level order traversal. For each level, the last node processed is the rightmost visible node.

Time: O(n)
Space: O(n)

```
class Solution {
    public int[] rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        
        while (!q.isEmpty()) {
            TreeNode rightSide = null;
            int qLen = q.size();
            
            for (int i = 0; i < qLen; i++) {
                TreeNode node = q.poll();
                if (node != null) {
                    rightSide = node;
                    q.offer(node.left);
                    q.offer(node.right);
                }
            }
            if (rightSide != null) res.add(rightSide.val);
        }
        return res.stream().mapToInt(i -> i).toArray();
    }
}

```
