# LeetCode 121 — Best Time to Buy and Sell Stock

Easy

Topic: Arrays, Greedy

Problem: Given an array `prices`, where `prices[i]` is the stock price on day `i`, find the maximum profit you can achieve by buying on one day and selling on a later day.

You may complete only one transaction.

## 1. Understand the problem

Example:

```
prices = [7, 1, 5, 3, 6, 4]
```

You should buy at price `1` and sell later at price `6`.

\\[ \text{Profit}=6-1=\boxed{5} \\]

### Visualize the prices

<img width="1062" height="333" alt="image" src="https://github.com/user-attachments/assets/0e21274a-ee30-4185-ae03-0e3e9b6e2d6c" />

Important: You must buy before you sell. You cannot buy at `6` and then sell earlier at `1`.

## 2. The key idea — Track the minimum price

As you move through the array, maintain two variables:

- `minPrice`: the lowest stock price seen so far.
- `maxProfit`: the maximum profit found so far.

For each price:

1. Calculate the profit if you sell today: `price - minPrice`.
2. Update `maxProfit` if this profit is larger.
3. Update `minPrice` if today's price is lower.

The formula is:

\\[ \text{maxProfit}=\max(\text{maxProfit},\text{price}-\text{minPrice}) \\]

## 3. Dry run

For `prices = [7, 1, 5, 3, 6, 4]`:

| Day | Price | Minimum so far | Profit if sold today | Maximum profit |
| --- | ----- | -------------- | -------------------- | -------------- |
| 0   | 7     | 7              | 0                    | 0              |
| 1   | 1     | 1              | 0                    | 0              |
| 2   | 5     | 1              | 4                    | 4              |
| 3   | 3     | 1              | 2                    | 4              |
| 4   | 6     | 1              | 5                    | 5              |
| 5   | 4     | 1              | 3                    | 5              |

The answer is 5.

Notice that when we reach price `6`, selling gives us a profit of `6 - 1 = 5`, which becomes our maximum.

## 4. Java solution

```

class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            // Update the lowest buying price
            minPrice = Math.min(minPrice, price);

            // Calculate profit if we sell today
            int profit = price - minPrice;

            // Keep the best profit
            maxProfit = Math.max(maxProfit, profit);
        }

        return maxProfit;
    }
}

```

### How the code works

- `Integer.MAX_VALUE` initializes the minimum price to a very large number.
- `Math.min()` keeps track of the lowest price seen so far.
- `price - minPrice` calculates the profit from selling at the current price.
- `Math.max()` stores the highest profit found.

If prices always decrease, such as `[7, 6, 4, 3, 1]`, the answer is `0` because no profitable transaction is possible.

## 5. Complexity

- Time: \\(O(n)\\) — we visit every price exactly once.
- Extra space: \\(O(1)\\) — we only use two variables.

## 6. Test your understanding

Question: What is the maximum profit for this array?

`prices = [7, 2, 8, 1, 5]`

3

6

7

0

Interview takeaway: You do not need to check every possible buy-and-sell pair. Track the lowest buying price so far and calculate the profit at each subsequent price. This reduces the brute-force \\(O(n^2)\\) approach to \\(O(n)\\).
