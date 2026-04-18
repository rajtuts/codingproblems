# Concatenated Words
Given an array of strings words (without duplicates), return all the concatenated words in the given list. A concatenated word is a string that is comprised entirely of at least two shorter words in the given array.

Example 1:
Input: {"words":["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]}
Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]

Put words in set. For each word, try splitting. If prefix in set and suffix in set or can be formed recursively, it's concatenated. Memoize results.

Time: O(n * L^3)
Space: O(n * L)

```
class Solution {
    Set<String> wordSet;
    Map<String, Boolean> memo = new HashMap<>();

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        wordSet = new HashSet<>(Arrays.asList(words));
        List<String> result = new ArrayList<>();

        for (String word : words) {
            if (word.length() > 0 && canForm(word)) {
                result.add(word);
            }
        }

        return result;
    }

    private boolean canForm(String word) {
        if (memo.containsKey(word)) return memo.get(word);

        for (int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);

            if (wordSet.contains(prefix)) {
                if (wordSet.contains(suffix) || canForm(suffix)) {
                    memo.put(word, true);
                    return true;
                }
            }
        }

        memo.put(word, false);
        return false;
    }
}
```
