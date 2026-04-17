# Multiply Strings
Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string. You must not use any built-in BigInteger library or convert the inputs to integer directly.

Example 1:
Input: {"num1":"123","num2":"456"}
Output: 56088

**Approach: Grade-school Multiplication**

**Key Insight:** Digit at position i in num1 times digit at position j in num2 contributes to positions i+j and i+j+1 in the result.

**Algorithm:**

1.  Create result array of size m+n (max possible length)
    
2.  For each pair of digits (from right to left):
    
    -   Multiply: mul = digit1 × digit2
        
    -   Add to result\[i+j+1\], handle carry to result\[i+j\]
        
3.  Convert to string, strip leading zeros
    

**Example:** "123" × "456"

-   3×6=18 at positions \[4,5\]: result = \[0,0,0,1,8\]
    
-   3×5=15 at positions \[3,4\]: result = \[0,0,0,16,8\] → \[0,0,1,6,8\]
    
-   Continue for all digit pairs...
    
-   Result: "56088"
    

**Position formula:**

-   num1\[i\] × num2\[j\] affects result\[i+j\] (carry) and result\[i+j+1\] (ones)
    

**Time Complexity:** O(m×n) - multiply each pair **Space Complexity:** O(m+n) - result array

**Time: O(m \* n)**

**Space: O(m + n)**

```
class Solution {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";

        int m = num1.length(), n = num2.length();
        int[] result = new int[m + n];

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int total = mul + result[p2];

                result[p2] = total % 10;
                result[p1] += total / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int digit : result) {
            if (!(sb.length() == 0 && digit == 0)) {
                sb.append(digit);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
```
