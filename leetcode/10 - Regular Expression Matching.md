# LeetCode 10: Regular Expression Matching

Hard

Dynamic Programming

String

The goal is to determine whether a string `s` matches a pattern `p` completely, using two special characters:

- `.` — matches any single character.
- `*` — matches zero or more occurrences of the character immediately before it.

## 1. Understand the problem

Example 1:

```
s = "aa"
p = "a"

Output: false
```

The pattern `"a"` matches only one character, but the string contains two characters. The entire string must match.

Example 2:

```
s = "aa"
p = "a*"

Output: true
```

Here, `a*` means zero or more `a` characters, so it can match both `a` characters.

Example 3:

```
s = "ab"
p = ".*"

Output: true
```

- `.` matches any character.
- `*` allows the preceding `.` to repeat zero or more times.

Therefore, `.*` can match `"ab"` or any other string.

## 2. Why use dynamic programming?

The main difficulty is `*`, because it gives us two choices:

1. Match zero occurrences of the preceding character.
2. Match one or more occurrences of the preceding character.

Dynamic programming lets us store the answers to smaller matching problems instead of repeatedly checking them.

Define:

\\[ dp[i][j] \\]

as whether the first \\(i\\) characters of `s` match the first \\(j\\) characters of `p`.

For example:

\\[ dp[2][3] \\]

means: do the first two characters of `s` match the first three characters of `p`?

## 3. Java solution

```

class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] dp = new boolean[m + 1][n + 1];

        // Empty string matches empty pattern
        dp[0][0] = true;

        // Handle patterns such as a*, a*b*, a*b*c*
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);

                if (pc == '.' || pc == sc) {
                    // Current characters match
                    dp[i][j] = dp[i - 1][j - 1];

                } else if (pc == '*') {
                    char prev = p.charAt(j - 2);

                    // Case 1: '*' represents zero occurrences
                    dp[i][j] = dp[i][j - 2];

                    // Case 2: '*' represents one or more occurrences
                    if (prev == '.' || prev == sc) {
                        dp[i][j] = dp[i][j]
                                || dp[i - 1][j];
                    }
                }
            }
        }

        return dp[m][n];
    }
}

```

## 4. Understand the transitions

There are three main cases.

### Case 1: The pattern character is equal to the string character, or is `.`

If the current characters match, the result depends on whether the previous prefixes matched.

```
if (pc == '.' || pc == sc) {
    dp[i][j] = dp[i - 1][j - 1];
}
```

For example, `"a"` matches `"a"`, and `"b"` matches `"."`.

### Case 2: The pattern character is `*`

This is the most important part of the problem.

Option A: `*` matches zero occurrences.

```
dp[i][j] = dp[i][j - 2];
```

We remove the preceding character and `*` from the pattern.

For example:

```
s = "b"
p = "ab*"
```

The pattern `b*` can represent an empty string, leaving `"a"` to match `"b"`—which is false.

More generally, this transition skips the pair and checks whether the shorter pattern matches the same string prefix.

Option B: `*` matches one or more occurrences.

```
if (prev == '.' || prev == sc) {
    dp[i][j] = dp[i][j] || dp[i - 1][j];
}
```

If the character before `*` matches the current string character, we can consume that character from the string while keeping the pattern position unchanged.

For example:

```
s = "aa"
p = "a*"
```

The second `a` can be matched by the same `a*` pattern, so we look at `dp[i - 1][j]`.

## 5. Dry run: `s = "aa"`, `p = "a*"`

The DP table looks like this:

Rows represent prefixes of `s`; columns represent prefixes of `p`.

| s \ p | "" | "a" | "a"\* |
| ----- | -- | --- | ----- |
| ""    | T  | F   | T     |
| "a"   | F  | T   | T     |
| "aa"  | F  | F   | T     |

Final answer: true

The key calculation for `dp[2][2]` (the full string and full pattern) is:

- `p.charAt(1)` is `*`.
- The preceding character is `a`, which matches the current string character.
- Therefore, we can use `dp[1][2]`, which is true.

So the entire string matches `a*`.

## 6. Complexity analysis

Let \\(m\\) be the length of `s` and \\(n\\) the length of `p`.

- Time complexity: \\(O(mn)\\), because each DP cell takes constant time.
- Space complexity: \\(O(mn)\\), for the two-dimensional DP table.

## 7. Interview tips

Remember these three rules:

| Pattern condition                    | DP transition  |
| ------------------------------------ | -------------- |
| Character matches or pattern has `.` | `dp[i-1][j-1]` |
| `*` matches zero occurrences         | `dp[i][j-2]`   |
| `*` matches another character        | `dp[i-1][j]`   |

Most important: `*` always applies to the character immediately before it. It does not independently match any character—that is the job of `.`.
