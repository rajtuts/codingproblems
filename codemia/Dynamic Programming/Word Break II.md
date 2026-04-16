Word Break II
Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.

Example 1:
Input: {"s":"catsanddog","wordDict":["cat","cats","and","sand","dog"]}
Output: ["cats and dog","cat sand dog"]

Backtracking with memoization. At each position, try all matching words and recursively solve for the rest.

Time: O(2^n * n)
Space: O(2^n * n)

```
class Solution {
    private Set<String> wordSet;
    private Map<Integer, List<String>> memo;

    public List<String> wordBreak(String s, List<String> wordDict) {
        wordSet = new HashSet<>(wordDict);
        memo = new HashMap<>();
        return backtrack(s, 0);
    }

    private List<String> backtrack(String s, int start) {
        if (memo.containsKey(start)) return memo.get(start);

        List<String> result = new ArrayList<>();
        if (start == s.length()) {
            result.add("");
            return result;
        }

        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (wordSet.contains(word)) {
                for (String sentence : backtrack(s, end)) {
                    if (sentence.isEmpty()) {
                        result.add(word);
                    } else {
                        result.add(word + " " + sentence);
                    }
                }
            }
        }

        memo.put(start, result);
        return result;
    }
}
```
