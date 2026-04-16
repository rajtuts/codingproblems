# Cheapest Flights Within K Stops
There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei. You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.

Example 1:
Input: {"n":3,"flights":[[0,1,100],[1,2,100],[0,2,500]],"src":0,"dst":2,"k":1}
Output: 200

**Approach: Bellman-Ford Variant**

**Key Insight:** K stops means at most K+1 edges. Run Bellman-Ford for exactly K+1 iterations, using previous iteration's prices to avoid path length violations.

**Algorithm:**

1.  Initialize prices\[src\] = 0, others = ∞
    
2.  For K+1 iterations:
    
    -   Create temp copy of prices
        
    -   For each edge (u,v,p): temp\[v\] = min(temp\[v\], prices\[u\] + p)
        
    -   prices = temp
        
3.  Return prices\[dst\] or -1 if unreachable
    

**Example:** n=4, flights=\[\[0,1,100\],\[1,2,100\],\[2,3,200\]\], src=0, dst=3, k=1

-   Iter 0: prices\[1\]=100
    
-   Iter 1: prices\[2\]=200, prices\[3\]=300
    
-   Result: 300 (but with k=1, only 2 edges allowed, need recheck)
    

**Time Complexity:** O(k \* E) - k+1 iterations over all edges **Space Complexity:** O(n) - prices array

**Time: O(k \* E)**

**Space: O(n)**

```
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] prices = new int[n];
        Arrays.fill(prices, Integer.MAX_VALUE);
        prices[src] = 0;
        
        for (int i = 0; i <= k; i++) {
            int[] tmpPrices = Arrays.copyOf(prices, n);
            for (int[] flight : flights) {
                int s = flight[0], d = flight[1], p = flight[2];
                if (prices[s] == Integer.MAX_VALUE) continue;
                if (prices[s] + p < tmpPrices[d]) {
                    tmpPrices[d] = prices[s] + p;
                }
            }
            prices = tmpPrices;
        }
        return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst];
    }
}
