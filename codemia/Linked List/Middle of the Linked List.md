Middle of the Linked List
Given the head of a singly linked list, return the middle node of the linked list. If there are two middle nodes, return the second middle node.

Example 1:
Input: [1,2,3,4,5]
Output: [3,4,5]

**Approach: Fast and Slow Pointers (Tortoise and Hare)**

**Key Insight:** If slow moves 1 step while fast moves 2 steps, when fast reaches the end, slow is at the middle (fast travels 2x the distance).

**Algorithm:**

1.  Initialize both pointers at head
    
2.  Move slow by 1, fast by 2 in each iteration
    
3.  Stop when fast reaches end (fast is null or fast.next is null)
    
4.  Return slow (it's at the middle)
    

**Why It Works:**

-   Fast travels 2x the speed of slow
    
-   When fast finishes n steps, slow has done n/2 steps
    
-   n/2 is exactly the middle position
    

**Example:** \[1,2,3,4,5\] (odd length)

`Start: slow=1, fast=1 Step1: slow=2, fast=3 Step2: slow=3, fast=5 Step3: fast.next=null, stop Return slow=3 ✓`

**Example:** \[1,2,3,4,5,6\] (even length)

`Start: slow=1, fast=1 Step1: slow=2, fast=3 Step2: slow=3, fast=5 Step3: slow=4, fast=null Return slow=4 (second middle) ✓`

**Time Complexity:** O(n) - single traversal **Space Complexity:** O(1) - only two pointers

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
```
