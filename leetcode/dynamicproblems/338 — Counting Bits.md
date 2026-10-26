# LeetCode 338 — Counting Bits

<img width="735" height="808" alt="image" src="https://github.com/user-attachments/assets/5c4fd5a9-ada4-40ab-9b8d-003fc498b2db" />

Easy

Topic: Dynamic Programming, Bit Manipulation

Problem: Given an integer `n`, return an array `ans` of length `n + 1`, where `ans[i]` is the number of `1`s in the binary representation of `i`.

## 1. Understand the problem

For `n = 5`, we need to count how many `1` bits appear in every number from `0` to `5`.

| Number | Binary | Count of `1`s |
| ------ | ------ | ------------- |
| 0      | `000`  | 0             |
| 1      | `001`  | 1             |
| 2      | `010`  | 1             |
| 3      | `011`  | 2             |
| 4      | `100`  | 1             |
| 5      | `101`  | 2             |

Output:

```
[0, 1, 1, 2, 1, 2]
```

The goal is to solve this efficiently without counting every bit from scratch.

## 2. Key dynamic programming idea

The most useful observation is:

\\[ \boxed{ans[i]=ans[i \mathbin{>>} 1]+(i \mathbin{\&} 1)} \\]

Here, `>> 1` shifts the binary number one position to the right, and `& 1` checks its last bit.

Why does this work?

- `i >> 1` removes the last binary bit.
- `i & 1` is `1` if the last bit is `1`, otherwise `0`.
- We already know the count for the smaller number `i >> 1`.

### Example: Calculate `ans[5]`

The binary representation of `5` is `101`.

Step 1 — Shift right

## 101

5 in binary

## 10

2 in binary

Step 2 — Count the last bit

`5 & 1 = 1`, because `101` ends in `1`.

Step 3 — Reuse the previous answer

\\[ ans[5]=ans[2]+1=1+1=2 \\]

## 3. Java solution — Dynamic programming

```

class Solution {
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }

        return ans;
    }
}

```

### Line-by-line explanation

- `int[] ans = new int[n + 1];` creates the result array. Java initializes all entries to `0`, so `ans[0] = 0`.
- `i >> 1` gives the number after removing the last binary bit.
- `i & 1` tells us whether that last bit is `1`.
- `ans[i >> 1]` reuses a count we have already calculated.

We process numbers in increasing order, so the smaller answer is always available.

## 4. Dry run for `n = 5`

| `i` | `i >> 1` | `i & 1` | Calculation  | `ans[i]` |
| --- | -------- | ------- | ------------ | -------- |
| 0   | —        | —       | Base case    | 0        |
| 1   | 0        | 1       | `ans[0] + 1` | 1        |
| 2   | 1        | 0       | `ans[1] + 0` | 1        |
| 3   | 1        | 1       | `ans[1] + 1` | 2        |
| 4   | 2        | 0       | `ans[2] + 0` | 1        |
| 5   | 2        | 1       | `ans[2] + 1` | 2        |

Final array:

`[0, 1, 1, 2, 1, 2]`

## 5. Another way to remember it

You can also use this equivalent recurrence:

\\[ \boxed{ans[i]=ans[i \mathbin{\&}(i-1)]+1} \\]

The expression `i & (i - 1)` removes the rightmost set bit (the rightmost `1`) from `i`.

For example, `6` is `110` in binary:

- `6 & 5` gives `100`, which is `4`.
- `ans[6] = ans[4] + 1 = 1 + 1 = 2`.

Both recurrences are valid. The right-shift approach is often easiest to understand when first learning dynamic programming.

## 6. Complexity

- Time: \\(O(n)\\), because each number is processed once.
- Space: \\(O(n)\\), for the output array.

## 7. Test your understanding

What is `ans[7]`?

Binary representation of `7` is `111`.

1

2

3

4

Interview takeaway: Instead of recounting the bits in every number, use a previously computed answer. Remember:

`ans[i] = ans[i >> 1] + (i & 1)`

This is a classic example of combining bit manipulation with dynamic programming.
