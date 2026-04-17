Linked List Cycle II
Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.

Example 1:
Input: {"values":[3,2,0,-4],"cyclePos":1}
Output: node at index 1

**Approach: Floyd's Tortoise and Hare**

**Key Insight:** Treat array values as pointers to next index. If there's a duplicate, there's a cycle. Use Floyd's cycle detection to find the cycle entrance.

**Algorithm:**

1.  Phase 1: Find cycle intersection
    
    -   Slow moves 1 step: slow = nums\[slow\]
        
    -   Fast moves 2 steps: fast = nums\[nums\[fast\]\]
        
    -   When they meet, cycle exists
        
2.  Phase 2: Find cycle entrance (the duplicate)
    
    -   Reset slow to start
        
    -   Both move 1 step until they meet
        
    -   Meeting point is the duplicate
        

**Example:** nums = \[1,3,4,2,2\]

-   Phase 1: slow=1→3→2→4→2, fast=1→4→2→4→2, meet at 2
    
-   Phase 2: slow=0→1→3→2, slow2=2→4→2, meet at 2
    
-   Duplicate: 2 ✓
    

**Why It Works:**

-   Array forms a linked list (value at i points to index nums\[i\])
    
-   Duplicate means two indices point to same location → cycle
    
-   Cycle entrance is the repeated value
    

**Time Complexity:** O(n) - each pointer traverses at most n steps **Space Complexity:** O(1) - only two pointers

**Time: O(n)**

**Space: O(1)**

```
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }
}
````
