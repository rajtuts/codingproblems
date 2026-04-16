# Non-overlapping Intervals
Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

Example 1:
Input: [[1,2],[2,3],[3,4],[1,3]]
Output: 1

**Approach: Greedy by End Time**

**Key Insight:** Sort intervals by end time. Greedily keep intervals that don't overlap with the last kept interval. Count removals.

**Algorithm:**

1.  Sort intervals by end time
    
2.  Track end of last kept interval
    
3.  For each interval:
    
    -   If start >= end: no overlap, keep it (update end)
        
    -   Else: overlap, must remove it (increment count)
        
4.  Return count
    

**Example:** intervals = \[\[1,2\],\[2,3\],\[3,4\],\[1,3\]\]

-   Sorted by end: \[\[1,2\],\[2,3\],\[1,3\],\[3,4\]\]
    
-   \[1,2\]: end=-∞, 1>=-∞ → keep, end=2
    
-   \[2,3\]: 2>=2 → keep, end=3
    
-   \[1,3\]: 1<3 → overlap, remove (count=1)
    
-   \[3,4\]: 3>=3 → keep, end=4
    
-   Result: 1 removal ✓
    

**Why Sort by End Time:**

-   Keeping intervals that end early leaves more room for future intervals
    
-   Minimizes conflicts
    

**Time Complexity:** O(n log n) - sorting dominates **Space Complexity:** O(1) - or O(n) for sorting

**Time: O(n log n)**

**Space: O(1)**

```
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        
        int res = 0;
        int prevEnd = Integer.MIN_VALUE;
        
        for (int[] interval : intervals) {
            if (interval[0] >= prevEnd) {
                prevEnd = interval[1];
            } else {
                res++;
            }
        }
        return res;
    }
}
```
