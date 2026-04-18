# Binary Tree Paths
Given the root of a binary tree, return all root-to-leaf paths in any order.

Example 1:
Input: {"root":[1,2,3,null,5]}
Output: ["1->2->5","1->3"]

DFS traversal building path strings. When reaching a leaf node, add the complete path to results.

Time: O(n)
Space: O(n)

```
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root != null) dfs(root, "", paths);
        return paths;
    }
    
    private void dfs(TreeNode root, String path, List<String> paths) {
        if (root.left == null && root.right == null) {
            paths.add(path + root.val);
        }
        if (root.left != null) {
            dfs(root.left, path + root.val + "->", paths);
        }
        if (root.right != null) {
            dfs(root.right, path + root.val + "->", paths);
        }
    }
}
```
