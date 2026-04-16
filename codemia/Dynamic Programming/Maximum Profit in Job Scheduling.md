# Maximum Profit in Job Scheduling
We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of profit[i]. Return the maximum profit you can take such that there are no two jobs with overlapping time ranges.

Example 1:
Input: {"startTime":[1,2,3,3],"endTime":[3,4,5,6],"profit":[50,10,40,70]}
Output: 120

Sort jobs by start time. DP where dp[i] = max profit from job i onwards. For each job: take it (profit + dp[next]) or skip (dp[i+1]). Binary search finds next non-overlapping job.

Time: O(n log n)
Space: O(n)

```
class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b) -> a[0] - b[0]);

        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            int nextJob = findNext(jobs, i);
            int take = jobs[i][2] + dp[nextJob];
            int skip = dp[i + 1];
            dp[i] = Math.max(take, skip);
        }

        return dp[0];
    }

    private int findNext(int[][] jobs, int i) {
        int target = jobs[i][1];
        int lo = i + 1, hi = jobs.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (jobs[mid][0] >= target) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
}
```
