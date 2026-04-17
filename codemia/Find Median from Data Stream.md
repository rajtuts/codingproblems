# Find Median from Data Stream
The median is the middle value in an ordered integer list. Implement the MedianFinder class.

Example 1:
Input: {"commands":["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"],"values":[[],[1],[2],[],[3],[]]}
Output: [null,null,null,1.5,null,2.0]

**Approach: Two Heaps (Max + Min)**

**Key Insight:** Maintain two heaps: a max-heap for the lower half of numbers and a min-heap for the upper half. The median is either the max-heap top or the average of both tops.

**Algorithm:**

1.  small = max-heap (negate values), large = min-heap
    
2.  addNum(num):
    
    -   Push to small (max-heap)
        
    -   Move largest of small to large (balance)
        
    -   If large has more: move smallest back to small
        
3.  findMedian():
    
    -   If small has more: return -small\[0\]
        
    -   Else: return (-small\[0\] + large\[0\]) / 2
        

**Example:** Add 1, 2, 3

-   Add 1: small=\[-1\], large=\[\] → median=1
    
-   Add 2: small=\[-1\], push 2, pop 1→large → small=\[-2\], large=\[1\] → large bigger, move 1 back → small=\[-1\], large=\[2\] Wait, let me recalculate...
    
-   Actually: small=\[-1\], large=\[2\] → median=(1+2)/2=1.5
    
-   Add 3: small=\[-1,-3\], large=\[2\] → pop -1→ large=\[1,2\] → small=\[-3\], large=\[1,2\] → median=1? Rebalance: large bigger → move 1 to small → small=\[-1,-3\]? No wait...
    

**Invariant:**

-   |small| >= |large|
    
-   |small| - |large| <= 1
    

**Time Complexity:** O(log n) add, O(1) find **Space Complexity:** O(n) - storing all elements

**Time: O(log n) add, O(1) find**

**Space: O(n)**

```
class MedianFinder {
    PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> large = new PriorityQueue<>();

    public void addNum(int num) {
        small.add(num);
        if (small.size() > 0 && large.size() > 0 && small.peek() > large.peek()) {
            large.add(small.poll());
        }
        if (small.size() > large.size() + 1) {
            large.add(small.poll());
        }
        if (large.size() > small.size() + 1) {
            small.add(large.poll());
        }
    }

    public double findMedian() {
        if (small.size() > large.size()) return small.peek();
        if (large.size() > small.size()) return large.peek();
        return (small.peek() + large.peek()) / 2.0;
    }
}

```
