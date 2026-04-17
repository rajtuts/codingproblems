# Kth Largest Element in an Array
Given an integer array nums and an integer k, return the kth largest element in the array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

Example 1:
Input: {"nums":[3,2,1,5,6,4],"k":2}
Output: 5

**Approach 1: Min Heap of Size K**

Maintain a min heap of the k largest elements. The root is the k-th largest.

**Algorithm:**

1.  For each number in array:
    
    -   Push to heap
        
    -   If heap size > k, pop minimum
        
2.  Root of heap is the k-th largest.
    

**Why Min Heap?**

-   The k largest elements stay in the heap.
    
-   The smallest among them (root) is exactly the k-th largest overall.
    

**Approach 2: Quickselect (O(n) average)**

Use the partition step from quicksort:

1.  Partition array around a pivot.
    
2.  If pivot is at position n-k, return it.
    
3.  Otherwise, recurse on the appropriate half.
    

**Comparison:**

-   Heap: O(n log k) time, O(k) space, always works
    
-   Quickselect: O(n) average, O(n²) worst case, O(1) space
    

**Time Complexity:** O(n log k) with heap **Space Complexity:** O(k) for heap

**Time: O(n log k)**

**Space: O(k)**

```
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // Min heap of size k
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        for (int num : nums) {
            heap.add(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        return heap.peek();
    }
}
```
