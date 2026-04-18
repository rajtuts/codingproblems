Implement Trie (Prefix Tree)
A trie (prefix tree) is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. Implement the Trie class with insert, search, and startsWith methods.

Example 1:
Input: {"operations":["Trie","insert","search","search","startsWith","insert","search"],"args":[[],["apple"],["apple"],["app"],["app"],["app"],["app"]]}
Output: [null,null,true,false,true,null,true]

**Approach: Trie Data Structure**

A Trie is a tree where each node represents a character, and paths from root represent strings.

**Structure:**

-   Each node has:
    
    -   `children`: dictionary mapping char → child node
        
    -   `is_end`: boolean marking if a word ends here
        

**Operations:**

**Insert(word):**

1.  Start at root.
    
2.  For each character, create child node if needed.
    
3.  Move to child node.
    
4.  Mark last node as end of word.
    

**Search(word):**

1.  Traverse following characters.
    
2.  If any character not found, return False.
    
3.  Return True only if last node is marked as end.
    

**StartsWith(prefix):**

1.  Same traversal as search.
    
2.  Return True if traversal succeeds (no need to check is\_end).
    

**Example Trie after inserting "apple", "app":**

`root └─ a └─ p └─ p (is_end=True for "app") └─ l └─ e (is_end=True for "apple")`

**Time Complexity:** O(m) per operation where m = word/prefix length **Space Complexity:** O(total characters across all words)

**Time: O(m) per operation**

**Space: O(n\*m) total**

```
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
}

class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) return false;
            node = node.children.get(c);
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return false;
            node = node.children.get(c);
        }
        return true;
    }
}
```
