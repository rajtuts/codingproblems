# Minimum Number of Arrows to Burst Balloons
There are some spherical balloons taped onto a flat wall that represents the XY-plane. The balloons are represented as a 2D integer array points where points[i] = [xstart, xend] denotes a balloon whose horizontal diameter stretches between xstart and xend. You need to find the minimum number of arrows that must be shot to burst all balloons. An arrow can be shot at any integer x coordinate. A balloon with xstart and xend is burst by an arrow shot at x if xstart <= x <= xend.

Example 1:
Input: [[10,16],[2,8],[1,6],[7,12]]
Output: 2

**Approach: Greedy Interval Scheduling**

**Key Insight:** Sort balloons by end position. Shoot at the end of the current balloon—this maximizes chances of hitting overlapping balloons. Start new arrow only when current balloon doesn't overlap.

**Algorithm:**

1.  Sort balloons by end position
    
2.  Initialize arrows = 1, current\_end = first balloon's end
    
3.  For each balloon after first:
    
    -   If balloon.start > current\_end: new arrow needed
        
        -   arrows++, current\_end = balloon.end
            
    -   Else: current arrow can burst this balloon (no change)
        
4.  Return arrows
    

**Example:** points = \[\[10,16\],\[2,8\],\[1,6\],\[7,12\]\]

-   Sorted by end: \[\[1,6\],\[2,8\],\[7,12\],\[10,16\]\]
    
-   Arrow 1 at x=6: bursts \[1,6\], \[2,8\] (8≥6? no, 2≤6 yes)
    
-   Actually: \[1,6\] end=6, \[2,8\] start=2≤6 → same arrow
    
-   \[7,12\] start=7>6 → new arrow at x=12
    
-   \[10,16\] start=10≤12 → same arrow
    
-   Total: 2 arrows ✓
    

**Why Sort by End:**

-   Shooting at earliest end maximizes overlap potential
    
-   Similar to interval scheduling (activity selection)
    

**Time Complexity:** O(n log n) - sorting dominates **Space Complexity:** O(1) - or O(n) for sorting

**Time: O(n log n)**

**Space: O(1)**

```
class Solution {
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) return 0;

        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        int arrows = 1;
        int end = points[0][1];

        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > end) {
                arrows++;
                end = points[i][1];
            }
        }

        return arrows;
    }
}
```
