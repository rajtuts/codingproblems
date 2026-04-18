# Prefix and Suffix Search
Design a special dictionary that searches words by a prefix and a suffix. Implement WordFilter with constructor that takes words array, and f(prefix, suffix) that returns the index of the word with given prefix and suffix. If multiple words match, return the largest index.

Example 1:
Input: {"words":["apple"],"queries":[["a","e"]]}
Output: [0]

In constructor, generate all prefix#suffix combinations for each word and store in map. Later indices override earlier ones. Query is O(1) hash lookup.

Time: O(n * L^3) for constructor, O(1) for query
Space: O(n * L^3)

```
class WordFilter {
    Map<String, Integer> map;

    public WordFilter(String[] words) {
        map = new HashMap<>();
        for (int idx = 0; idx < words.length; idx++) {
            String word = words[idx];
            int n = word.length();
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    String key = word.substring(0, i) + "#" + word.substring(n - j);
                    map.put(key, idx);
                }
            }
        }
    }

    public int f(String pref, String suff) {
        String key = pref + "#" + suff;
        return map.getOrDefault(key, -1);
    }
}
```
