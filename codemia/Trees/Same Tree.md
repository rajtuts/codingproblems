# Same Tree
Given the roots of two binary trees p and q, write a function to check if they are the same or not.

Example 1:
Input: {"p":[1,2,3],"q":[1,2,3]}
Output: true

**Approach: Recursive Tree Comparison**

**Key Insight:** Two trees are identical if:

1.  Their root values match
    
2.  Their left subtrees are identical
    
3.  Their right subtrees are identical
    

**Algorithm:**

`def isSameTree(p, q): # Base cases if not p and not q: return True # Both empty if not p or not q: return False # One empty if p.val != q.val: return False # Values differ # Recurse on children return isSameTree(p.left, q.left) and isSameTree(p.right, q.right)`

**Base Cases:**

-   Both None → True (empty trees are identical)
    
-   One None, one not → False (structural difference)
    
-   Values differ → False
    

**Visual Example:**

`Tree 1: 1 Tree 2: 1 / \ / \ 2 3 2 3`

Compare: 1==1 ✓, left subtrees match ✓, right subtrees match ✓ → True

**Iterative Alternative:** Use two queues for level-order comparison.

**Time: O(n)** where n = min nodes in either tree **Space: O(h)** for recursion stack, h = tree height

**Time: O(min(m,n))**

**Space: O(min(m,n))**

```
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null || p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```
