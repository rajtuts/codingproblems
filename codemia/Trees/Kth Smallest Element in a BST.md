Kth Smallest Element in a BST
Given the root of a binary search tree, and an integer k, return the kth smallest value (1-indexed) of all the values of the nodes in the tree.

Example 1:
Input: {"root":[3,1,4,null,2],"k":1}
Output: 1

**Approach: In-Order Traversal**

**Key Insight:** In a BST, in-order traversal (left, root, right) visits nodes in ascending order. The kth node visited is the kth smallest.

**Algorithm (Iterative):**

1.  Use stack to simulate recursion
    
2.  Go left as far as possible, pushing nodes to stack
    
3.  Pop node, decrement k
    
4.  If k == 0: found the answer
    
5.  Go right and repeat
    

**Example:** BST \[3,1,4,null,2\], k = 1

-   Push 3, then push 1 (left of 3)
    
-   Pop 1, k = 0 → return 1 ✓
    

**Recursive Version:**

`def inorder(node): if node: inorder(node.left) result.append(node.val) inorder(node.right) return result[k-1]`

**Follow-up Optimization:**

-   If tree is modified frequently, augment nodes with subtree sizes
    
-   Allows O(h) lookup without full traversal
    

**Time Complexity:** O(h + k) - go down tree + process k nodes **Space Complexity:** O(h) - stack depth

**Time: O(h + k)**

**Space: O(h)**

````
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        
        while (!stack.isEmpty() || curr != null) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            k--;
            if (k == 0) return curr.val;
            curr = curr.right;
        }
        return 0;
    }
}
```
