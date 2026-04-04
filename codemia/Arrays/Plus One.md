# Plus One  
Given a large integer represented as an integer array digits, increment the large integer by one and return the resulting array of digits.  

## Example 1:  
Input: [1,2,3]  
Output: [1,2,4]  

**Approach: Right-to-Left Traversal with Carry**
**Key Insight:** The only tricky case is handling carry (when a digit is 9). We can simplify by recognizing that if we can increment without carry, we're done immediately.

**Algorithm:**  
1.  Traverse from rightmost digit to left    
2.  If digit < 9: increment it and return (no carry propagation needed)    
3.  If digit = 9: set it to 0 and continue (carry propagates left)    
4.  If we exit the loop, all digits were 9s → prepend 1     

**Why This Works:** 
-   Case 1: \[1,2,3\] → \[1,2,4\] (increment last digit, done)    
-   Case 2: \[1,2,9\] → \[1,3,0\] (9 becomes 0, increment 2)    
-   Case 3: \[9,9,9\] → \[0,0,0\] → \[1,0,0,0\] (all 9s become 0s, prepend 1)     
**Example:** \[1,9,9\]
-   i=2: digit=9, set to 0 → \[1,9,0\]    
-   i=1: digit=9, set to 0 → \[1,0,0\]    
-   i=0: digit=1 < 9, increment → \[2,0,0\], return ✓    
**Time Complexity:** O(n) - worst case traverses all digits **Space Complexity:** O(1) - in-place modification (O(n) if all 9s need new array)
**Time: O(n)**   **Space: O(1)**

```
class Solution {
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
}
```
