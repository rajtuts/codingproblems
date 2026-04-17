# Daily Temperatures
Given an array of integers temperatures represents the daily temperatures, return an array answer such that answer[i] is the number of days you have to wait after the ith day to get a warmer temperature. If there is no future day for which this is possible, keep answer[i] == 0.

Example 1:
Input: [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]

Use a monotonic decreasing stack storing indices. When we find a warmer temperature, pop indices and calculate the difference.

Time: O(n)
Space: O(n)

```
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>(); // index
        
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int index = stack.pop();
                res[index] = i - index;
            }
            stack.push(i);
        }
        return res;
    }
}
```
