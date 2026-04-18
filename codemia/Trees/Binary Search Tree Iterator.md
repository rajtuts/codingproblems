Binary Search Tree Iterator
Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST). The class should have a constructor that takes the root of a BST, a next() method that returns the next smallest number, and a hasNext() method that returns whether there is a next number.

Example 1:
Input: {"tree":[7,3,15,null,null,9,20],"operations":["next","next","hasNext","next","hasNext","next","hasNext","next","hasNext"]}
Output: [3,7,true,9,true,15,true,20,false]

Use stack to store left spine. On next(), pop and push right child's left spine.

Time: O(1) amortized for next(), O(h) for hasNext()
Space: O(h) where h is tree height

```
class BSTIterator {
    private Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
        pushLeft(root);
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    public int next() {
        TreeNode node = stack.pop();
        pushLeft(node.right);
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
```
