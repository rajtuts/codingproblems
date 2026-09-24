package HeapTrie;

import java.util.PriorityQueue;

public class MedianFinderLeetCode295 {

    PriorityQueue<Integer> maxHeap;
    PriorityQueue<Integer> minHeap;

    public MedianFinderLeetCode295() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        minHeap = new PriorityQueue<>((a, b) -> a - b);
    }

    public void addNum(int num) {
        minHeap.add(num);
        maxHeap.add(minHeap.poll());
        if (minHeap.size() < maxHeap.size()) {
            minHeap.add(maxHeap.poll());
        }
    } // TC: 0(log n), SC: 0(log n)

    public double findMedian() {
        if (minHeap.size() == maxHeap.size()) {// even number of integers
            return (minHeap.peek() + maxHeap.peek()) / 2.0;
        }
        return minHeap.peek();
    } // TC: 0(1), SC: 0(1)
}