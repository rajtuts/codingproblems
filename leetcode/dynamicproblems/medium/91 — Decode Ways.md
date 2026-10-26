# LeetCode 91 — Decode Ways

<img width="385" height="438" alt="image" src="https://github.com/user-attachments/assets/03ce8b8b-2776-42d5-8107-b951adf51142" />

Medium

Topic: Dynamic Programming, Strings

Problem: A string of digits encodes letters using the mapping `1 → A`, `2 → B`, ..., `26 → Z`. Return the number of ways to decode the digit string.

## 1. Understand the problem

Example:

```
Input:  s = "226"
Output: 3
```

The three possible decodings are:

`2 | 2 | 6`

B B F

`22 | 6`

V F

`2 | 26`

B Z

Answer: 3 ways

Important rules:

- A single digit from `1` to `9` can be decoded.
- `0` cannot be decoded by itself.
- Two consecutive digits can be decoded together only if their value is between `10` and `26`.
- A leading zero or a zero not paired with a valid preceding digit makes the string invalid.

## 2. The dynamic programming idea

Let `dp[i]` represent the number of ways to decode the first `i` digits of the string.

We use two possibilities at each position.

### Case 1: Take one digit

If the current digit is not `0`, it can represent a letter by itself.

\\[ dp[i] \mathrel{+}= dp[i-1] \\]

For example, in `"226"`, the final `6` can be decoded separately as `F`.

### Case 2: Take two digits

If the current digit and the previous digit form a number between `10` and `26`, they can represent one letter together.

\\[ dp[i] \mathrel{+}= dp[i-2] \\]

For example, `"26"` can be decoded together as `Z`.

Both cases can contribute to the answer, so we add their counts when valid.

## 3. Java solution

```

class Solution {
    public int numDecodings(String s) {
        int n = s.length();

        if (n == 0 || s.charAt(0) == '0') {
            return 0;
        }

        int[] dp = new int[n + 1];

        // Empty string has one way to decode
        dp[0] = 1;

        // A non-zero first digit has one way
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            char current = s.charAt(i - 1);
            char previous = s.charAt(i - 2);

            // Case 1: Decode one digit
            if (current != '0') {
                dp[i] += dp[i - 1];
            }

            // Case 2: Decode two digits
            int twoDigit = (previous - '0') * 10
                         + (current - '0');

            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }

            // No valid decoding reaches this prefix
            if (dp[i] == 0) {
                return 0;
            }
        }

        return dp[n];
    }
}

```

## 4. Dry run for `"226"`

Initialize:

- `dp[0] = 1` — one way to decode an empty prefix, which helps build the solution.
- `dp[1] = 1` — `"2"` decodes as `B`.

Then process the remaining digits.

| Prefix  | One-digit option | Two-digit option            | Total ways |
| ------- | ---------------- | --------------------------- | ---------- |
| `"2"`   | Base case        | —                           | 1          |
| `"22"`  | `dp[1] = 1`      | `dp[0] = 1` (`22` is valid) | 2          |
| `"226"` | `dp[2] = 2`      | `dp[1] = 1` (`26` is valid) | 3          |

Therefore, `dp[3] = 3`.

## 5. Why zeros matter

Consider these examples:

| Input   | Output | Reason                                   |
| ------- | ------ | ---------------------------------------- |
| `"10"`  | 1      | `10 → J`                                 |
| `"20"`  | 1      | `20 → T`                                 |
| `"06"`  | 0      | Cannot decode a leading zero             |
| `"30"`  | 0      | Neither `3` nor `30` can decode the zero |
| `"111"` | 3      | `A A A`, `K A`, or `A K`                 |

The most common mistake is counting a zero as an independent letter. A zero is valid only when paired with `1` or `2` to form `10` or `20`.

## 6. Complexity

- Time: \\(O(n)\\), because each digit is processed once.
- Space: \\(O(n)\\), for the DP array.

## 7. Test your understanding

How many ways can `"1111"` be decoded?

3

4

5

8

Interview takeaway: At each position, ask: “Can I decode the last digit alone? Can I decode the last two digits together?” Add the valid possibilities. This is the central idea behind LeetCode 91.
