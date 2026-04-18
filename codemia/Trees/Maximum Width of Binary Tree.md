# Maximum Width of Binary Tree
Given the root of a binary tree, return the maximum width of the given tree. The maximum width of a tree is the maximum width among all levels. The width of one level is defined as the length between the end-nodes (the leftmost and rightmost non-null nodes), where the null nodes between the end-nodes that would be present in a complete binary tree are also counted.

Example 1:
Input: [1,3,2,5,3,null,9]
Output: 4

**Approach: BFS with Position Tracking**

**Key Insight:** If we number nodes like a complete binary tree (root=0, left=2i, right=2i+1), the width at any level is simply rightmost\_position - leftmost\_position + 1.

**Position Formula (Heap Indexing):**

-   Root: position 0
    
-   Left child: 2 × parent\_pos
    
-   Right child: 2 × parent\_pos + 1
    

**Algorithm:**

1.  BFS level by level
    
2.  Track (node, position) for each node
    
3.  At each level: width = last\_pos - first\_pos + 1
    
4.  Track maximum width across all levels
    

**Position Normalization (Critical!):** Positions can grow exponentially (2^depth). To prevent overflow:

-   At each level, subtract the leftmost position from all positions
    
-   This keeps numbers small while preserving relative distances
    

**Example:** Tree = \[1,3,2,5,3,null,9\]

`Level 0: 1(pos=0) width = 0-0+1 = 1 Level 1: 3(0), 2(1) width = 1-0+1 = 2 Level 2: 5(0), 3(1), _, 9(3) width = 3-0+1 = 4`

Max width = 4 ✓

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(n) - queue stores at most one level

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        int maxWidth = 0;
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0));

        while (!queue.isEmpty()) {
            int size = queue.size();
            int left = queue.peek().getValue();
            int right = left;

            for (int i = 0; i < size; i++) {
                Pair<TreeNode, Integer> pair = queue.poll();
                TreeNode node = pair.getKey();
                int pos = pair.getValue() - left; // Normalize to prevent overflow

                right = pair.getValue();

                if (node.left != null) {
                    queue.offer(new Pair<>(node.left, 2 * pos));
                }
                if (node.right != null) {
                    queue.offer(new Pair<>(node.right, 2 * pos + 1));
                }
            }

            maxWidth = Math.max(maxWidth, right - left + 1);
        }

        return maxWidth;
    }
}
```
