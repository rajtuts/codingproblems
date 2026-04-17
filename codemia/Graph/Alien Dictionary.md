# Alien Dictionary
There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you. You are given a list of strings words from the alien language's dictionary, where the strings in words are sorted lexicographically by the rules of this new language. Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new rules. If there is no solution, return "".

Example 1:
Input: ["wrt","wrf","er","ett","rftt"]
Output: "wertf"

**Approach: Graph + Topological Sort**

**Key Insight:** Compare adjacent words to discover letter ordering. The first differing character tells us c1 comes before c2 in the alien alphabet. Build a directed graph and topologically sort it.

**Algorithm:**

1.  Initialize all characters with in-degree 0
    
2.  Compare adjacent word pairs to build edges:
    
    -   Find first differing character position
        
    -   Add edge: word1\[j\] → word2\[j\] (word1\[j\] comes before word2\[j\])
        
    -   Check invalid case: longer word before shorter prefix (e.g., "abc" before "ab")
        
3.  Topological sort using BFS (Kahn's algorithm):
    
    -   Start with nodes having in-degree 0
        
    -   Process each node, reduce neighbors' in-degrees
        
4.  If result length ≠ total characters, cycle exists → return ""
    

**Example:** words = \["wrt","wrf","er","ett","rftt"\]

-   Compare "wrt" vs "wrf": t→f
    
-   Compare "wrf" vs "er": w→e
    
-   Compare "er" vs "ett": r→t
    
-   Compare "ett" vs "rftt": e→r
    
-   Graph: w→e→r→t→f
    
-   Topo sort: "wertf" ✓
    

**Time Complexity:** O(C) where C = total characters across all words **Space Complexity:** O(1) - at most 26 letters in alphabet

**Time: O(C) where C is total characters**

**Space: O(1) since alphabet is fixed**

```
class Solution {
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> adj = new HashMap<>();
        for (String w : words) for (char c : w.toCharArray()) adj.putIfAbsent(c, new HashSet<>());
        
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i], w2 = words[i+1];
            int minLen = Math.min(w1.length(), w2.length());
            if (w1.length() > w2.length() && w1.startsWith(w2)) return "";
            for (int j = 0; j < minLen; j++) {
                if (w1.charAt(j) != w2.charAt(j)) {
                    adj.get(w1.charAt(j)).add(w2.charAt(j));
                    break;
                }
            }
        }
        
        Map<Character, Boolean> visit = new HashMap<>();
        StringBuilder res = new StringBuilder();
        
        for (Character c : adj.keySet()) {
            if (dfs(c, adj, visit, res)) return "";
        }
        return res.reverse().toString();
    }
    
    private boolean dfs(Character c, Map<Character, Set<Character>> adj, Map<Character, Boolean> visit, StringBuilder res) {
        if (visit.containsKey(c)) return visit.get(c);
        
        visit.put(c, true);
        for (Character nei : adj.get(c)) {
            if (dfs(nei, adj, visit, res)) return true;
        }
        visit.put(c, false);
        res.append(c);
        return false;
    }
}
```
