# Generate Parentheses
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.  

## Example 1:  
Input: 3  
Output: ["((()))","(()())","(())()","()(())","()()()"]  

**Backtracking: add '(' if remaining, add ')' only if it won't exceed open parens.**

**Time: O(4^n / sqrt(n))
Space: O(n)**

```
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(n, 0, 0, new StringBuilder(), result);
        return result;
    }

    private void backtrack(int n, int open, int close, StringBuilder path, List<String> result) {
        if (path.length() == 2 * n) {
            result.add(path.toString());
            return;
        }
        if (open < n) {
            path.append('(');
            backtrack(n, open + 1, close, path, result);
            path.deleteCharAt(path.length() - 1);
        }
        if (close < open) {
            path.append(')');
            backtrack(n, open, close + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
```
