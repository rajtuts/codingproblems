# Linked List Cycle
Given head, the head of a linked list, determine if the linked list has a cycle in it. A cycle exists if there is some node that can be reached again by continuously following the next pointer.

Example 1:
Input: {"head":[3,2,0,-4],"pos":1}
Output: true

**Approach: Floyd's Cycle Detection (Tortoise and Hare)**

**Key Insight:** If there's a cycle, a fast pointer (2 steps) will eventually catch up to a slow pointer (1 step) inside the cycle.

**Algorithm:**

1.  Initialize slow and fast pointers at head
    
2.  Move slow by 1 step, fast by 2 steps
    
3.  If fast reaches null → no cycle
    
4.  If fast meets slow → cycle exists
    

**Why It Works (Mathematical Proof):**

-   In a cycle of length C, fast gains 1 step per iteration
    
-   Fast will catch slow within C iterations
    
-   Like runners on a circular track - the faster one laps the slower
    

**Follow-up: Find Cycle Start** After detection, reset one pointer to head. Move both by 1 step. They meet at cycle start.

**Time/Space:** O(n) time, O(1) space - no extra data structures needed.

**Alternative Approach:** Hash set to store visited nodes

-   Simpler but O(n) space
    
-   Floyd's is preferred for O(1) space
    

**Time: O(n)**

**Space: O(1)**


```
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
````
