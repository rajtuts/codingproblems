# Odd Even Linked List
Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return the reordered list. The first node is considered odd, and the second node is even, and so on.

Example 1:
Input: [1,2,3,4,5]
Output: [1,3,5,2,4]

**Approach: Two Pointers Building Separate Lists**

**Key Insight:** Build two separate chains (odd-indexed and even-indexed nodes) simultaneously, then connect them.

**Algorithm:**

1.  Initialize odd pointer at node 1, even pointer at node 2
    
2.  Save even\_head (to connect later)
    
3.  Alternate: odd.next = even.next, move odd; even.next = odd.next, move even
    
4.  Connect odd tail to even\_head
    

**Visual Example:** \[1,2,3,4,5\]

`Initial: 1 → 2 → 3 → 4 → 5 ↑odd ↑even (save as even_head) Step 1: odd.next = 3, move odd to 3 1 → 3 2 → 4 → 5 ↑odd ↑even Step 2: even.next = 4, move even to 4 1 → 3 → 5 2 → 4 ↑odd ↑even Step 3: even.next = null (5.next), even = null, loop ends Connect: odd.next = even_head 1 → 3 → 5 → 2 → 4 → null`

**Loop Condition:** `while even and even.next`

-   Ensures we have at least 2 more nodes to process
    
-   Handles both odd and even length lists
    

**Time Complexity:** O(n) - single pass through list **Space Complexity:** O(1) - only pointer variables, in-place rewiring

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) return null;

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;
        return head;
    }
}
```
