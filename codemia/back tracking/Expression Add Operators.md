# Expression Add Operators
Given a string num that contains only digits and an integer target, return all possible expressions by adding the binary operators +, -, or * between the digits so that the expression evaluates to the target value.  

## Example 1:  
Input: {"num":"123","target":6}  
Output: ["1*2*3","1+2+3"]  

**Backtracking with tracking of current value and previous operand. For multiplication, undo previous operation and apply new one.**

**Time: O(4^n * n)  
Space: O(n)**  

```
class Solution {
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        backtrack(num, target, 0, "", 0, 0, result);
        return result;
    }

    private void backtrack(String num, int target, int index, String expr, long value, long prev, List<String> result) {
        if (index == num.length()) {
            if (value == target) result.add(expr);
            return;
        }

        for (int i = index; i < num.length(); i++) {
            if (i > index && num.charAt(index) == '0') break;

            String currStr = num.substring(index, i + 1);
            long curr = Long.parseLong(currStr);

            if (index == 0) {
                backtrack(num, target, i + 1, currStr, curr, curr, result);
            } else {
                backtrack(num, target, i + 1, expr + "+" + currStr, value + curr, curr, result);
                backtrack(num, target, i + 1, expr + "-" + currStr, value - curr, -curr, result);
                backtrack(num, target, i + 1, expr + "*" + currStr, value - prev + prev * curr, prev * curr, result);
            }
        }
    }
}

```
