# Rotate List
Given the head of a linked list, rotate the list to the right by k places.

Example 1:
Input: {"head":[1,2,3,4,5],"k":2}
Output: [4,5,1,2,3]

**Approach: Find New Tail and Reconnect**

**Key Insight:** Rotating right by k is equivalent to moving the last k nodes to the front. The key is finding where to "cut" the list.

**Algorithm:**

1.  Find length and tail (single traversal)
    
2.  Calculate effective k = k % length (handle k > length)
    
3.  Find new tail at position (length - k - 1)
    
4.  Reconnect: new\_[tail.next](http://tail.next) = None, [tail.next](http://tail.next) = head
    

**Example:** \[1,2,3,4,5\], k = 2

-   Length = 5, k = 2 % 5 = 2
    
-   New tail position = 5 - 2 - 1 = 2 (node with value 3)
    
-   Before: 1→2→3→4→5→None
    
-   After: 4→5→1→2→3→None
    

**Visual:**

`Original: 1 → 2 → 3 → 4 → 5 ↑new_tail ↑tail Cut here: 1 → 2 → 3 and 4 → 5 Reconnect: 4 → 5 → 1 → 2 → 3 → None ↑new_head ↑new_tail`

**Edge Cases:**

-   Empty list or single node → return as-is
    
-   k = 0 or k % length = 0 → no rotation needed
    

**Time Complexity:** O(n) - two passes maximum **Space Complexity:** O(1) - only pointer variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Find length and tail
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Calculate actual rotation
        k = k % length;
        if (k == 0) return head;

        // Find new tail (length - k - 1 steps from head)
        ListNode newTail = head;
        for (int i = 0; i < length - k - 1; i++) {
            newTail = newTail.next;
        }

        // Rotate
        ListNode newHead = newTail.next;
        newTail.next = null;
        tail.next = head;

        return newHead;
    }
}
```
