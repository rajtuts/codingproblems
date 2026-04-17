# Excel Sheet Column Number
Given a string columnTitle that represents the column title as appears in an Excel sheet, return its corresponding column number.

Example 1:
Input: "A"
Output: 1

**Approach: Base-26 Conversion**

**Key Insight:** Excel columns are essentially base-26 numbers where A=1, B=2, ..., Z=26 (not 0-25 like typical base systems).

**Algorithm:**

1.  Process characters left to right
    
2.  For each character:
    
    -   Multiply current result by 26 (shift left in base 26)
        
    -   Add character's value (A=1, B=2, ..., Z=26)
        

**Character Value:**

-   `ord(char) - ord('A') + 1` gives A=1, B=2, etc.
    

**Example:** "AB" = 28

-   'A': result = 0 × 26 + 1 = 1
    
-   'B': result = 1 × 26 + 2 = 28 ✓
    

**Example:** "ZY" = 701

-   'Z': result = 0 × 26 + 26 = 26
    
-   'Y': result = 26 × 26 + 25 = 676 + 25 = 701 ✓
    

**Why +1 Instead of +0?** Unlike normal base systems (0-9 for decimal), Excel uses 1-26:

-   "A" = 1 (not 0)
    
-   "Z" = 26 (not 25)
    
-   "AA" = 27 (not 26×26)
    

**Time Complexity:** O(n) - single pass through string **Space Complexity:** O(1) - just one accumulator variable

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int titleToNumber(String columnTitle) {
        int result = 0;
        for (char c : columnTitle.toCharArray()) {
            result = result * 26 + (c - 'A' + 1);
        }
        return result;
    }
}
```
