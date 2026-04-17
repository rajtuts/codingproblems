Add Two Numbers
Given two non-empty linked lists representing two non-negative integers stored in reverse order, add them and return the sum as a linked list.

Example 1:
Input: {"l1":[2,4,3],"l2":[5,6,4]}
Output: [7,0,8]


**Approach: Elementary Math with Carry**

**Key Insight:** Since digits are stored in reverse order, we can add them directly from left to right (least significant first), just like manual addition.

**Algorithm:**

1.  Use dummy node to simplify result list construction
    
2.  While either list has nodes OR there's a carry:
    
    -   Sum = carry + l1.val (if exists) + l2.val (if exists)
        
    -   New digit = sum % 10
        
    -   New carry = sum // 10
        
    -   Advance pointers
        

**Example:** l1 = \[2,4,3\] (342), l2 = \[5,6,4\] (465)

`Step 1: 2 + 5 = 7, carry = 0 → [7] Step 2: 4 + 6 = 10, digit = 0, carry = 1 → [7,0] Step 3: 3 + 4 + 1 = 8, carry = 0 → [7,0,8] Result: 342 + 465 = 807 ✓`

**Handling Different Lengths:**

`l1 = [9,9], l2 = [1] → 99 + 1 = 100 Step 1: 9 + 1 = 10 → [0], carry = 1 Step 2: 9 + 0 + 1 = 10 → [0,0], carry = 1 Step 3: 0 + 0 + 1 = 1 → [0,0,1]`

**Why Dummy Node?** Avoids special handling for the first node creation.

**Time Complexity:** O(max(m, n)) - process longer list **Space Complexity:** O(max(m, n)) - result list length

**Time: O(max(m,n))**

**Space: O(max(m,n))**

````
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;

            int total = val1 + val2 + carry;
            carry = total / 10;
            curr.next = new ListNode(total % 10);
            curr = curr.next;

            l1 = (l1 != null) ? l1.next : null;
            l2 = (l2 != null) ? l2.next : null;
        }

        return dummy.next;
    }
}
````
