# Best Time to Buy and Sell Stock II
Find the maximum profit from buying and selling stock with unlimited transactions. You may hold at most one share at a time.

Example 1:
Input: [7,1,5,3,6,4]
Output: 7

**Approach: Greedy**

**Key Insight:** If tomorrow's price is higher than today's, buy today and sell tomorrow. By summing all positive price differences, we capture every opportunity to profit.

**Algorithm:**

1.  Initialize profit = 0
    
2.  For each consecutive pair of days:
    
    -   If prices\[i\] > prices\[i-1\]: profit += prices\[i\] - prices\[i-1\]
        
3.  Return profit
    

**Example:** prices = \[7,1,5,3,6,4\]

-   Day 1→2: 1-7 = -6 (skip)
    
-   Day 2→3: 5-1 = 4 (profit += 4)
    
-   Day 3→4: 3-5 = -2 (skip)
    
-   Day 4→5: 6-3 = 3 (profit += 3)
    
-   Day 5→6: 4-6 = -2 (skip)
    
-   Total: 4 + 3 = 7 ✓
    

**Why This Works:**

-   Equivalent to buying/selling multiple times
    
-   Every upward segment is captured
    
-   A→B→C (A<B<C) equals (B-A) + (C-B) = C-A
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - only profit variable

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                profit += prices[i] - prices[i-1];
            }
        }
        return profit;
    }
}
