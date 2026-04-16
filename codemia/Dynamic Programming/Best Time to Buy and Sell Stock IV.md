# Best Time to Buy and Sell Stock IV
You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k. Find the maximum profit you can achieve. You may complete at most k transactions. Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
Input: {"k":2,"prices":[3,2,6,5,0,3]}
Output: 7

**Approach: DP with Optimization**

**Key Insight:** dp\[i\]\[j\] = max profit using at most i transactions on days 0..j. Optimize by tracking max\_diff = max(dp\[i-1\]\[t\] - prices\[t\]) for all t < j.

**Algorithm:**

1.  If k >= n/2: unlimited transactions → sum all gains
    
2.  Create dp\[k+1\]\[n\] initialized to 0
    
3.  For each transaction count i from 1 to k:
    
    -   max\_diff = -prices\[0\] (best buy opportunity so far)
        
    -   For each day j from 1 to n-1:
        
        -   dp\[i\]\[j\] = max(dp\[i\]\[j-1\], prices\[j\] + max\_diff)
            
        -   max\_diff = max(max\_diff, dp\[i-1\]\[j\] - prices\[j\])
            
4.  Return dp\[k\]\[n-1\]
    

**Example:** k=2, prices=\[2,4,1\]

-   dp\[1\]\[0\]=0, dp\[1\]\[1\]=2 (buy at 2, sell at 4)
    
-   dp\[1\]\[2\]=2 (holding)
    
-   dp\[2\]\[0\]=0, dp\[2\]\[1\]=2, dp\[2\]\[2\]=2
    
-   Result: 2 ✓
    

**Why max\_diff:**

-   At day j, we want max of (sell today with previous buy)
    
-   dp\[i-1\]\[t\] - prices\[t\] = profit from i-1 transactions minus buy cost
    
-   prices\[j\] + max\_diff = sell at j with best prior state
    

**Time Complexity:** O(k×n) - with max\_diff optimization **Space Complexity:** O(k×n) - DP table

**Time: O(k\*n)**

**Space: O(k\*n)**

```
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) return 0;
        int n = prices.length;
        // If k >= n/2, unlimited transactions
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i-1]) {
                    profit += prices[i] - prices[i-1];
                }
            }
            return profit;
        }

        // buy[i] = min cost to buy for i-th transaction
        // sell[i] = max profit after i-th transaction
        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];
        Arrays.fill(buy, Integer.MAX_VALUE);

        for (int price : prices) {
            for (int i = 1; i <= k; i++) {
                buy[i] = Math.min(buy[i], price - sell[i-1]);
                sell[i] = Math.max(sell[i], price - buy[i]);
            }
        }
        return sell[k];
    }
}
```
