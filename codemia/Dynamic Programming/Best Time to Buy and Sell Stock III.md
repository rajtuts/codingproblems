# Best Time to Buy and Sell Stock III
Find the maximum profit with at most two transactions. You may not engage in multiple transactions simultaneously (you must sell the stock before you buy again).

Example 1:
Input: [3,3,5,0,0,3,1,4]
Output: 6

**Approach: State Machine DP**

**Key Insight:** Track four states: cost after first buy, profit after first sell, cost after second buy, profit after second sell. Each state builds on the previous.

**Algorithm:**

1.  Initialize: buy1 = buy2 = ∞, sell1 = sell2 = 0
    
2.  For each price:
    
    -   buy1 = min(buy1, price) — minimize first buy cost
        
    -   sell1 = max(sell1, price - buy1) — maximize first profit
        
    -   buy2 = min(buy2, price - sell1) — second buy cost includes first profit
        
    -   sell2 = max(sell2, price - buy2) — maximize total profit
        
3.  Return sell2
    

**Example:** prices = \[3,3,5,0,0,3,1,4\]

-   At 5: buy1=3, sell1=2, buy2=3, sell2=2
    
-   At 0: buy1=0, sell1=2, buy2=-2, sell2=2
    
-   At 4: buy1=0, sell1=4, buy2=-2, sell2=6 ✓
    

**Why This Works:**

-   sell1 represents best profit from one transaction
    
-   buy2 = price - sell1 effectively "uses" first profit to reduce second buy cost
    
-   sell2 accumulates total from both transactions
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - four variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxProfit(int[] prices) {
        int buy1 = Integer.MAX_VALUE, buy2 = Integer.MAX_VALUE;
        int sell1 = 0, sell2 = 0;
        for (int price : prices) {
            buy1 = Math.min(buy1, price);
            sell1 = Math.max(sell1, price - buy1);
            buy2 = Math.min(buy2, price - sell1);
            sell2 = Math.max(sell2, price - buy2);
        }
        return sell2;
    }
}
```
