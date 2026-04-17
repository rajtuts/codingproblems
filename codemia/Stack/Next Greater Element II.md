# Next Greater Element II
Given a circular integer array nums, return the next greater number for every element. Search circularly if needed. Return -1 if no greater element exists.

Example 1:
Input: [1,2,1]
Output: [2,-1,2]

**Approach: Monotonic Stack with Circular Traversal**

The key insight is to use a monotonic decreasing stack and iterate through the array twice to handle the circular nature.

**Why Monotonic Decreasing Stack?**

-   We're looking for the NEXT GREATER element
    
-   Stack stores indices of elements waiting to find their next greater
    
-   When we find a larger element, all smaller elements in the stack have found their answer
    

**Handling Circular Array:**

-   Iterate 2n times using `i % n` to simulate circular access
    
-   Only push indices during first pass (i < n) to avoid duplicates
    
-   Second pass allows elements to find their next greater from the "wrapped around" portion
    

**Algorithm:**

1.  Initialize result array with -1 (default: no greater element)
    
2.  Use stack to store indices (not values)
    
3.  For i from 0 to 2n-1:
    
    -   Get actual index: idx = i % n
        
    -   While stack not empty AND current element > element at stack top:
        
        -   Pop index from stack, set its result to current element
            
    -   If i < n: push current index to stack
        
4.  Return result
    

**Example: nums = \[1, 2, 1\]**

-   i=0: stack=\[0\], result=\[-1,-1,-1\]
    
-   i=1: nums\[1\]=2 > nums\[0\]=1, pop 0, result=\[2,-1,-1\]. Push 1, stack=\[1\]
    
-   i=2: Push 2, stack=\[1,2\]
    
-   i=3 (idx=0): nums\[0\]=1, no pops
    
-   i=4 (idx=1): nums\[1\]=2 > nums\[2\]=1, pop 2, result=\[2,-1,2\]
    
-   Result: \[2, -1, 2\]
    

**Why -1 remains for index 1?**

-   2 is the maximum element, so it never finds a greater element even after wrapping around
    

**Time Complexity:** O(n) - each index pushed/popped at most once **Space Complexity:** O(n) - for stack and result array

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < 2 * n; i++) {
            int idx = i % n;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[idx]) {
                result[stack.pop()] = nums[idx];
            }
            if (i < n) {
                stack.push(i);
            }
        }
        return result;
    }
}
````
