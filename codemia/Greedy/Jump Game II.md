Jump Game II
You are given a 0-indexed array of integers nums of length n. You are initially positioned at nums[0]. Each element nums[i] represents the maximum length of a forward jump from index i. Return the minimum number of jumps to reach nums[n - 1].

Example 1:
Input: [2,3,1,1,4]
Output: 2

**Approach: Greedy BFS-like**

**Key Insight:** Track the farthest position reachable within the current jump. When you reach the end of the current range, you must make a jump.

**Algorithm:**

1.  Track: jumps, current\_end (range of current jump), farthest
    
2.  For each index i (until n-2):
    
    -   Update farthest = max(farthest, i + nums\[i\])
        
    -   If i == current\_end: must jump
        
        -   jumps++
            
        -   current\_end = farthest
            
3.  Return jumps
    

**Example:** nums = \[2,3,1,1,4\]

-   i=0: farthest=2, i==current\_end(0) → jump, current\_end=2
    
-   i=1: farthest=4
    
-   i=2: i==current\_end(2) → jump, current\_end=4
    
-   Reached end, jumps=2
    

**Why it works:**

-   Each "level" represents positions reachable with k jumps
    
-   We jump when forced (reaching current level's end)
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - only variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int jump(int[] nums) {
        int res = 0;
        int l = 0, r = 0;
        
        while (r < nums.length - 1) {
            int farthest = 0;
            for (int i = l; i <= r; i++) {
                farthest = Math.max(farthest, i + nums[i]);
            }
            l = r + 1;
            r = farthest;
            res++;
        }
        return res;
    }
}
````
