# Best Time to Buy and Sell Stock
You are given an array prices where prices[i] is the price of a given stock on the ith day. You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.

Example 1:
Input: [7,1,5,3,6,4]
Output: 5

**Approach: One Pass - Track Minimum and Maximum Profit**

**Key Insight:** We want to buy at the lowest price BEFORE we sell. Keep track of the minimum price seen so far, and at each point calculate the potential profit if we sold today.

**Algorithm:**

1.  Initialize min\_price = infinity, max\_profit = 0
    
2.  For each price:
    
    -   Update min\_price = min(min\_price, price)
        
    -   Update max\_profit = max(max\_profit, price - min\_price)
        
3.  Return max\_profit
    

**Why it works:** At each day, we know the best buying price from all previous days. The maximum profit is the best selling opportunity we encounter.

**Example:** prices = \[7,1,5,3,6,4\]

-   Day 0: min=7, profit=0
    
-   Day 1: min=1, profit=0 (1-1=0)
    
-   Day 2: min=1, profit=4 (5-1=4)
    
-   Day 3: min=1, profit=4 (3-1=2, less than 4)
    
-   Day 4: min=1, profit=5 (6-1=5) ← best!
    
-   Day 5: min=1, profit=5 (4-1=3, less than 5)
    

**Edge Case:** Decreasing prices → no profit possible, return 0

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxProfit(int[] prices) {
        int l = 0;
        int r = 1;
        int maxP = 0;
        
        while (r < prices.length) {
            if (prices[l] < prices[r]) {
                int profit = prices[r] - prices[l];
                maxP = Math.max(maxP, profit);
            } else {
                l = r;
            }
            r++;
        }
        return maxP;
    }
}
