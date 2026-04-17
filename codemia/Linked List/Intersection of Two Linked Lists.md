# Intersection of Two Linked Lists
Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return null.

Example 1:
Input: {"listA":[4,1,8,4,5],"listB":[5,6,1,8,4,5],"skipA":2,"skipB":3}
Output: [8,4,5]

**Approach: Two Pointers with Path Swapping**

**Key Insight:** By having each pointer traverse both lists (switching to the other list when reaching the end), they travel equal total distances and meet at the intersection.

**Why It Works:**

-   Pointer A travels: a\_only + shared + b\_only + shared
    
-   Pointer B travels: b\_only + shared + a\_only + shared
    
-   Same total distance! They align at the intersection
    

**Algorithm:**

1.  Start pointer a at headA, pointer b at headB
    
2.  Move each pointer one step at a time
    
3.  When a reaches end, redirect to headB (and vice versa)
    
4.  They meet at intersection (or both null if no intersection)
    

**Example:** A: 4→1→8→4→5, B: 5→6→1→8→4→5 (intersect at 8)

`a: 4→1→8→4→5→(switch)→5→6→1→8 b: 5→6→1→8→4→5→(switch)→4→1→8 Both at 8! ✓`

**No Intersection Case:**

`a: ...→null→(switch to B)→...→null b: ...→null→(switch to A)→...→null Both null simultaneously`

**Mathematical Proof:**

-   len(A) = a + c, len(B) = b + c (c = shared portion)
    
-   Path A then B: a + c + b = a + b + c
    
-   Path B then A: b + c + a = a + b + c
    
-   Equal! Meet at node c (or null)
    

**Time Complexity:** O(m + n) - each list traversed at most twice **Space Complexity:** O(1) - only two pointers

**Time: O(m + n)**

**Space: O(1)**

```
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b) {
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }
        return a;
    }
}
```
