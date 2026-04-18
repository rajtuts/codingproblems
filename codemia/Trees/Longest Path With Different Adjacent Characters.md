# Longest Path With Different Adjacent Characters
Given a tree represented by parent array and a string s where s[i] is the character assigned to node i, return the length of the longest path where adjacent nodes have different characters.

Example 1:
Input: {"parent":[-1,0,0,1,1,2],"s":"abacbe"}
Output: 3

DFS from root. For each node, find two longest paths from children that have different characters. The path through this node is max1 + max2 + 1. Return max1 + 1 to parent.

Time: O(n)
Space: O(n)

```
class Solution {
    int result = 1;
    List<List<Integer>> children;
    String s;

    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        this.s = s;
        children = new ArrayList<>();
        for (int i = 0; i < n; i++) children.add(new ArrayList<>());
        for (int i = 1; i < n; i++) children.get(parent[i]).add(i);

        dfs(0);
        return result;
    }

    private int dfs(int node) {
        int max1 = 0, max2 = 0;

        for (int child : children.get(node)) {
            int childLen = dfs(child);
            if (s.charAt(child) != s.charAt(node)) {
                if (childLen > max1) {
                    max2 = max1;
                    max1 = childLen;
                } else if (childLen > max2) {
                    max2 = childLen;
                }
            }
        }

        result = Math.max(result, max1 + max2 + 1);
        return max1 + 1;
    }
}
```
