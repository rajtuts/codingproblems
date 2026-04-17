# Reverse Linked List
Given the head of a singly linked list, reverse the list, and return the reversed list.

Example 1:
Input: {"head":[1,2,3,4,5]}
Output: [5,4,3,2,1]

**Approach: Iterative Pointer Reversal**

**Key Insight:** We need three pointers to reverse links without losing references: prev, curr, and next.

**Algorithm:**

1.  Initialize: prev = None, curr = head
    
2.  While curr is not None:
    
    -   Save next node: next = curr.next
        
    -   Reverse the link: curr.next = prev
        
    -   Move pointers forward: prev = curr, curr = next
        
3.  Return prev (new head)
    

**Visual Walkthrough:** \[1\] → \[2\] → \[3\] → None

-   Step 1: None ← \[1\] \[2\] → \[3\] → None (prev=1, curr=2)
    
-   Step 2: None ← \[1\] ← \[2\] \[3\] → None (prev=2, curr=3)
    
-   Step 3: None ← \[1\] ← \[2\] ← \[3\] None (prev=3, curr=None)
    
-   Return prev (node 3)
    

**Alternative - Recursive:**

`def reverseList(head): if not head or not head.next: return head new_head = reverseList(head.next) head.next.next = head head.next = None return new_head`

Recursive uses O(n) stack space vs O(1) for iterative.

**Time: O(n)**

**Space: O(1)**

````
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode nxt = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nxt;
        }
        return prev;
    }
}
