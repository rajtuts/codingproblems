# Largest Rectangle in Histogram
Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.

Example 1:
Input: [2,1,5,6,2,3]
Output: 10

Monotonic stack. When popping, calculate area with popped bar as height extending to current position.

Time: O(n)
Space: O(n)

```
class Solution {
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        Stack<int[]> stack = new Stack<>(); // index, height
        
        for (int i = 0; i < heights.length; i++) {
            int start = i;
            while (!stack.isEmpty() && stack.peek()[1] > heights[i]) {
                int[] pair = stack.pop();
                int index = pair[0];
                int height = pair[1];
                maxArea = Math.max(maxArea, height * (i - index));
                start = index;
            }
            stack.push(new int[]{start, heights[i]});
        }
        
        for (int[] pair : stack) {
            maxArea = Math.max(maxArea, pair[1] * (heights.length - pair[0]));
        }
        return maxArea;
    }
}
```
