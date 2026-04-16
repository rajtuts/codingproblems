# Maximum Number of Events That Can Be Attended II
Given an array of events where events[i] = [startDay, endDay, value], and an integer k. You can attend at most k events. You cannot attend two events that overlap. Return the maximum sum of values that you can receive by attending events.

Example 1:
Input: {"events":[[1,2,4],[3,4,3],[2,3,1]],"k":2}
Output: 7

Sort by start time. DP: for each event, either take it (add value, find next non-overlapping via binary search) or skip. Memoize on (index, remaining k).

Time: O(n * k * log n)
Space: O(n * k)

```
class Solution {
    int[][] events;
    int[][] memo;

    public int maxValue(int[][] events, int k) {
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        this.events = events;
        this.memo = new int[events.length][k + 1];
        for (int[] row : memo) Arrays.fill(row, -1);
        return dp(0, k);
    }

    private int dp(int i, int remaining) {
        if (i >= events.length || remaining == 0) return 0;
        if (memo[i][remaining] != -1) return memo[i][remaining];

        int end = events[i][1];
        int lo = i + 1, hi = events.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (events[mid][0] > end) hi = mid;
            else lo = mid + 1;
        }

        int take = events[i][2] + dp(lo, remaining - 1);
        int skip = dp(i + 1, remaining);
        return memo[i][remaining] = Math.max(take, skip);
    }
}
```
