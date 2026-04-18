# Populating Next Right Pointers
You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Example 1:
Input: {"root":[1,2,3,4,5,6,7]}
Output: [1,#,2,3,#,4,5,6,7,#]

**Approach: Level-by-Level Using Existing Pointers**

**Key Insight:** Since it's a perfect binary tree, use already-established next pointers at level N to connect nodes at level N+1. No queue needed—O(1) space.

**Algorithm:**

1.  Start with leftmost node of each level
    
2.  While leftmost has left child:
    
    -   Traverse level using next pointers
        
    -   Connect children: [left.next](http://left.next) = right
        
    -   Connect across parents: [right.next](http://right.next) = next\_parent.left
        
    -   Move to next level: leftmost = leftmost.left
        
3.  Return root
    

**Example:**

`1 1 → null / \ / \ 2 3 → 2 → 3 → null / \ / \ / \ / \ 4 5 6 7 4→5→6→7 → null`

**Why O(1) Space:**

-   Use existing next pointers to traverse levels
    
-   No queue or recursion stack needed
    

**Time Complexity:** O(n) - visit each node once **Space Complexity:** O(1) - only using existing pointers

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public Node connect(Node root) {
        if (root == null) return null;
        
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int length = queue.size();
            for (int i = 0; i < length; i++) {
                Node node = queue.poll();
                
                if (i < length - 1) {
                    node.next = queue.peek();
                }
                
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        
        return root;
    }
}
```
