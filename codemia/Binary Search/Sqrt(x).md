# Sqrt(x)
Given a non-negative integer x, return the square root of x rounded down to the nearest integer.

Example 1:
Input: 8
Output: 2

**Approach: Binary Search**

**Key Insight:** We're searching for the largest integer k where k² ≤ x. This is a classic binary search problem since the square function is monotonically increasing.

**Algorithm:**

1.  Handle edge cases: if x < 2, return x (sqrt(0)=0, sqrt(1)=1)
    
2.  Search space: 1 to x/2 (since for x≥4, sqrt(x) ≤ x/2)
    
3.  Binary search for the largest k where k² ≤ x:
    
    -   If mid² = x, found exact square root
        
    -   If mid² < x, search right half (might find larger valid k)
        
    -   If mid² > x, search left half
        

**Why Return** `right`**?** When the loop ends, `left > right`. The last valid value where mid² ≤ x was stored in `right` (since we moved left = mid + 1 when mid² < x).

**Example:** x = 8

-   Search \[1, 4\], mid = 2, 2² = 4 < 8 → left = 3
    
-   Search \[3, 4\], mid = 3, 3² = 9 > 8 → right = 2
    
-   left > right, return right = 2 ✓
    

**Time Complexity:** O(log x) - binary search halves the search space each iteration **Space Complexity:** O(1) - only using a few variables

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int mySqrt(int x) {
        if (x < 2) return x;

        long left = 1, right = x / 2;
        while (left <= right) {
            long mid = (left + right) / 2;
            if (mid * mid == x) return (int) mid;
            else if (mid * mid < x) left = mid + 1;
            else right = mid - 1;
        }
        return (int) right;
    }
}
```
