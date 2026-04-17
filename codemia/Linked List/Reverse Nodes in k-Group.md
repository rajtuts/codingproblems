# Reverse Nodes in k-Group
Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.

Example 1:
Input: {"head":[1,2,3,4,5],"k":2}
Output: [2,1,4,3,5]

**Approach: Iterative Group Reversal**

**Key Insight:** Process the list in groups of k nodes. For each group, reverse the k nodes in-place and connect it to the previous reversed group.

**Algorithm:**

1.  Count total nodes to know how many complete groups exist
    
2.  Use a dummy node before head for easier edge case handling
    
3.  For each group of k nodes:
    
    -   Reverse k nodes using iterative reversal
        
    -   Connect reversed group to previous part
        
    -   Move pointers to next group
        
4.  Incomplete groups (< k nodes) remain unchanged
    

**Example:** \[1,2,3,4,5\], k=2

-   Group 1: Reverse \[1,2\] → \[2,1\], list = \[2,1,3,4,5\]
    
-   Group 2: Reverse \[3,4\] → \[4,3\], list = \[2,1,4,3,5\]
    
-   Remaining \[5\] stays as is
    
-   Result: \[2,1,4,3,5\] ✓
    

**Key Variables:**

-   group\_prev: Points to node before current group
    
-   kth: Points to last node of current group (becomes first after reversal)
    
-   group\_next: Points to first node of next group
    

**Time Complexity:** O(n) - each node visited twice (once to count, once to reverse) **Space Complexity:** O(1) - only pointer manipulation, no recursion

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode groupPrev = dummy;
        
        while (true) {
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) break;
            ListNode groupNext = kth.next;
            
            // Reverse group
            ListNode prev = kth.next;
            ListNode curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }
            
            ListNode tmp = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = tmp;
        }
        return dummy.next;
    }
    
    private ListNode getKth(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }
}
````
