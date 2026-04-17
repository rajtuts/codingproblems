# Min Stack
Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Example 1:
Input: {"commands":["MinStack","push","push","push","getMin","pop","top","getMin"],"values":[[],[-2],[0],[-3],[],[],[],[]]}
Output: [null,null,null,null,-3,null,0,-2]

**Approach: Two Stacks (or Single Stack with Pairs)**

**Key Insight:** Track the minimum at each state of the stack. When we pop, we need to know what the minimum was before that element was pushed.

**Method 1: Two Stacks**

-   Main stack: stores all elements
    
-   Min stack: stores minimum at each level
    
-   On push: push to main; push to min if <= current min
    
-   On pop: pop from main; pop from min if values match
    

**Method 2: Single Stack with Pairs** Store (value, current\_min) for each element:

`class MinStack: def __init__(self): self.stack = [] # [(val, min_so_far), ...] def push(self, val): curr_min = min(val, self.stack[-1][1] if self.stack else val) self.stack.append((val, curr_min)) def pop(self): self.stack.pop() def top(self): return self.stack[-1][0] def getMin(self): return self.stack[-1][1]`

**Method 3: Store Difference** Store difference from min to save space (advanced).

**All operations O(1) time and O(n) space overall.**

**Why We Can't Just Track Min?** When we pop the minimum element, we need to know the PREVIOUS minimum. That's why we need to store history.

**Time: O(1) for all operations**

**Space: O(n)**


```
class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int val) {
        stack.push(val);
        val = Math.min(val, minStack.isEmpty() ? val : minStack.peek());
        minStack.push(val);
    }
    
    public void pop() {
        stack.pop();
        minStack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}
```
