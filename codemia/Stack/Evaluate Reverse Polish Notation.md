# Evaluate Reverse Polish Notation
Evaluate the value of an arithmetic expression in Reverse Polish Notation. Valid operators are +, -, *, and /. Each operand may be an integer or another expression. Note that division between two integers should truncate toward zero.

Example 1:
Input: {"tokens":["2","1","+","3","*"]}
Output: 9

**Approach: Stack-Based Evaluation**

**Key Insight:** In Reverse Polish Notation (postfix), operands come before operators. Use a stack: push numbers, when operator appears, pop two operands, compute, and push result.

**Algorithm:**

1.  Initialize empty stack
    
2.  For each token:
    
    -   If operator (+, -, \*, /):
        
        -   Pop b, then pop a (order matters for - and /)
            
        -   Compute result based on operator
            
        -   Push result
            
    -   Else (number):
        
        -   Push integer value
            
3.  Return stack\[0\] (final result)
    

**Example:** tokens = \["2","1","+","3","\*"\]

-   "2": push → \[2\]
    
-   "1": push → \[2, 1\]
    
-   "+": pop 1, 2 → push 3 → \[3\]
    
-   "3": push → \[3, 3\]
    
-   "\*": pop 3, 3 → push 9 → \[9\]
    
-   Return 9 ✓
    

**Edge Cases:**

-   Division: use int(a/b) for truncation toward zero
    
-   Negative numbers: handled by int() conversion
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(n) - stack size

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String t : tokens) {
            if (t.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (t.equals("-")) {
                int a = stack.pop(), b = stack.pop();
                stack.push(b - a);
            } else if (t.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (t.equals("/")) {
                int a = stack.pop(), b = stack.pop();
                stack.push(b / a);
            } else {
                stack.push(Integer.parseInt(t));
            }
        }
        return stack.pop();
    }
}
```
