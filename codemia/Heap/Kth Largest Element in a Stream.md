# Kth Largest Element in a Stream
Design a class to find the kth largest element in a stream. Note that it is the kth largest element in the sorted order, not the kth distinct element.

Example 1:
Input: {"commands":["KthLargest","add","add","add","add","add"],"values":[[3,[4,5,8,2]],[3],[5],[10],[9],[4]]}
Output: [null,4,5,5,8,8]

**Approach: Min-Heap of Size K**

**Key Insight:** Maintain a min-heap of size k containing the k largest elements. The root (minimum of these k) is always the kth largest overall.

**Algorithm:**

1.  Initialize min-heap from initial nums
    
2.  While heap size > k: pop smallest
    
3.  For each add(val):
    
    -   Push val to heap
        
    -   If size > k: pop smallest
        
    -   Return heap root (kth largest)
        

**Example:** k=3, nums=\[4,5,8,2\]

-   Initial heap: \[4,5,8,2\] → heapify → \[2,4,5,8\]
    
-   Trim to size 3: pop 2 → \[4,5,8\]
    
-   add(3): push → \[3,4,5,8\] → pop 3 → \[4,5,8\] → return 4
    
-   add(5): push → \[4,5,5,8\] → pop 4 → \[5,5,8\] → return 5
    
-   add(10): push → \[5,5,8,10\] → pop 5 → \[5,8,10\] → return 5
    

**Why Min-Heap:**

-   We need the SMALLEST of the k largest
    
-   Min-heap gives O(1) access to minimum
    
-   Pop removes elements smaller than kth largest
    

**Time Complexity:** O(log k) per add - heap operations **Space Complexity:** O(k) - heap size

**Time: O(log k) per add**

**Space: O(k)**

```
class KthLargest {
    PriorityQueue<Integer> minHeap;
    int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        minHeap = new PriorityQueue<>();
        for (int num : nums) add(num);
    }
    
    public int add(int val) {
        minHeap.add(val);
        if (minHeap.size() > k) minHeap.poll();
        return minHeap.peek();
    }
}
```
