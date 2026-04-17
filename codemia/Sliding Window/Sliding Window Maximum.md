# Sliding Window Maximum
You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.

Example 1:
Input: {"nums":[1,3,-1,-3,5,3,6,7],"k":3}
Output: [3,3,5,5,6,7]

**Approach: Monotonic Decreasing Deque**

The key insight is to maintain a deque that stores indices of elements in decreasing order of their values. The front of the deque always contains the index of the maximum element in the current window.

**Algorithm:**

1.  For each element at index i:
    
    -   Remove indices from the front that are outside the window (< i - k + 1).
        
    -   Remove indices from the back where the element is smaller than nums\[i\] (they can never be the max while nums\[i\] is in the window).
        
    -   Add index i to the back.
        
    -   If window is complete (i >= k - 1), add nums\[deque\[0\]\] to result.
        

**Why it works:**

-   Elements smaller than the current element and to its left can never be the maximum while the current element is in the window.
    
-   The deque maintains a "potential maximums" list in decreasing order.
    
-   The front is always the maximum because larger elements always push smaller ones out.
    

**Example with nums=\[1,3,-1,-3,5,3,6,7\], k=3:**

-   i=0: deque=\[0\], no result yet
    
-   i=1: nums\[0\]=1 < 3, pop 0, deque=\[1\], no result yet
    
-   i=2: deque=\[1,2\], result=\[3\] (nums\[1\])
    
-   i=3: deque=\[1,2,3\], result=\[3,3\]
    
-   i=4: 1 is out of window, pop. 3,-1,-3 < 5, pop all. deque=\[4\], result=\[3,3,5\] ...
    

**Time Complexity:** O(n) - each element is added and removed from deque at most once **Space Complexity:** O(k) - deque holds at most k elements

**Time: O(n)**

**Space: O(k)**


```
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            // Remove indices outside current window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            // Remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            // Add max to result when window is complete
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
```
