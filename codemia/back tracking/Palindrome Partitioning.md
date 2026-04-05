# Palindrome Partitioning
Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.  

## Example 1:  
Input: {"s":"aab"}  
Output: [["a","a","b"],["aa","b"]]  

**Backtracking: try all palindrome prefixes, recursively partition the rest.**  

**Time: O(n * 2^n)
Space: O(n)**  

```
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> part = new ArrayList<>();
        dfs(0, s, part, res);
        return res;
    }
    
    private void dfs(int i, String s, List<String> part, List<List<String>> res) {
        if (i >= s.length()) {
            res.add(new ArrayList<>(part));
            return;
        }
        
        for (int j = i; j < s.length(); j++) {
            if (isPali(s, i, j)) {
                part.add(s.substring(i, j + 1));
                dfs(j + 1, s, part, res);
                part.remove(part.size() - 1);
            }
        }
    }
    
    private boolean isPali(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}
```
