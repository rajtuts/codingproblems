# Minimum Difficulty of a Job Schedule
You want to schedule a list of jobs in d days. Jobs are dependent so you have to finish all jobs from index 0 to i before working on job i+1. Each day you must work on at least one job. The difficulty of a day is the maximum difficulty of a job done that day. Return the minimum sum of difficulties of each day.

Example 1:
Input: {"jobDifficulty":[6,5,4,3,2,1],"d":2}
Output: 7

DP where dp(i, k) = min difficulty to schedule jobs[i:] in k days. Base case: when k=1, must do all remaining jobs in one day (max difficulty). Otherwise, try each split point and recurse.

Time: O(n^2 * d)
Space: O(n * d)

```
class Solution {
    public int minDifficulty(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        if (n < d) return -1;

        int[][] dp = new int[n + 1][d + 1];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= Math.min(i, d); k++) {
                int maxDiff = 0;
                for (int j = i - 1; j >= k - 1; j--) {
                    maxDiff = Math.max(maxDiff, jobDifficulty[j]);
                    if (dp[j][k - 1] != Integer.MAX_VALUE) {
                        dp[i][k] = Math.min(dp[i][k], dp[j][k - 1] + maxDiff);
                    }
                }
            }
        }

        return dp[n][d] == Integer.MAX_VALUE ? -1 : dp[n][d];
    }
}

```
