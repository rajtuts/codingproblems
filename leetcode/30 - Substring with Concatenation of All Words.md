# LeetCode 30: Substring with Concatenation of All Words



Hard

Sliding Window



## 1. Problem explanation

You are given:

- A string `s`
- An array of strings `words`, where every word has the same length

Find all starting indices in `s` where a substring contains every word in `words` exactly once, concatenated together, in any order.

### Example

```
s = "barfoothefoobarman"
words = ["foo", "bar"]
```

The answer is:

```
[0, 9]
```

Why?

Index 0

barfoo

`"barfoo"` contains `"bar"` and `"foo"` exactly once.

Index 9

foobar

`"foobar"` also contains `"foo"` and `"bar"` exactly once.

The order does not matter. Both `"barfoo"` and `"foobar"` are valid.

## 2. Key observation

Suppose:

```
words = ["foo", "bar"]
```

Each word has length `3`, and there are `2` words.

Therefore, the required substring length is:

\\[ \text{windowSize} = 3 \times 2 = 6 \\]

We must examine substrings of length `6`, but checking every possible substring independently would be inefficient.

The key idea: use a sliding window that processes one word at a time, rather than one character at a time.

## 3. Approach: Sliding window + HashMap

We use two frequency maps:

- `wordCount`: the required frequency of each word.
- `windowCount`: the frequency of each word in the current window.

For example:

```
wordCount = {
    "foo": 1,
    "bar": 1
}
```

A window is valid when its word frequencies match the required frequencies.

### Step-by-step walkthrough

For `s = "barfoothefoobarman"`:

Step 1 — Read words of length 3

### bar

### foo

### the

### foo

### bar

### man

Step 2 — Combine consecutive words into windows of 2

1. `bar + foo` → valid, index `0`
2. `foo + the` → invalid
3. `the + foo` → invalid
4. `foo + bar` → valid, index `9`
5. `bar + man` → invalid

Final answer: `[0, 9]`

In an optimized implementation, we don't restart the counting process for every window. We add a word when the window expands and remove a word when the window moves forward.

## 4. Python solution

```

import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();

        if (s == null || words == null ||
            s.length() == 0 || words.length == 0) {
            return result;
        }

        int wordLen = words[0].length();
        int wordCount = words.length;
        int windowSize = wordLen * wordCount;

        if (s.length() < windowSize) {
            return result;
        }

        Map<String, Integer> required = new HashMap<>();

        for (String word : words) {
            required.put(word,
                required.getOrDefault(word, 0) + 1);
        }

        // Try each possible alignment of word-sized chunks
        for (int offset = 0; offset < wordLen; offset++) {
            int left = offset;
            int count = 0;

            Map<String, Integer> seen = new HashMap<>();

            for (int right = offset;
                 right + wordLen <= s.length();
                 right += wordLen) {

                String word = s.substring(right, right + wordLen);

                // If the word is not required, reset the window
                if (!required.containsKey(word)) {
                    seen.clear();
                    count = 0;
                    left = right + wordLen;
                    continue;
                }

                seen.put(word, seen.getOrDefault(word, 0) + 1);
                count++;

                // Shrink while this word occurs too many times
                while (seen.get(word) > required.get(word)) {
                    String leftWord =
                        s.substring(left, left + wordLen);

                    seen.put(leftWord, seen.get(leftWord) - 1);
                    count--;
                    left += wordLen;
                }

                // All words are present with valid frequencies
                if (count == wordCount) {
                    result.add(left);

                    // Move forward to allow overlapping matches
                    String leftWord =
                        s.substring(left, left + wordLen);

                    seen.put(leftWord, seen.get(leftWord) - 1);
                    count--;
                    left += wordLen;
                }
            }
        }

        return result;
    }
}
```

Example
```
Input:
s = "barfoothefoobarman"
words = ["foo", "bar"]

Output:
[0, 9]
```

## 5. How the code works

1. Calculate the word length. Every word has the same length, so we can split the string into chunks of that size.
2. Try each offset. If each word has length `3`, start at indices `0`, `1`, and `2`. This ensures that all possible alignments are checked.
3. Expand the window. Read the next word-sized chunk and increment its frequency.
4. Handle invalid words. If a chunk isn't in `words`, clear the current window and start after that chunk.
5. Remove excess words. If a word appears more often than required, move the left boundary forward until its count is valid.
6. Record matches. When the window contains exactly the required number of words, save its starting index and move forward to search for overlapping matches.

## 6. Complexity analysis

Let \\(n\\) be the length of `s`, \\(m\\) the number of words, and \\(k\\) the length of each word.

- Time: \\(O(n \cdot k)\\) under typical substring-hashing assumptions in Python.
- Space: \\(O(m)\\) for the frequency maps, excluding the returned indices and substring storage details.

## 7. Interview takeaway

Remember this pattern:

- Fixed word length → move the sliding window in chunks of that length.
- Required word frequencies → use a hash map or counter.
- Too many occurrences → shrink the window.
- Invalid word → reset the current window.
- Multiple alignments → iterate over each offset from `0` to `word_len - 1`.

This problem is especially useful for learning how sliding windows work when the window contains words rather than individual characters.
