# LeetCode 118 — Pascal's Triangle


Easy

Topic: Arrays, Dynamic Programming

Problem: Given an integer `numRows`, return the first `numRows` rows of Pascal's Triangle.

## 1. Understand the problem

In Pascal's Triangle:

- The first and last elements of every row are `1`.
- Every middle element is the sum of the two elements directly above it.

For example, if `numRows = 5`, the output is:

```
[
     [1],
    [1, 1],
   [1, 2, 1],
  [1, 3, 3, 1],
 [1, 4, 6, 4, 1]
]
```

## 2. How do we build each row?

Let's construct the triangle one row at a time.

## Row 1

1

## Row 2

1

1

Both ends are 1.

## Row 3

1

2

1

Middle: 1 + 1 = 2

## Row 4

1

3

3

1

Middle values: 1 + 2 = 3, and 2 + 1 = 3

The general rule for a middle element is:

\\[ \boxed{row[j]=prevRow[j-1]+prevRow[j]} \\]

Here, `prevRow` is the previous row, and `j` is the current element's index.

## 3. Java solution

```

import java.util.*;

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();

            // Every row starts with 1
            row.add(1);

            // Calculate the middle elements
            for (int j = 1; j < i; j++) {
                int value = triangle.get(i - 1).get(j - 1)
                          + triangle.get(i - 1).get(j);

                row.add(value);
            }

            // Every row ends with 1, except the first row
            if (i > 0) {
                row.add(1);
            }

            triangle.add(row);
        }

        return triangle;
    }
}

```

## 4. Dry run for `numRows = 4`

| `i` | Action                     | New row        |
| --- | -------------------------- | -------------- |
| 0   | Add `1`                    | `[1]`          |
| 1   | Add first and last `1`     | `[1, 1]`       |
| 2   | Middle = `1 + 1`           | `[1, 2, 1]`    |
| 3   | Middles = `1 + 2`, `2 + 1` | `[1, 3, 3, 1]` |

Final output:

```
[[1], [1, 1], [1, 2, 1], [1, 3, 3, 1]]
```

## 5. Complexity

- Time: \\(O(n^2)\\), because we generate all elements across `n` rows.
- Space: \\(O(n^2)\\), to store the triangle. The returned result itself requires this space.

## 6. Test your understanding

What is the fourth row of Pascal's Triangle, counting the first row as `[1]`?

`[1, 2, 1]`

`[1, 3, 3, 1]`

`[1, 4, 6, 4, 1]`

Interview takeaway: The key idea is that each middle element comes from adding two adjacent elements in the previous row. The first and last elements are always `1`.
