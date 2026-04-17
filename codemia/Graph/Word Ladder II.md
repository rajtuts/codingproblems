# Word Ladder II
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that every adjacent pair differs by a single letter, every si is in wordList, and sk == endWord. Given two words and a dictionary, return all the shortest transformation sequences.

Example 1:
Input: {"beginWord":"hit","endWord":"cog","wordList":["hot","dot","dog","lot","log","cog"]}
Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]

BFS level by level, tracking all paths. Remove visited words after each level to ensure shortest paths only.

Time: O(N * M * 26)
Space: O(N * M)

```
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return new ArrayList<>();

        Map<String, List<List<String>>> layer = new HashMap<>();
        layer.put(beginWord, new ArrayList<>());
        layer.get(beginWord).add(Arrays.asList(beginWord));
        wordSet.remove(beginWord);

        while (!layer.isEmpty()) {
            Map<String, List<List<String>>> newLayer = new HashMap<>();
            for (String word : layer.keySet()) {
                if (word.equals(endWord)) return layer.get(endWord);
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char orig = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;
                        String newWord = new String(chars);
                        if (wordSet.contains(newWord)) {
                            newLayer.putIfAbsent(newWord, new ArrayList<>());
                            for (List<String> path : layer.get(word)) {
                                List<String> newPath = new ArrayList<>(path);
                                newPath.add(newWord);
                                newLayer.get(newWord).add(newPath);
                            }
                        }
                    }
                    chars[i] = orig;
                }
            }
            wordSet.removeAll(newLayer.keySet());
            layer = newLayer;
        }

        return new ArrayList<>();
    }
}
```
