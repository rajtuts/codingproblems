# Sort List
Sort a linked list in O(n log n) time and O(1) memory space using merge sort.

Example 1:
Input: {"head":[4,2,1,3]}
Output: [1,2,3,4]

**Approach: Merge Sort on Linked List**

**Key Insight:** Merge sort is ideal for linked lists because: (1) finding middle with slow/fast is O(n), (2) merging two sorted lists is O(n), (3) no random access needed.

**Algorithm:**

1.  Base case: empty or single node returns as-is
    
2.  Find middle using slow/fast pointers
    
3.  Split list at middle (set [slow.next](http://slow.next) = None)
    
4.  Recursively sort both halves
    
5.  Merge sorted halves
    

**Example:** \[4,2,1,3\]

-   Split: \[4,2\] and \[1,3\]
    
-   Sort left \[4,2\]: split to \[4\] and \[2\], merge to \[2,4\]
    
-   Sort right \[1,3\]: split to \[1\] and \[3\], merge to \[1,3\]
    
-   Merge \[2,4\] and \[1,3\]:
    
    -   2>1: take 1 → \[1\]
        
    -   2<3: take 2 → \[1,2\]
        
    -   4>3: take 3 → \[1,2,3\]
        
    -   take 4 → \[1,2,3,4\] ✓
        

**Merge Function:**

-   Use dummy node, compare heads, take smaller
    
-   Attach remaining list when one exhausted
    

**Time Complexity:** O(n log n) - log n levels, O(n) work per level **Space Complexity:** O(log n) - recursion stack

**Time: O(n log n)**

**Space: O(log n)**

```
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (left != null && right != null) {
            if (left.val <= right.val) {
                curr.next = left;
                left = left.next;
            } else {
                curr.next = right;
                right = right.next;
            }
            curr = curr.next;
        }
        curr.next = left != null ? left : right;
        return dummy.next;
    }
}
```
