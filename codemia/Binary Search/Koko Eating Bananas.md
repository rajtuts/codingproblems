# Koko Eating Bananas
Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone and will come back in h hours. Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour. Return the minimum integer k such that she can eat all the bananas within h hours.
  
**Example 1:**  
Input: {"piles":[3,6,7,11],"h":8}  
Output: 4  

**Approach: Binary Search on Answer**

**Key Insight:** If Koko can finish at speed k, she can finish at any speed > k. Binary search for the minimum valid speed.

**Algorithm:**

1.  Search range: \[1, max(piles)\]
    
2.  For each candidate speed mid:
    
    -   Calculate hours needed: sum(ceil(pile/mid) for each pile)
        
    -   If hours <= h: speed is valid, try smaller
        
    -   Else: need higher speed
        
3.  Return minimum valid speed
    

**Example:** piles = \[3,6,7,11\], h = 8

-   Speed 4: hours = 1+2+2+3 = 8 ≤ 8 ✓
    
-   Speed 3: hours = 1+2+3+4 = 10 > 8 ✗
    
-   Minimum speed: 4
    

**Time Complexity:** O(n log m) - n piles, m = max pile size **Space Complexity:** O(1) - only variables

**Time: O(n log m)**

**Space: O(1)**

```
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int l = 1, r = 1;
        for (int p : piles) r = Math.max(r, p);
        int res = r;
        
        while (l <= r) {
            int k = l + (r - l) / 2;
            long hours = 0;
            for (int p : piles) {
                hours += (p + k - 1) / k; // ceil(p/k)
            }
            
            if (hours <= h) {
                res = Math.min(res, k);
                r = k - 1;
            } else {
                l = k + 1;
            }
        }
        return res;
    }
}
```
