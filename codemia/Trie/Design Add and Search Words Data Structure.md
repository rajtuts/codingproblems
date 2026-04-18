# Design Add and Search Words Data Structure
Design a data structure that supports adding new words and finding if a string matches any previously added string. Implement the WordDictionary class with addWord(word) and search(word) methods. search(word) can search a literal word or a regular expression string containing only letters a-z or '.'. A '.' means it can represent any one letter.

Example 1:
Input: {"commands":["WordDictionary","addWord","addWord","addWord","search","search","search","search"],"values":[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]}
Output: [null,null,null,null,false,true,true,true]

**Approach: Trie with DFS for Wildcard Search**

**Key Insight:** A Trie enables O(n) word insertion and search. For wildcards ('.'), we must explore ALL branches at that position using DFS.

**Trie Structure:**

`Each node has: - children: dict mapping char → child node - isEnd: boolean marking end of a word`

**addWord("bad"):**

`root → 'b' → 'a' → 'd' (isEnd=True)`

**search(".ad"):**

`At '.': Try ALL children of root - Try 'b' → 'a' → 'd' → isEnd=True ✓ - Try 'd' → 'a' → 'd' → isEnd=True ✓ - Try 'm' → 'a' → 'd' → isEnd=True ✓`

**Algorithm:**

1.  **addWord:** Standard Trie insertion
    
    -   Traverse/create nodes for each character
        
    -   Mark final node as isEnd=True
        
2.  **search:** DFS with wildcard handling
    
    -   If '.': recursively try ALL children
        
    -   If letter: follow specific path
        
    -   At end of word: check isEnd flag
        

**Time Complexity:**

-   addWord: O(n) where n = word length
    
-   search: O(26^d × n) where d = number of '.' wildcards
    

**Space Complexity:** O(total characters across all words)

**Time: O(n) for addWord, O(26^m) worst case for search with dots**

**Space: O(total characters in all words)**
```
class WordDictionary {
    private Map<Character, WordDictionary> children;
    private boolean isEnd;

    public WordDictionary() {
        children = new HashMap<>();
        isEnd = false;
    }

    public void addWord(String word) {
        WordDictionary node = this;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.children.put(c, new WordDictionary());
            }
            node = node.children.get(c);
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        return dfs(this, word, 0);
    }

    private boolean dfs(WordDictionary node, String word, int i) {
        if (i == word.length()) return node.isEnd;
        char c = word.charAt(i);
        if (c == '.') {
            for (WordDictionary child : node.children.values()) {
                if (dfs(child, word, i + 1)) return true;
            }
            return false;
        }
        if (!node.children.containsKey(c)) return false;
        return dfs(node.children.get(c), word, i + 1);
    }
}
```
