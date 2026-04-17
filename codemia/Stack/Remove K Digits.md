# Remove K Digits
Given string num representing a non-negative integer num, and an integer k, return the smallest possible integer after removing k digits from num.

Example 1:
Input: {"num":"1432219","k":3}
Output: "1219"

**Approach: Monotonic Stack (Greedy)**

The key insight is that to form the smallest number, we want smaller digits at more significant positions (left side).

**Algorithm:**

1.  Use a stack to build the result digit by digit.
    
2.  For each digit in the input:
    
    -   While we have removals left (k > 0) AND the stack is not empty AND the top of stack is larger than current digit:
        
        -   Pop the larger digit (remove it) and decrement k
            
    -   Push the current digit onto the stack
        
3.  If we still have removals left after processing all digits, remove from the end of the stack.
    
4.  Convert stack to string, strip leading zeros, return "0" if empty.
    

**Why this works:**

-   When we see "14", and then see "3", we know "13..." is always smaller than "14...", so removing "4" is beneficial.
    
-   The greedy choice of removing larger preceding digits when we find a smaller digit always leads to the optimal result.
    

**Example: num = "1432219", k = 3**

-   Process '1': stack = \['1'\]
    
-   Process '4': stack = \['1', '4'\]
    
-   Process '3': 4 > 3, pop 4, k=2. stack = \['1', '3'\]
    
-   Process '2': 3 > 2, pop 3, k=1. stack = \['1', '2'\]
    
-   Process '2': stack = \['1', '2', '2'\]
    
-   Process '1': 2 > 1, pop 2, k=0. stack = \['1', '2', '1'\]
    
-   Process '9': stack = \['1', '2', '1', '9'\]
    
-   Result: "1219"
    

**Time Complexity:** O(n) - each digit is pushed and popped at most once **Space Complexity:** O(n) - for the stack

**Time: O(n)**

**Space: O(n)**

````
class Solution {
    public String removeKdigits(String num, int k) {
        StringBuilder stack = new StringBuilder();
        for (char digit : num.toCharArray()) {
            while (k > 0 && stack.length() > 0 && stack.charAt(stack.length() - 1) > digit) {
                stack.deleteCharAt(stack.length() - 1);
                k--;
            }
            stack.append(digit);
        }
        // Remove remaining k digits from end
        while (k > 0 && stack.length() > 0) {
            stack.deleteCharAt(stack.length() - 1);
            k--;
        }
        // Remove leading zeros
        while (stack.length() > 0 && stack.charAt(0) == '0') {
            stack.deleteCharAt(0);
        }
        return stack.length() == 0 ? "0" : stack.toString();
    }
}
```
