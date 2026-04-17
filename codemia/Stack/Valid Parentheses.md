# Valid Parentheses
Given a string s containing just the characters "(", ")", "{", "}", "[" and "]", determine if the input string is valid. An input string is valid if: Open brackets must be closed by the same type of brackets. Open brackets must be closed in the correct order.

Example 1:
Input: "()[]{}"
Output: true

**Approach: Stack-Based Matching**

**Key Insight:** Opening brackets must be closed in reverse order (LIFO), which is exactly what a stack provides.

**Algorithm:**

1.  Create an empty stack and a mapping of closing → opening brackets
    
2.  For each character:
    
    -   If it's an opening bracket: push onto stack
        
    -   If it's a closing bracket: pop from stack and verify it matches
        
3.  Valid if stack is empty at the end (all brackets matched)
    

**Why Stack Works:** The most recent opening bracket must be closed first. Stack's LIFO property ensures correct nesting order.

**Edge Cases:**

-   Empty string → valid
    
-   Closing bracket with empty stack → invalid
    
-   Leftover opening brackets → invalid
    

**Example:** "(\[{}\])"

-   Push '(' → stack: \['('\]
    
-   Push '\[' → stack: \['(', '\['\]
    
-   Push '{' → stack: \['(', '\[', '{'\]
    
-   Pop for '}' → matches '{', stack: \['(', '\['\]
    
-   Pop for '\]' → matches '\[', stack: \['('\]
    
-   Pop for ')' → matches '(', stack: \[\]
    
-   Stack empty → valid!
    

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(') stack.push(')');
            else if (c == '{') stack.push('}');
            else if (c == '[') stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }
}
```
