# Count Good Nodes in Binary Tree
Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a value greater than X. Return the number of good nodes in the binary tree.

Example 1:
Input: {"root":[3,1,4,3,null,1,5]}
Output: 4

**Approach: DFS with Path Maximum**

**Key Insight:** A node is "good" if no ancestor has a greater value. Track the maximum value seen on the path from root to current node.

**Algorithm:**

1.  DFS from root with max\_value = root.val
    
2.  At each node:
    
    -   If node.val >= max\_value: count it as good
        
    -   Update max\_value = max(max\_value, node.val)
        
    -   Recurse on children with updated max
        
3.  Return total count
    

**Example:** root = \[3,1,4,3,null,1,5\]

-   3 (root): max=3, good ✓
    
-   1: max=3, 1<3, not good
    
-   4: max=3, 4≥3, good ✓
    
-   3: max=3, 3≥3, good ✓
    
-   5: max=4, 5≥4, good ✓
    
-   Total: 4
    

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion stack

**Time: O(n)**

**Space: O(h)**

```
class Solution:
    def goodNodes(self, root: TreeNode) -> int:
        def dfs(node, maxVal):
            if not node:
                return 0
            
            res = 1 if node.val >= maxVal else 0
            maxVal = max(maxVal, node.val)
            res += dfs(node.left, maxVal)
            res += dfs(node.right, maxVal)
            return res
            
        return dfs(root, root.val)
```
