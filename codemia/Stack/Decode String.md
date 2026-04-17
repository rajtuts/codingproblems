# Decode String
Given an encoded string, return its decoded string. The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times.

Example 1:
Input: "3[a]2[bc]"
Output: aaabcbc


**Approach: Stack for Nested Decoding**

**Key Insight:** Nested brackets require saving "outer" context before processing "inner" content. A stack perfectly handles this LIFO pattern.

**Algorithm:**

1.  Maintain current\_str (string being built) and current\_num (repeat count)
    
2.  On digit: build multi-digit number
    
3.  On '\[': push (current\_str, current\_num) to stack, reset both
    
4.  On '\]': pop from stack, append repeated current\_str to previous string
    
5.  On letter: append to current\_str
    

**Example:** "3\[a2\[c\]\]"

`'3': current_num = 3 '[': push("", 3), reset → current_str="", num=0 'a': current_str = "a" '2': current_num = 2 '[': push("a", 2), reset → current_str="", num=0 'c': current_str = "c" ']': pop("a", 2), current_str = "a" + "c"*2 = "acc" ']': pop("", 3), current_str = "" + "acc"*3 = "accaccacc"`

Result: "accaccacc" ✓

**Stack State Tracking:**

`"[": Save context before entering nested scope "]": Restore context and combine with nested result`

**Time Complexity:** O(n × max\_k) where n is output length **Space Complexity:** O(n) for stack in deeply nested cases

**Time: O(n \* k)**

**Space: O(n)**

```
class Solution {
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<StringBuilder> strStack = new Stack<>();
        StringBuilder currentStr = new StringBuilder();
        int currentNum = 0;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                currentNum = currentNum * 10 + (c - '0');
            } else if (c == '[') {
                numStack.push(currentNum);
                strStack.push(currentStr);
                currentNum = 0;
                currentStr = new StringBuilder();
            } else if (c == ']') {
                int repeatTimes = numStack.pop();
                StringBuilder temp = strStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(currentStr);
                }
                currentStr = temp;
            } else {
                currentStr.append(c);
            }
        }
        return currentStr.toString();
    }
}
```
