# Zigzag Level Order Traversal
Given the root of a binary tree, return the zigzag level order traversal of its nodes values. (i.e., from left to right, then right to left for the next level and alternate between).

Example 1:
Input: {"root":[3,9,20,null,null,15,7]}
Output: [[3],[20,9],[15,7]]

**Approach: BFS with Direction Toggle**

**Key Insight:** Standard level-order traversal with a twist: track direction and reverse the level list on odd-indexed levels (1, 3, 5...).

**Algorithm:**

1.  BFS with queue, track left\_to\_right boolean
    
2.  For each level:
    
    -   Collect all nodes at current level
        
    -   If left\_to\_right: add level as-is
        
    -   If right\_to\_left: add reversed level
        
    -   Toggle direction
        
3.  Return result
    

**Example:** tree \[3,9,20,null,null,15,7\]

`3 / \ 9 20 / \ 15 7`

-   Level 0 (L→R): \[3\]
    
-   Level 1 (R→L): \[20,9\] (reversed from \[9,20\])
    
-   Level 2 (L→R): \[15,7\]
    
-   Result: \[\[3\],\[20,9\],\[15,7\]\] ✓
    

**Alternative: Deque for O(1) Append**

-   Use appendleft/append based on direction
    
-   Avoids reversing entire level
    

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(n) - queue and result

**Time: O(n)**

**Space: O(n)**

```
class Solution:
    def zigzagLevelOrder(self, root: Optional[TreeNode]) -> List[List[int]]:
        res = []
        q = deque([root] if root else [])
        
        while q:
            level = []
            for i in range(len(q)):
                node = q.popleft()
                level.append(node.val)
                if node.left:
                    q.append(node.left)
                if node.right:
                    q.append(node.right)
            
            if len(res) % 2 == 1:
                level.reverse()
                
            res.append(level)
            
        return res
```
