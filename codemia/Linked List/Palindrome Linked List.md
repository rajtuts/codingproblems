# Palindrome Linked List
Given the head of a singly linked list, return true if it is a palindrome or false otherwise.

Example 1:
Input: [1,2,2,1]
Output: true

**Approach: Reverse Second Half and Compare**

**Key Insight:** Find the middle of the linked list, reverse the second half, then compare both halves node by node.

**Algorithm:**

1.  Find middle using slow/fast pointers
    
2.  Reverse the second half starting from [slow.next](http://slow.next)
    
3.  Compare first half with reversed second half
    
4.  (Optional) Restore the list by reversing again
    

**Example:** 1→2→2→1

-   Find middle: slow at 2 (first 2)
    
-   Reverse second half: 1→2→2←1 becomes 1→2 and 1→2
    
-   Compare: 1==1, 2==2 ✓
    
-   Result: True (palindrome)
    

**Example:** 1→2→3

-   Middle: 2
    
-   Reverse second half: 3 (single node)
    
-   Compare: 1≠3
    
-   Result: False
    

**Time Complexity:** O(n) - find middle + reverse + compare **Space Complexity:** O(1) - in-place reversal

**Time: O(n)**

**Space: O(1)**


```
class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode prev = null;
        while (slow != null) {
            ListNode tmp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = tmp;
        }

        ListNode left = head, right = prev;
        while (right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }
}
```
