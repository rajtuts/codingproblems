# Ones and Zeroes
You are given an array of binary strings strs and two integers m and n. Return the size of the largest subset of strs such that there are at most m 0's and n 1's in the subset.

Example 1:
Input: {"strs":["10","0001","111001","1","0"],"m":5,"n":3}
Output: 4


**Approach: 2D Knapsack DP**

**Key Insight:** This is a 0/1 knapsack with two constraints (0s and 1s). Use 2D DP where dp\[i\]\[j\] = max strings using ≤i zeros and ≤j ones.

**Algorithm:**

1.  Initialize dp\[m+1\]\[n+1\] with zeros
    
2.  For each string, count its 0s and 1s
    
3.  Update dp in reverse order (to avoid reusing same string)
    
4.  dp\[i\]\[j\] = max(dp\[i\]\[j\], dp\[i-zeros\]\[j-ones\] + 1)
    

**Time Complexity:** O(l _m_ n) where l = len(strs) **Space Complexity:** O(m \* n)

**Time: O(l _m_ n)**

**Space: O(m \* n)**

```
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        for (String s : strs) {
            int zeros = 0, ones = 0;
            for (char c : s.toCharArray()) {
                if (c == '0') zeros++;
                else ones++;
            }

            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }
        }

        return dp[m][n];
    }
}
```
