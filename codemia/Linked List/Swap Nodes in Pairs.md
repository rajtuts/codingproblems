# Swap Nodes in Pairs
Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the nodes.

Example 1:
Input: [1,2,3,4]
Output: [2,1,4,3]

**Approach: Iterative Pointer Manipulation with Dummy Node**

**Key Insight:** Use a dummy node before head to simplify edge cases. For each pair, we need to rewire 3 pointers: prev→second, second→first, first→rest.

**Algorithm:**

1.  Create dummy node pointing to head
    
2.  Use `prev` pointer to track node before current pair
    
3.  For each pair (first, second):
    
    -   prev.next = second (prev now points to second)
        
    -   first.next = second.next (first points to rest of list)
        
    -   second.next = first (second points to first)
        
    -   prev = first (move prev to end of swapped pair)
        

**Visual Example:** \[1,2,3,4\]

`Initial: dummy → 1 → 2 → 3 → 4 prev first second Step 1: dummy → 2 → 1 → 3 → 4 (prev.next = second) prev second first Step 2: Move prev to 1, repeat for (3,4) dummy → 2 → 1 → 4 → 3 prev`

**Why Dummy Node?** It handles the case where we need to change the head pointer (when swapping first two nodes).

**Time Complexity:** O(n) - single pass through list **Space Complexity:** O(1) - only pointer variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;

            prev.next = second;
            first.next = second.next;
            second.next = first;
            prev = first;
        }

        return dummy.next;
    }
}
```
