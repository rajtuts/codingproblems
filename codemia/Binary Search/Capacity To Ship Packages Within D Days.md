# Capacity To Ship Packages Within D Days
A conveyor belt has packages that must be shipped from one port to another within days days. The ith package has a weight of weights[i]. Each day, we load the ship with packages on the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity of the ship. Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within days days.

Example 1:
Input: {"weights":[1,2,3,4,5,6,7,8,9,10],"days":5}
Output: 15

**Approach: Binary Search on Answer**

**Key Insight:** If capacity c works, any capacity > c also works. Binary search for minimum valid capacity.

**Algorithm:**

1.  Search range: \[max(weights), sum(weights)\]
    
    -   Minimum: must fit largest package
        
    -   Maximum: ship everything in one day
        
2.  For each capacity mid, simulate shipping:
    
    -   Pack packages until exceeding capacity, then new day
        
3.  If days\_needed <= days: valid, try smaller
    
4.  Else: need larger capacity
    

**Example:** weights = \[1,2,3,4,5,6,7,8,9,10\], days = 5

-   Capacity 15: \[1,2,3,4,5\], \[6,7\], \[8\], \[9\], \[10\] = 5 days ✓
    
-   Capacity 14: 6 days needed ✗
    
-   Result: 15
    

**Time Complexity:** O(n log S) - S = sum of weights **Space Complexity:** O(1) - only variables

**Time: O(n log S)**

**Space: O(1)**

```
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int l = 0, r = 0;
        for (int w : weights) {
            l = Math.max(l, w);
            r += w;
        }
        int res = r;
        
        while (l <= r) {
            int cap = l + (r - l) / 2;
            if (canShip(cap, weights, days)) {
                res = Math.min(res, cap);
                r = cap - 1;
            } else {
                l = cap + 1;
            }
        }
        return res;
    }
    
    private boolean canShip(int cap, int[] weights, int days) {
        int ships = 1, currCap = cap;
        for (int w : weights) {
            if (currCap - w < 0) {
                ships++;
                currCap = cap;
            }
            currCap -= w;
        }
        return ships <= days;
    }
}
```
