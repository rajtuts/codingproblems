# LeetCode 72 — Edit Distance

Hard
Topic: Dynamic Programming

Problem: Given two strings word1 and word2, find the minimum number of operations needed to convert word1 into word2.

You can perform three operations:
- Insert a character
- Delete a character
- Replace a character


1. Understand the problem
Example:
word1 = "horse"
word2 = "ros"

Output = 3

<img width="1081" height="430" alt="image" src="https://github.com/user-attachments/assets/69c5fb54-bf31-40b7-8fdb-dece9f0d5d29" />

One way to convert "horse" into "ros" is:
horse

rorse

Replace h with r

rorse

rose

Delete r

rose

ros

Delete e


Total operations = 3

2. Dynamic programming idea
Let dp[i][j] represent:

The minimum operations required to convert the first i characters of word1 into the first j characters of word2.

For example:
- dp[0][0]: convert empty string to empty string → 0
- dp[3][0]: convert "hor" to empty string → 3 deletions
- dp[0][2]: convert empty string to "ro" → 2 insertions
Initialize the DP table
If one string is empty, the answer is the length of the other string.
\[
dp[i][0]=i
\]

\[
dp[0][j]=j
\]

3. The three transitions
Suppose the current characters are word1[i-1] and word2[j-1].

Case 1: Characters match

If the characters are equal, no new operation is needed.
\[
dp[i][j]=dp[i-1][j-1]
\]

Example: "ca" → "ca"; matching the final a adds zero operations.




Case 2: Insert

Insert the required character into word1.
\[
dp[i][j]=dp[i][j-1]+1
\]

We use dp[i][j-1] because the inserted character completes the target prefix.




Case 3: Delete

Delete the current character from word1.
\[
dp[i][j]=dp[i-1][j]+1
\]





Case 4: Replace

When the characters differ, replace one with the other.
\[
dp[i][j]=dp[i-1][j-1]+1
\]




When the characters differ, choose the cheapest of the three operations:
\[
dp[i][j]=1+\min
\begin{cases}
dp[i-1][j] & \text{delete}\\
dp[i][j-1] & \text{insert}\\
dp[i-1][j-1] & \text{replace}
\end{cases}
\]

4. Visualize a smaller example
Convert word1 = "ab" to word2 = "ac".

Each cell shows the minimum operations needed to convert the prefix on the left into the prefix above.
word1 ↓ / word2 →	""	a	ac
""	0	1	2
a	1	0	1
ab	2	1	1

The final answer is dp[2][2] = 1: replace b with c.

5. Java solution

class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        // Convert a prefix to an empty string: delete all characters
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // Convert an empty string to a prefix: insert all characters
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match: no extra operation
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Choose insert, delete, or replace
                    dp[i][j] = 1 + Math.min(
                        dp[i][j - 1], Math.min(
                            dp[i - 1][j],
                            dp[i - 1][j - 1]
                        )
                    );
                }
            }
        }

        return dp[m][n];
    }
}



6. Complexity
- Time: \(O(mn)\), because each DP cell is calculated once.
- Space: \(O(mn)\), for the two-dimensional DP table.
Here, m and n are the lengths of the two strings.
7. Interview takeaways
Remember these three rules:
- Match: diagonal, no extra cost.
- Different: take the minimum of insert, delete, and replace, then add 1.
- Answer: dp[m][n], the bottom-right cell.
A useful way to remember the transitions is: delete moves up, insert moves left, and replace moves diagonally.
