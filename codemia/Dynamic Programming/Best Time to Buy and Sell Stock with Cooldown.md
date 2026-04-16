# Best Time to Buy and Sell Stock with Cooldown
Find the maximum profit with a cooldown period of 1 day after selling (cannot buy the next day after selling).

Example 1:
Input: [1,2,3,0,2]
Output: 3

**Approach: State Machine DP**

**Key Insight:** At each day, you're in one of three states: holding stock, just sold (cooldown next), or resting (can buy). Track maximum profit for each state.

**Algorithm:**

1.  States: hold (own stock), sold (just sold), rest (can buy)
    
2.  Transitions:
    
    -   hold = max(hold, rest - price) (keep holding or buy)
        
    -   sold = hold + price (sell what we hold)
        
    -   rest = max(rest, sold) (keep resting or finished cooldown)
        
3.  Initialize: hold = -prices\[0\], sold = 0, rest = 0
    
4.  Return max(sold, rest)
    

**Example:** prices = \[1,2,3,0,2\]

-   Day 0: hold=-1, sold=0, rest=0
    
-   Day 1: hold=max(-1,0-2)=-1, sold=-1+2=1, rest=max(0,0)=0
    
-   Day 2: hold=max(-1,0-3)=-1, sold=-1+3=2, rest=max(0,1)=1
    
-   Day 3: hold=max(-1,1-0)=1, sold=-1+0=-1, rest=max(1,2)=2
    
-   Day 4: hold=max(1,2-2)=1, sold=1+2=3, rest=max(2,-1)=2
    
-   Result: max(3, 2) = 3 ✓
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - three state variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length <= 1) return 0;
        int hold = -prices[0];
        int sold = 0;
        int rest = 0;
        for (int i = 1; i < prices.length; i++) {
            int prevHold = hold;
            hold = Math.max(hold, rest - prices[i]);
            rest = Math.max(rest, sold);
            sold = prevHold + prices[i];
        }
        return Math.max(sold, rest);
    }
}
```
