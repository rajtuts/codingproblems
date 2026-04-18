# Minimum Depth of Binary Tree
Given a binary tree, find its minimum depth. The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

Example 1:
Input: {"root":[3,9,20,null,null,15,7]}
Output: 2

**Approach: BFS Level Order**

**Key Insight:** BFS explores level by level. The first leaf node encountered is guaranteed to be at the minimum depth. This is more efficient than DFS for minimum depth.

**Algorithm:**

1.  If empty tree: return 0
    
2.  Initialize queue with (root, 1)
    
3.  Process level by level:
    
    -   Dequeue (node, depth)
        
    -   If leaf (no children): return depth immediately
        
    -   Enqueue children with depth + 1
        
4.  Return depth of first leaf found
    

**Example:** tree \[3,9,20,null,null,15,7\]

`3 (depth 1) / \ 9 20 (depth 2) / \ 15 7 (depth 3)`

-   Process 3 (depth 1): has children, enqueue 9, 20
    
-   Process 9 (depth 2): leaf! Return 2 ✓
    

**Example:** tree \[2,null,3,null,4\]

`2 (depth 1) \ 3 (depth 2) \ 4 (depth 3)`

-   Process 2: has right child
    
-   Process 3: has right child
    
-   Process 4: leaf! Return 3 ✓
    

**Why BFS over DFS:**

-   DFS might go down a long path before finding minimum
    
-   BFS guarantees first leaf is shallowest
    

**Time Complexity:** O(n) - visit nodes until first leaf **Space Complexity:** O(n) - queue at widest level

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int depth = 1;
        
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                
                if (node.left == null && node.right == null) {
                    return depth;
                }
                
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            depth++;
        }
        
        return 0;
    }
}
```
