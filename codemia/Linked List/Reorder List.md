# Reorder List
You are given the head of a singly linked-list. The list can be represented as: L0 → L1 → … → Ln - 1 → Ln. Reorder the list to be on the following form: L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …

Example 1:
Input: {"head":[1,2,3,4]}
Output: [1,4,2,3]

**Approach: Three Steps - Find Middle, Reverse, Merge**

**Key Insight:** Break into three subproblems: (1) find middle of list, (2) reverse second half, (3) merge two halves by alternating nodes.

**Algorithm:**

1.  Find middle using slow/fast pointers
    
2.  Reverse second half:
    
    -   Start from slow, reverse links
        
3.  Merge two halves alternating:
    
    -   first = head, second = reversed half
        
    -   Interleave: first→second→first→second→...
        

**Example:** 1→2→3→4

-   Find middle: slow at 3
    
-   Reverse 3→4 to 4→3
    
-   Merge: 1→4→2→3 ✓
    

**Example:** 1→2→3→4→5

-   Find middle: slow at 3
    
-   Reverse 3→4→5 to 5→4→3
    
-   Merge: 1→5→2→4→3 ✓
    

**Detailed Merge:**

-   first=1, second=4
    
-   [1.next](http://1.next)\=4, move first to 2
    
-   [4.next](http://4.next)\=2, move second to 3
    
-   [2.next](http://2.next)\=3 ([second.next](http://second.next) is None, stop)
    

**Time Complexity:** O(n) - each step is O(n) **Space Complexity:** O(1) - in-place operations

**Time: O(n)**

**Space: O(1)**

````
class Solution {
    public void reorderList(ListNode head) {
        if (head == null) return;
        
        // Find middle
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Reverse second half
        ListNode second = slow.next;
        slow.next = null;
        ListNode prev = null;
        while (second != null) {
            ListNode tmp = second.next;
            second.next = prev;
            prev = second;
            second = tmp;
        }
        
        // Merge
        ListNode first = head;
        second = prev;
        while (second != null) {
            ListNode tmp1 = first.next;
            ListNode tmp2 = second.next;
            first.next = second;
            second.next = tmp1;
            first = tmp1;
            second = tmp2;
        }
    }
}
````
