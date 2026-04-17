# Valid Parenthesis String
Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.

Example 1:
Input: {"s":"(*))"}
Output: true

**Approach: Greedy with Range Tracking**

**Key Insight:** Track the possible range of open parentheses count. '\*' can be '(', ')' or empty, so it expands the range.

**Algorithm:**

1.  Track low (min possible open) and high (max possible open)
    
2.  For '(': both low and high increase
    
3.  For ')': both decrease (low can't go below 0)
    
4.  For '\*': low decreases (treat as ')'), high increases (treat as '(')
    
5.  If high < 0: impossible. Valid if low == 0 at end.
    

**Example:** s = "(\*)"

-   '(': low=1, high=1
    
-   '\*': low=0, high=2
    
-   ')': low=0, high=1
    
-   End: low=0 ✓
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - two variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public boolean checkValidString(String s) {
        int leftMin = 0, leftMax = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftMin++;
                leftMax++;
            } else if (c == ')') {
                leftMin--;
                leftMax--;
            } else {
                leftMin--;
                leftMax++;
            }
            
            if (leftMax < 0) return false;
            if (leftMin < 0) leftMin = 0;
        }
        return leftMin == 0;
    }
}
