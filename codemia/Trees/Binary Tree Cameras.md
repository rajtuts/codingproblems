@ Binary Tree Cameras
You are given the root of a binary tree. We install cameras on the tree nodes where each camera at a node can monitor its parent, itself, and its immediate children. Return the minimum number of cameras needed to monitor all nodes of the tree.

Example 1:
Input: {"root":[0,0,null,0,0]}
Output: 1

Greedy post-order DFS. If child not covered, place camera here. If child has camera, we're covered. Process leaves up to minimize cameras.

Time: O(n)
Space: O(h)
Solution Code

```
class Solution {
    int cameras = 0;

    public int minCameraCover(TreeNode root) {
        // States: 0 = not covered, 1 = covered, 2 = has camera
        if (dfs(root) == 0) cameras++;
        return cameras;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 1;

        int left = dfs(node.left);
        int right = dfs(node.right);

        if (left == 0 || right == 0) {
            cameras++;
            return 2;
        }

        if (left == 2 || right == 2) {
            return 1;
        }

        return 0;
    }
}
```
