# Jump Game
You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position. Return true if you can reach the last index, or false otherwise.

Example 1:
Input: [2,3,1,1,4]
Output: true

**Approach: Greedy**

**Key Insight:** Track the farthest position reachable so far. If at any point the current index exceeds the farthest reachable, we can't proceed.

**Algorithm:**

1.  Initialize farthest = 0
    
2.  For each index i:
    
    -   If i > farthest: return False (unreachable)
        
    -   Update farthest = max(farthest, i + nums\[i\])
        
3.  Return True (reached or passed the end)
    

**Example:** nums = \[2,3,1,1,4\]

-   i=0: farthest = max(0, 0+2) = 2
    
-   i=1: farthest = max(2, 1+3) = 4
    
-   i=2: farthest = max(4, 2+1) = 4
    
-   i=3: farthest = max(4, 3+1) = 4
    
-   i=4: reached end ✓
    

**Example:** nums = \[3,2,1,0,4\]

-   i=0: farthest = 3
    
-   i=1: farthest = 3
    
-   i=2: farthest = 3
    
-   i=3: farthest = 3
    
-   i=4: 4 > 3 → False ✗
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - only farthest variable

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public boolean canJump(int[] nums) {
        int goal = nums.length - 1;
        
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= goal) {
                goal = i;
            }
        }
        
        return goal == 0;
    }
}
```
