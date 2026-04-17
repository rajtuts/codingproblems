# K Closest Points to Origin
Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0). The distance between two points is the Euclidean distance. You may return the answer in any order.

Example 1:
Input: {"points":[[1,3],[-2,2]],"k":1}
Output: [[-2,2]]

**Approach: Max Heap of Size K**

We want the k smallest distances. Using a max heap of size k, we keep track of the k closest points seen so far.

**Key Insight:**

-   Don't compute sqrt - comparing x² + y² gives the same ordering.
    
-   Use a max heap (negate distances in Python) to track k smallest.
    
-   When heap exceeds k, pop the farthest (largest distance).
    

**Algorithm:**

1.  For each point, compute distance² = x² + y².
    
2.  Push (-distance, x, y) to heap (negate for max heap behavior).
    
3.  If heap size > k, pop the maximum (farthest point).
    
4.  Return all points remaining in heap.
    

**Why Max Heap?**

-   We want to quickly remove the FARTHEST among our k candidates.
    
-   Max heap gives O(1) access to maximum element.
    

**Alternative: Quickselect**

-   Partition around k-th element in O(n) average time.
    
-   Better for large n, but heap solution is simpler.
    

**Time Complexity:** O(n log k) - n insertions, each O(log k) **Space Complexity:** O(k) - heap size

**Time: O(n log k)**

**Space: O(k)**

```
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        // Max heap based on distance
        PriorityQueue<int[]> heap = new PriorityQueue<>(
            (a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1])
        );

        for (int[] p : points) {
            heap.add(p);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        return heap.toArray(new int[k][]);
    }
}
```
