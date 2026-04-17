# Remove Nth Node From End of List
Given the head of a linked list, remove the nth node from the end of the list and return its head.

Example 1:
Input: {"head":[1,2,3,4,5],"n":2}
Output: [1,2,3,5]

**Approach: Two Pointers with Gap**

**Key Insight:** Maintain a gap of n+1 between two pointers. When the first reaches the end, the second is just before the node to remove.

**Algorithm:**

1.  Create dummy node (handles removing head)
    
2.  Move `first` pointer n+1 steps from dummy
    
3.  Move both pointers until `first` reaches null
    
4.  `second.next` is the node to remove → skip it
    

**Why n+1 Steps?** We want `second` to stop at the node BEFORE the one to remove, so we can update `second.next`.

**Example:** \[1,2,3,4,5\], n = 2

`After dummy: dummy → 1 → 2 → 3 → 4 → 5 → null Move first n+1=3 steps: dummy → 1 → 2 → 3 → 4 → 5 → null ↑second ↑first Move both until first = null: dummy → 1 → 2 → 3 → 4 → 5 → null ↑second ↑first Remove second.next (4): second.next = second.next.next Result: [1,2,3,5] ✓`

**Edge Case:** Removing head

-   dummy → 1 → null, n = 1
    
-   first moves past null, second stays at dummy
    
-   dummy.next = 1.next = null → removes head
    

**Time Complexity:** O(L) - single pass through list **Space Complexity:** O(1) - only pointer variables

**Time: O(L)**

**Space: O(1)**

```
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode left = dummy;
        ListNode right = head;
        
        while (n > 0 && right != null) {
            right = right.next;
            n--;
        }
        
        while (right != null) {
            left = left.next;
            right = right.next;
        }
        
        left.next = left.next.next;
        return dummy.next;
    }
}
```
