# Roman to Integer
Given a roman numeral, convert it to an integer.  

## Example 1:  
Input: "III"  
Output: 3  

**Approach: Left-to-Right with Subtraction Rule**  

**Key Insight:** Roman numerals are normally additive (left to right), but when a smaller value appears before a larger one, it's subtracted instead of added.  

**Roman Values:**  
-   I=1, V=5, X=10, L=50, C=100, D=500, M=1000      

**Subtraction Cases:**  
-   IV=4 (5-1), IX=9 (10-1)      
-   XL=40 (50-10), XC=90 (100-10)      
-   CD=400 (500-100), CM=900 (1000-100)      

**Algorithm:**  
1.  For each character, compare with the next character      
2.  If current < next: subtract current value      
3.  Otherwise: add current value      

**Example:** MCMXCIV = 1994  
-   M: 1000 > C → add 1000, total=1000      
-   C: 100 < M → subtract 100, total=900      
-   M: 1000 > X → add 1000, total=1900      
-   X: 10 < C → subtract 10, total=1890      
-   C: 100 > I → add 100, total=1990      
-   I: 1 < V → subtract 1, total=1989      
-   V: last → add 5, total=1994 ✓      

**Time Complexity:** O(n) - single pass through string **Space Complexity:** O(1) - fixed-size value map  

**Time: O(n)**  
**Space: O(1)**  

```
class Solution {
    public int romanToInt(String s) {
        int[] values = new int[128];
        values['I'] = 1; values['V'] = 5; values['X'] = 10;
        values['L'] = 50; values['C'] = 100; values['D'] = 500; values['M'] = 1000;

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i + 1 < s.length() && values[s.charAt(i)] < values[s.charAt(i + 1)]) {
                result -= values[s.charAt(i)];
            } else {
                result += values[s.charAt(i)];
            }
        }
        return result;
    }
}
```
