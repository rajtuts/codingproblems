# Copy List with Random Pointer
A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null. Construct a deep copy of the list.

Example 1:
Input: [[7,-1],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]


**Approach: Hash Map for Node Mapping**

**Key Insight:** The random pointer can point to any node, so we need a way to map original nodes to their copies. Use a hash map: first pass creates all copy nodes, second pass sets the pointers.

**Algorithm:**

1.  First pass: create copy nodes
    
    -   For each original node, create copy with same value
        
    -   Store mapping: old\_node → new\_node
        
2.  Second pass: set pointers
    
    -   For each original node:
        
        -   [copy.next](http://copy.next) = mapping\[[original.next](http://original.next)\]
            
        -   copy.random = mapping\[original.random\]
            
3.  Return mapping\[head\]
    

**Example:** 7 → 13 → 11 → 10 → 1 (with random pointers)

-   Pass 1: Create 7', 13', 11', 10', 1'
    
-   Pass 2: 7'.next = 13', 7'.random = None 13'.next = 11', 13'.random = 7' ... etc.
    

**Alternative: Interleaving (O(1) space)**

-   Insert copies between originals: 7→7'→13→13'→...
    
-   Set random: copy.random = [original.random.next](http://original.random.next)
    
-   Separate lists
    

**Time Complexity:** O(n) - two passes **Space Complexity:** O(n) - hash map

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) return null;

        // Step 1: Create interleaved list
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }

        // Step 2: Set random pointers
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        // Step 3: Separate lists
        Node dummy = new Node(0);
        Node copyCurr = dummy;
        curr = head;
        while (curr != null) {
            copyCurr.next = curr.next;
            curr.next = curr.next.next;
            copyCurr = copyCurr.next;
            curr = curr.next;
        }

        return dummy.next;
    }
}
```
