# Word Search II
Given an m x n board of characters and a list of strings words, return all words on the board.  

## Example 1:  
Input: {"board":[["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]],"words":["oath","pea","eat","rain"]}  
Output: ["eat","oath"]  

**Approach: Trie + DFS with Pruning**

**Key Insight:** Instead of searching for each word separately, build a Trie from all words. DFS on the board following Trie paths—this prunes invalid prefixes early.

**Algorithm:**
1.  Build Trie from words (mark word ends with '$')      
2.  For each cell, DFS following Trie:      
    -   If char not in current Trie node: return (prune)          
    -   If word end found: add to result, delete marker (avoid duplicates)         
    -   Mark cell visited, recurse 4 directions, restore cell          
3.  Return collected words      

**Example:** words = \["oath","pea","eat","rain"\]  
-   Build Trie with o→a→t→h,p→e→a,_p_→_e_→_a_, e→a→t,r→a→i→n,_r_→_a_→_i_→_n_      
-   DFS from 'o' at (0,0): follow o→a→t→h → found "oath" ✓      
-   DFS from 'e' at (1,0): follow e→a→t → found "eat" ✓      

**Optimizations:**  
-   Delete word markers after finding (no duplicates)      
-   Prune empty Trie branches      
-   Early termination when all words found      

**Why Trie is Better:**  
-   Single traversal finds all words      
-   Pruning avoids exploring dead-end prefixes       

**Time Complexity:** O(m×n×4^L) - L is max word length **Space Complexity:** O(sum of word lengths) - Trie storage    
**Time: O(m\*n\*4^L)**   
**Space: O(sum of word lengths)**    

```
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word = null;
}

public class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        TrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, res);
            }
        }
        return res;
    }

    public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
        char c = board[i][j];
        if (c == '#' || p.children[c - 'a'] == null) return;
        p = p.children[c - 'a'];
        if (p.word != null) {   // found one
            res.add(p.word);
            p.word = null;     // de-duplicate
        }

        board[i][j] = '#';
        if (i > 0) dfs(board, i - 1, j, p, res);
        if (j > 0) dfs(board, i, j - 1, p, res);
        if (i < board.length - 1) dfs(board, i + 1, j, p, res);
        if (j < board[0].length - 1) dfs(board, i, j + 1, p, res);
        board[i][j] = c;
    }

    public TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode p = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (p.children[i] == null) p.children[i] = new TrieNode();
                p = p.children[i];
            }
            p.word = w;
        }
        return root;
    }
}
```
