# Remove Linked List Elements
Given the head of a linked list and an integer val, remove all the nodes of the linked list that has Node.val == val, and return the new head.

Example 1:
Input: {"head":[1,2,6,3,4,5,6],"val":6}
Output: [1,2,3,4,5]

**Approach: Dummy Node with Two Pointers**

**Key Insight:** Use a dummy node to handle the case where the head needs to be removed. Use prev and curr pointers to traverse, skipping nodes with target value.

**Algorithm:**

1.  Create dummy node pointing to head
    
2.  Set prev = dummy, curr = head
    
3.  While curr is not null:
    
    -   If curr.val == val: skip by setting [prev.next](http://prev.next) = [curr.next](http://curr.next)
        
    -   Else: move prev to curr
        
    -   Move curr to [curr.next](http://curr.next)
        
4.  Return [dummy.next](http://dummy.next)
    

**Example:** head = \[1,2,6,3,4,5,6\], val = 6

-   dummy → 1 → 2 → 6 → 3 → 4 → 5 → 6
    
-   curr=1: 1≠6, prev=1
    
-   curr=2: 2≠6, prev=2
    
-   curr=6: 6==6, [prev.next](http://prev.next)\=3 (skip 6)
    
-   curr=3: 3≠6, prev=3
    
-   curr=4: 4≠6, prev=4
    
-   curr=5: 5≠6, prev=5
    
-   curr=6: 6==6, [prev.next](http://prev.next)\=null (skip 6)
    
-   Result: \[1,2,3,4,5\] ✓
    

**Why Dummy Node:**

-   Simplifies head removal (no special case)
    
-   prev always points to valid node
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - only two pointers

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        return dummy.next;
    }
}
````
