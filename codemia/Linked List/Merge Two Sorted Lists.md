# Merge Two Sorted Lists
You are given the heads of two sorted linked lists list1 and list2. Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.

Example 1:
Input: {"list1":[1,2,4],"list2":[1,3,4]}
Output: [1,1,2,3,4,4]

**Approach: Two Pointers with Dummy Head**

**Key Insight:** Since both lists are sorted, we can build the merged list by always choosing the smaller of the two current elements.

**Algorithm:**

1.  Create a dummy node to simplify edge cases
    
2.  Maintain a tail pointer starting at dummy
    
3.  While both lists have elements:
    
    -   Compare current elements of both lists
        
    -   Attach the smaller one to [tail.next](http://tail.next)
        
    -   Move that list's pointer forward
        
    -   Move tail forward
        
4.  Attach remaining elements (one list may still have nodes)
    
5.  Return [dummy.next](http://dummy.next)
    

**Why Dummy Node:** Eliminates special case for empty result. We don't need to track the head separately.

**Visual Example:** list1 = \[1,2,4\], list2 = \[1,3,4\]

-   Compare 1 vs 1: pick list1's 1 → merged: \[1\]
    
-   Compare 2 vs 1: pick list2's 1 → merged: \[1,1\]
    
-   Compare 2 vs 3: pick 2 → merged: \[1,1,2\]
    
-   Compare 4 vs 3: pick 3 → merged: \[1,1,2,3\]
    
-   Compare 4 vs 4: pick list1's 4 → merged: \[1,1,2,3,4\]
    
-   Attach remaining: 4 → merged: \[1,1,2,3,4,4\]
    

**Time: O(n + m)**

**Space: O(1)**

````
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        
        if (list1 != null) tail.next = list1;
        else if (list2 != null) tail.next = list2;
        
        return dummy.next;
    }
}
````
