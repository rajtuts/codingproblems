@ Sum of Left Leaves
Given the root of a binary tree, return the sum of all left leaves. A leaf is a node with no children. A left leaf is a leaf that is the left child of another node.

Example 1:
Input: [3,9,20,null,null,15,7]
Output: 24

**Approach: DFS with Parent-Child Awareness**

**Key Insight:** A "left leaf" must satisfy TWO conditions:

1.  It's a LEAF (no children)
    
2.  It's a LEFT child of its parent
    

We can only determine if a node is a "left child" from the parent's perspective.

**Algorithm:**

1.  At each node, check if left child exists AND is a leaf
    
2.  If yes: add its value (don't recurse into it)
    
3.  If no: recurse into left subtree
    
4.  Always recurse into right subtree
    

**Example:** Tree \[3,9,20,null,null,15,7\]

`3 / \ 9 20 / \ 15 7 - At 3: left child 9 is a leaf → add 9 - At 3: recurse right to 20 - At 20: left child 15 is a leaf → add 15 - At 20: right child 7 is NOT a left child Total: 9 + 15 = 24 ✓`

**Why Check from Parent?** A node itself doesn't know if it's a left or right child. Only the parent knows which side it came from.

**Alternative (Pass Flag):**

`def dfs(node, is_left): if not node: return 0 if not node.left and not node.right and is_left: return node.val return dfs(node.left, True) + dfs(node.right, False)`

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion stack depth (h = tree height)

**Time: O(n)**

**Space: O(h)**

```
class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;

        int sum = 0;
        if (root.left != null) {
            if (root.left.left == null && root.left.right == null) {
                sum += root.left.val; // Left leaf found
            } else {
                sum += sumOfLeftLeaves(root.left);
            }
        }
        sum += sumOfLeftLeaves(root.right);

        return sum;
    }
}
```
