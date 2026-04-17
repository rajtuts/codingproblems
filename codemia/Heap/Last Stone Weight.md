# Last Stone Weight
You have a collection of stones with positive integer weights. Each turn, choose the two heaviest stones and smash them. If they have different weights, the lighter one is destroyed and the heavier one loses weight equal to the lighter. Return the weight of the last remaining stone, or 0 if none remain.

Example 1:
Input: [2,7,4,1,8,1]
Output: 1

**Approach: Max Heap Simulation**

This is a simulation problem where we repeatedly need the two largest elements. A max heap is perfect for this.

**Algorithm:**

1.  Build a max heap from all stones.
    
2.  While heap has more than 1 stone:
    
    -   Pop the two heaviest stones (first, second)
        
    -   If first != second, push (first - second) back
        
    -   If first == second, both are destroyed (push nothing)
        
3.  Return the last stone's weight, or 0 if heap is empty.
    

**Python Implementation Note:** Python's heapq is a min heap. To use it as max heap:

-   Push negated values: `heappush(heap, -value)`
    
-   Pop and negate: `value = -heappop(heap)`
    

**Example: stones = \[2, 7, 4, 1, 8, 1\]**

-   Heap: \[8, 7, 4, 2, 1, 1\]
    
-   Pop 8 and 7, push 1: \[4, 2, 1, 1, 1\]
    
-   Pop 4 and 2, push 2: \[2, 1, 1, 1\]
    
-   Pop 2 and 1, push 1: \[1, 1, 1\]
    
-   Pop 1 and 1, both destroyed: \[1\]
    
-   Return 1
    

**Time Complexity:** O(n log n) - n heap operations, each O(log n) **Space Complexity:** O(n) - for the heap

**Time: O(n log n)**

**Space: O(n)**

```
class Solution {
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : stones) heap.add(s);

        while (heap.size() > 1) {
            int first = heap.poll();
            int second = heap.poll();
            if (first != second) {
                heap.add(first - second);
            }
        }

        return heap.isEmpty() ? 0 : heap.peek();
    }
}
```
