# Add Binary
Given two binary strings a and b, return their sum as a binary string.

## Example 1:  
Input: {"a":"11","b":"1"}  
Output: 100  

**Approach: Python Built-ins**

**Key Insight:** Python handles arbitrary-precision integers, so we can convert binary strings to integers, add them, and convert back.

**Algorithm (Pythonic):**  
1.  `int(a, 2)` - parse binary string to integer      
2.  Add the two integers      
3.  `bin(sum)` - convert to binary string (prefixed with '0b')      
4.  `[2:]` - remove the '0b' prefix      

**Example:** a = "1010", b = "1011"  
-   int("1010", 2) = 10      
-   int("1011", 2) = 11      
-   10 + 11 = 21      
-   bin(21) = "0b10101"      
-   "0b10101"\[2:\] = "10101" ✓      

**Alternative (Manual Bit-by-Bit):**  
`i, j, carry = len(a) - 1, len(b) - 1, 0     
`result = []    
`while i >= 0 or j >= 0 or carry:   
`total = carry if i >= 0:   
`total += int(a[i]); i -= 1   
`if j >= 0: total += int(b[j]); j -= 1   
`result.append(str(total % 2))   
`carry = total // 2   
`return ''.join(reversed(result))`  

**Time Complexity:** O(n + m) where n, m are string lengths **Space Complexity:** O(max(n, m)) for result string  

**Time: O(n + m)**  
**Space: O(max(n, m))**  

```
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1, j = b.length() - 1;

        while (i >= 0 || j >= 0 || carry > 0) {
            int total = carry;
            if (i >= 0) total += a.charAt(i--) - '0';
            if (j >= 0) total += b.charAt(j--) - '0';
            result.append(total % 2);
            carry = total / 2;
        }

        return result.reverse().toString();
    }
}
```
