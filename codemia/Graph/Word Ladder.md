# Word Ladder
Given two words beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord (changing one letter at a time).

Example 1:
Input: {"beginWord":"hit","endWord":"cog","wordList":["hot","dot","dog","lot","log","cog"]}
Output: 5

BFS to find shortest path. Each word is a node, edges connect words differing by one letter.

Time: O(M²*N)
Space: O(M²*N)

```
class Solution {
    public int ladderLength(String beginWord, String endWord, java.util.List<String> wordList) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;

        java.util.Queue<String> queue = new java.util.LinkedList<>();
        java.util.Set<String> visited = new java.util.HashSet<>();
        queue.offer(beginWord);
        visited.add(beginWord);
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                String word = queue.poll();
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char orig = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;
                        String newWord = new String(chars);
                        if (newWord.equals(endWord)) return level + 1;
                        if (wordSet.contains(newWord) && !visited.contains(newWord)) {
                            visited.add(newWord);
                            queue.offer(newWord);
                        }
                    }
                    chars[i] = orig;
                }
            }
            level++;
        }
        return 0;
    }
}
```
