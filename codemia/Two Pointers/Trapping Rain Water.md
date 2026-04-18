# Trapping Rain Water
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

Example 1:
Input: [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6

**Approach: Two Pointers**

**Key Insight:** Water at position i = min(maxLeft, maxRight) - height\[i\]. The water level is determined by the SHORTER of the two maximum heights on either side.

**Two-Pointer Algorithm:**

1.  Initialize left=0, right=n-1, leftMax=0, rightMax=0, water=0
    
2.  While left < right:
    
    -   If height\[left\] < height\[right\]:
        
        -   If height\[left\] >= leftMax: update leftMax
            
        -   Else: water += leftMax - height\[left\]
            
        -   Move left++
            
    -   Else:
        
        -   If height\[right\] >= rightMax: update rightMax
            
        -   Else: water += rightMax - height\[right\]
            
        -   Move right--
            

**Why This Works:** We process from the side with the smaller max. Since the other side has a larger or equal max, water is bounded by our current side's max.

**Example:** height = \[0,1,0,2,1,0,1,3,2,1,2,1\]

`█ █ ██ █ █ ██ ████ █ ─────────────`

Water trapped at each position: \[0,0,1,0,1,2,1,0,0,1,0,0\] = 6

**Alternative Approaches:**

-   **DP (Two Pass):** Precompute leftMax\[\] and rightMax\[\] arrays - O(n) space
    
-   **Stack:** Track indices of bars that might trap water - O(n) space
    

**Time: O(n)**, **Space: O(1)** for two-pointer approach

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int trap(int[] height) {
        if (height.length == 0) return 0;
        int l = 0, r = height.length - 1;
        int leftMax = height[l], rightMax = height[r];
        int res = 0;
        
        while (l < r) {
            if (leftMax < rightMax) {
                l++;
                leftMax = Math.max(leftMax, height[l]);
                res += leftMax - height[l];
            } else {
                r--;
                rightMax = Math.max(rightMax, height[r]);
                res += rightMax - height[r];
            }
        }
        return res;
    }
}
```
