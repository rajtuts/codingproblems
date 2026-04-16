# Longest Valid Parentheses
Given a string containing just the characters '(' and ')', return the length of the longest valid (well-formed) parentheses substring.

Example 1:
Input: "(()"
Output: 2

**Approach: Stack with Index Tracking**

**Key Insight:** The stack stores indices (not characters) to calculate substring lengths. Initialize with -1 as a base for calculating length when the stack becomes empty.

**Algorithm:**

1.  Initialize stack with -1 (boundary marker)
    
2.  For each character at index i:
    
    -   If '(': push index i onto stack
        
    -   If ')': pop from stack
        
        -   If stack empty: push i as new boundary
            
        -   If stack not empty: current length = i - stack\[-1\]
            
3.  Track maximum length seen
    

**Example:** s = ")()())"

-   i=0 ')': pop -1, stack empty, push 0 → stack=\[0\]
    
-   i=1 '(': push 1 → stack=\[0,1\]
    
-   i=2 ')': pop 1, len = 2-0 = 2, max=2 → stack=\[0\]
    
-   i=3 '(': push 3 → stack=\[0,3\]
    
-   i=4 ')': pop 3, len = 4-0 = 4, max=4 → stack=\[0\]
    
-   i=5 ')': pop 0, stack empty, push 5 → stack=\[5\]
    
-   Result: 4 ✓
    

**Why This Works:**

-   Stack top always represents the index just before the start of current valid substring
    
-   When we match '(' with ')', the length is current index minus the new stack top
    

**Time Complexity:** O(n) - single pass through string **Space Complexity:** O(n) - stack can hold all indices in worst case

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }

        return maxLen;
    }
}
```
