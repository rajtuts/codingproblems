# Counting Bits
Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.

Example 1:
Input: 5
Output: [0,1,1,2,1,2]

**Approach: DP with Bit Counting**

**Key Insight:** The number of 1-bits in i equals the number of 1-bits in (i with highest bit removed) plus 1. Use previously computed values: dp\[i\] = dp\[i - offset\] + 1.

**Algorithm:**

1.  Initialize dp\[0\] = 0
    
2.  Track offset (highest power of 2 seen so far)
    
3.  For i from 1 to n:
    
    -   If i is power of 2: update offset = i
        
    -   dp\[i\] = dp\[i - offset\] + 1
        
4.  Return dp array
    

**Example:** n = 5

-   dp\[0\] = 0
    
-   dp\[1\] = dp\[1-1\]+1 = dp\[0\]+1 = 1 (offset=1)
    
-   dp\[2\] = dp\[2-2\]+1 = dp\[0\]+1 = 1 (offset=2)
    
-   dp\[3\] = dp\[3-2\]+1 = dp\[1\]+1 = 2
    
-   dp\[4\] = dp\[4-4\]+1 = dp\[0\]+1 = 1 (offset=4)
    
-   dp\[5\] = dp\[5-4\]+1 = dp\[1\]+1 = 2
    
-   Result: \[0,1,1,2,1,2\] ✓
    

**Why This Works:**

-   Subtracting highest power of 2 clears the highest bit
    
-   Remaining bits are same as smaller number
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(n) - result array

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int[] countBits(int n) {
        int[] dp = new int[n + 1];
        int offset = 1;

        for (int i = 1; i <= n; i++) {
            if (offset * 2 == i) {
                offset = i;
            }
            dp[i] = 1 + dp[i - offset];
        }
        return dp;
    }
}
```
