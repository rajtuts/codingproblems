Convert Sorted Array to Binary Search Tree
Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.

Example 1:
Input: {"nums":[-10,-3,0,5,9]}
Output: [0,-3,9,-10,null,5]

**Approach: Divide and Conquer**

**Key Insight:** For a height-balanced BST, the middle element of the sorted array should be the root. Recursively apply this to left and right halves for subtrees.

**Algorithm:**

1.  Base case: empty range → return None
    
2.  Find middle index: mid = (left + right) // 2
    
3.  Create node with nums\[mid\] as root
    
4.  Recursively build:
    
    -   Left subtree from nums\[left:mid\]
        
    -   Right subtree from nums\[mid+1:right+1\]
        
5.  Return root
    

**Example:** nums = \[-10,-3,0,5,9\]

-   mid = 2, root = 0
    
-   Left subtree from \[-10,-3\]: mid=0, root=-10, right child=-3
    
-   Right subtree from \[5,9\]: mid=0 (of subarray), root=5, right child=9
    

`0 / \ -10 5 \ \ -3 9`

**Why Middle Element:**

-   Ensures equal number of nodes on left and right (±1)
    
-   Guarantees height-balanced tree
    

**Time Complexity:** O(n) - visit each element once **Space Complexity:** O(log n) - recursion depth for balanced tree

**Time: O(n)**

**Space: O(log n)**

```
class Solution {
    int[] nums;
    public TreeNode sortedArrayToBST(int[] nums) {
        this.nums = nums;
        return build(0, nums.length - 1);
    }
    private TreeNode build(int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = build(left, mid - 1);
        node.right = build(mid + 1, right);
        return node;
    }
}
```
