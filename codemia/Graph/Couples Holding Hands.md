# Couples Holding Hands
N couples sit in 2N seats arranged in a row. You want every couple to sit side by side. Couples are numbered (0,1), (2,3), etc. A swap consists of choosing any two people and having them swap seats. Return the minimum number of swaps so that every couple is sitting side by side.

Example 1:
Input: {"row":[0,2,1,3]}
Output: 1

Greedy approach. For each pair of seats, if not a couple, find the partner and swap. Partner of x is x^1. Track positions for O(1) swaps. Each swap fixes one couple.

Time: O(n)
Space: O(n)

```
class Solution {
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) pos[row[i]] = i;
        int swaps = 0;

        for (int i = 0; i < n; i += 2) {
            int first = row[i];
            int partner = first ^ 1;

            if (row[i + 1] != partner) {
                int partnerPos = pos[partner];
                pos[row[i + 1]] = partnerPos;
                pos[partner] = i + 1;
                row[partnerPos] = row[i + 1];
                row[i + 1] = partner;
                swaps++;
            }
        }

        return swaps;
    }
}

```
