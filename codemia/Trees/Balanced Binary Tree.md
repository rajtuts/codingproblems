Balanced Binary Tree
Given a binary tree, determine if it is height-balanced. A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.

Example 1:
Input: {"root":[3,9,20,null,null,15,7]}
Output: true

**Approach: DFS with Early Termination**

**Key Insight:** A height-balanced tree has all subtrees where left and right heights differ by at most 1. Compute height recursively, but return -1 immediately if any subtree is unbalanced.

**Algorithm:**

1.  Define height(node):
    
    -   If null: return 0
        
    -   left\_h = height(left)
        
    -   If left\_h == -1: return -1 (left unbalanced)
        
    -   right\_h = height(right)
        
    -   If right\_h == -1: return -1 (right unbalanced)
        
    -   If |left\_h - right\_h| > 1: return -1 (this node unbalanced)
        
    -   Return 1 + max(left\_h, right\_h)
        
2.  Return height(root) != -1
    

**Example:** Balanced tree \[3,9,20,null,null,15,7\]

`3 / \ 9 20 / \ 15 7`

-   height(9) = 1, height(20) = 2, |1-2| = 1 ≤ 1 ✓
    
-   Tree is balanced
    

**Example:** Unbalanced tree \[1,2,2,3,3,null,null,4,4\]

`1 / \ 2 2 / \ 3 3 / \ 4 4`

-   height(4) = 1, height(3) = 2
    
-   height(2) = 3, height(right 2) = 1
    
-   |3-1| = 2 > 1 → return -1, unbalanced ✗
    

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion stack depth

**Time: O(n)**

**Space: O(h)**

```
class Solution:
    def isBalanced(self, root: Optional[TreeNode]) -> bool:
        def dfs(root):
            if not root:
                return [True, 0]
            
            left, right = dfs(root.left), dfs(root.right)
            balanced = (left[0] and right[0] and 
                       abs(left[1] - right[1]) <= 1)
            
            return [balanced, 1 + max(left[1], right[1])]
            
        return dfs(root)[0]
```
