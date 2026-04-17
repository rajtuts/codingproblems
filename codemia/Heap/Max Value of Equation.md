# Max Value of Equation
Given an array of points sorted by x-coordinate and an integer k, find the maximum value of the equation yi + yj + |xi - xj| where |xi - xj| <= k and i < j.

Example 1:
Input: {"points":[[1,3],[2,0],[5,10],[6,-10]],"k":1}
Output: 4

Use monotonic deque. For each point j, we want max(yi - xi) where xj - xi <= k. Maintain deque of (yi - xi, xi) in decreasing order of yi - xi. Remove expired points and update max.

Time: O(n)
Space: O(n)

```
class Solution {
    public int findMaxValueOfEquation(int[][] points, int k) {
        Deque<int[]> dq = new ArrayDeque<>();
        int result = Integer.MIN_VALUE;

        for (int[] p : points) {
            int xj = p[0], yj = p[1];

            while (!dq.isEmpty() && xj - dq.peekFirst()[1] > k) {
                dq.pollFirst();
            }

            if (!dq.isEmpty()) {
                result = Math.max(result, yj + xj + dq.peekFirst()[0]);
            }

            while (!dq.isEmpty() && dq.peekLast()[0] <= yj - xj) {
                dq.pollLast();
            }
            dq.offerLast(new int[]{yj - xj, xj});
        }

        return result;
    }
}
```
