# Merge K Sorted Lists
You are given an array of k linked-lists lists, each linked-list is sorted in ascending order. Merge all the linked-lists into one sorted linked-list and return it.

Example 1:
Input: [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]

**Approach: Min-Heap (Priority Queue)**

**Key Insight:** At any point, the next node in the merged list is the minimum among all current heads. A min-heap efficiently finds this minimum.

**Algorithm:**

1.  Add the head of each non-empty list to a min-heap
    
2.  While heap is not empty:
    
    -   Pop the minimum node
        
    -   Add it to the result
        
    -   If that node has a next, push next to the heap
        
3.  Return merged list
    

**Why Heap Works:**

-   Heap maintains k elements (one from each list)
    
-   Each pop/push is O(log k)
    
-   Total operations: n nodes × O(log k) = O(n log k)
    

**Implementation Detail (Python):**

`import heapq heap = [(node.val, i, node) for i, node in enumerate(lists) if node] heapq.heapify(heap) # Use index i as tiebreaker since ListNode isn't comparable`

**Alternative Approaches:**

-   **Divide and Conquer:** Merge pairs of lists recursively
    
    -   T: O(n log k), S: O(log k) for recursion
        
-   **Merge One by One:** Merge 2 lists at a time
    
    -   T: O(nk), less efficient
        

**Time: O(n log k)** where n = total nodes, k = number of lists **Space: O(k)** for the heap

**Time: O(N log k) where N is total nodes**

**Space: O(k)**

```
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> a.val - b.val);
        for (ListNode list : lists) {
            if (list != null) pq.add(list);
        }
        
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            tail.next = node;
            tail = tail.next;
            
            if (node.next != null) pq.add(node.next);
        }
        
        return dummy.next;
    }
}
````
