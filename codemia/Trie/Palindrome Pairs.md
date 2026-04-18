# Palindrome Pairs
Given a list of unique words, return all pairs of distinct indices (i, j) such that the concatenation of words[i] + words[j] is a palindrome.

Example 1:
Input: {"words":["abcd","dcba","lls","s","sssll"]}
Output: [[0,1],[1,0],[3,2],[2,4]]

For each word, split at each position. If prefix is palindrome and reversed suffix exists, it's a valid pair. Same for suffix.

Time: O(n * k^2)
Space: O(n * k)

```
class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        Map<String, Integer> wordMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            wordMap.put(words[i], i);
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 0; j <= word.length(); j++) {
                String prefix = word.substring(0, j);
                String suffix = word.substring(j);

                if (isPalindrome(prefix)) {
                    String reversedSuffix = new StringBuilder(suffix).reverse().toString();
                    if (wordMap.containsKey(reversedSuffix) && wordMap.get(reversedSuffix) != i) {
                        result.add(Arrays.asList(wordMap.get(reversedSuffix), i));
                    }
                }

                if (j != word.length() && isPalindrome(suffix)) {
                    String reversedPrefix = new StringBuilder(prefix).reverse().toString();
                    if (wordMap.containsKey(reversedPrefix) && wordMap.get(reversedPrefix) != i) {
                        result.add(Arrays.asList(i, wordMap.get(reversedPrefix)));
                    }
                }
            }
        }

        return result;
    }

    private boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
}
````
