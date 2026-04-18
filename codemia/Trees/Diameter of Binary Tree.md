# Diameter of Binary Tree
Given the root of a binary tree, return the length of the diameter of the tree.

Example 1:
Input: {"root":[1,2,3,4,5]}
Output: 3

**Approach: DFS with Diameter Tracking**

**Key Insight:** The diameter passing through any node equals left\_depth + right\_depth. We compute depth recursively while tracking the maximum diameter seen.

**Algorithm:**

1.  Define depth function returning height of subtree
    
2.  At each node:
    
    -   Compute left and right depths
        
    -   Update max\_diameter = max(max\_diameter, left + right)
        
    -   Return 1 + max(left, right) for parent's calculation
        
3.  Return max\_diameter
    

**Example:** tree \[1,2,3,4,5\]

`1 / \ 2 3 / \ 4 5`

-   At 4: left=0, right=0, diameter=0, return 1
    
-   At 5: left=0, right=0, diameter=0, return 1
    
-   At 2: left=1, right=1, diameter=2, return 2
    
-   At 3: left=0, right=0, diameter=0, return 1
    
-   At 1: left=2, right=1, diameter=3 ← maximum!
    
-   Result: 3 ✓
    

**Note:** Diameter doesn't necessarily pass through root.

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(h) - recursion depth

**Time: O(n)**

**Space: O(h)**

```
class Solution:
    def diameterOfBinaryTree(self, root: Optional[TreeNode]) -> int:
        self.res = 0
        
        def dfs(curr):
            if not curr:
                return 0
            
            left = dfs(curr.left)
            right = dfs(curr.right)
            
            self.res = max(self.res, left + right)
            return 1 + max(left, right)
            
        dfs(root)
        return self.res
```
