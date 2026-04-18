Serialize and Deserialize Binary Tree
Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work.

Example 1:
Input: {"root":[1,2,3,null,null,4,5]}
Output: [1,2,3,null,null,4,5]

**Approach: Preorder Traversal with Null Markers**

**Key Insight:** Preorder traversal (root, left, right) with explicit null markers allows unique reconstruction.

**Serialize:**

-   Visit each node in preorder
    
-   Append value + delimiter
    
-   Use "None" for null children
    
-   Result: "1,2,None,None,3,4,None,None,5,None,None"
    

**Deserialize:**

-   Split by delimiter, create iterator
    
-   Recursively build: root, left subtree, right subtree
    
-   "None" means return null
    

**Example:** \[1,2,3,null,null,4,5\]

`Serialize: 1 / \ 2 3 / \ 4 5 Preorder: "1,2,None,None,3,4,None,None,5,None,None," Deserialize: 1 → build left → 2 → None, None → back → build right → 3 → 4 → None, None → 5 → None, None`

**Why Preorder Works:**

-   First value is always root
    
-   Left subtree comes before right
    
-   Null markers preserve structure
    

**Alternative:** Level-order (BFS) serialization like LeetCode format

**Time Complexity:** O(n) for both operations **Space Complexity:** O(n) for the string/recursion

**Time: O(n)**

**Space: O(n)**

```
class Codec:
    def serialize(self, root):
        if not root: return "[]"
        res = []
        queue = collections.deque([root])
        while queue:
            node = queue.popleft()
            if node:
                res.append(str(node.val))
                queue.append(node.left)
                queue.append(node.right)
            else:
                res.append("null")
        return "[" + ",".join(res) + "]"

    def deserialize(self, data):
        if data == "[]": return None
        vals = data[1:-1].split(",")
        root = TreeNode(int(vals[0]))
        queue = collections.deque([root])
        i = 1
        while queue:
            node = queue.popleft()
            if i < len(vals) and vals[i] != "null":
                node.left = TreeNode(int(vals[i]))
                queue.append(node.left)
            i += 1
            if i < len(vals) and vals[i] != "null":
                node.right = TreeNode(int(vals[i]))
                queue.append(node.right)
            i += 1
        return root
```
