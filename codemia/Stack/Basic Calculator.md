# Basic Calculator
Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation. The expression string may contain open ( and closing ) parentheses, the plus + or minus - sign, non-negative integers and empty spaces.

Example 1:
Input: "(1+(4+5+2)-3)+(6+8)"
Output: 23

**Approach: Stack for Nested State**

**Key Insight:** When we encounter '(', we need to save current result and sign, then reset for the inner expression. When we encounter ')', we apply the saved sign and add to the saved result.

**Algorithm:**

1.  Initialize result=0, current\_number=0, sign=1
    
2.  For each character:
    
    -   Digit: build current\_number
        
    -   '+': add current\_number × sign to result, sign=1, reset number
        
    -   '-': add current\_number × sign to result, sign=-1, reset number
        
    -   '(': push result and sign to stack, reset result and sign
        
    -   ')': add number to result, multiply by popped sign, add popped result
        
3.  Return result + current\_number × sign
    

**Example:** s = "(1+(4+5+2)-3)+(6+8)"

-   '(': push (0, 1), result=0
    
-   "1+": result=1
    
-   '(': push (1, 1), result=0
    
-   "4+5+2": result=11
    
-   ')': pop → result=1 + 1×11 = 12
    
-   "-3": result=12-3=9
    
-   ')': pop → result=0 + 1×9 = 9
    
-   "+(6+8)": result=9+14=23 ✓
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(n) - stack depth for nested parentheses

**Time: O(n)**

**Space: O(n)**


```
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int num = 0, sign = 1, result = 0;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '+') {
                result += sign * num;
                num = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * num;
                num = 0;
                sign = -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * num;
                num = 0;
                result *= stack.pop();
                result += stack.pop();
            }
        }
        return result + sign * num;
    }
}
````
