Container With Most Water
Find two lines that together with the x-axis form a container, such that the container contains the most water.

Example 1:
Input: [1,8,6,2,5,4,8,3,7]
Output: 49

**Approach: Two Pointers (Greedy)**

**Key Insight:** Area = width × min(height\[left\], height\[right\]). Start with maximum width (both ends) and greedily try to find taller heights.

**Algorithm:**

1.  Initialize left = 0, right = n-1, max\_area = 0
    
2.  While left < right:
    
    -   Calculate area = (right - left) × min(height\[left\], height\[right\])
        
    -   Update max\_area if larger
        
    -   Move the pointer with smaller height inward
        
3.  Return max\_area
    

**Why Move the Shorter Side?**

-   Width always decreases by 1 when we move any pointer
    
-   Moving the taller side can only decrease or maintain height (limited by shorter)
    
-   Moving the shorter side might find a taller line, potentially increasing area
    
-   This greedy choice never misses the optimal solution
    

**Proof by Contradiction:** If we kept the shorter line, the area is bounded by that height regardless of what's on the other side. By moving it, we have a chance to find something taller.

**Example:** height = \[1,8,6,2,5,4,8,3,7\]

-   l=0,r=8: area=1×8=8, move left (1<7)
    
-   l=1,r=8: area=7×7=49, move right (8>7)
    
-   l=1,r=7: area=3×6=18... continue
    

**Time: O(n)**, **Space: O(1)**

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxArea(int[] height) {
        int l = 0;
        int r = height.length - 1;
        int res = 0;

        while (l < r) {
            int area = (r - l) * Math.min(height[l], height[r]);
            res = Math.max(res, area);

            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return res;
    }
}
```
