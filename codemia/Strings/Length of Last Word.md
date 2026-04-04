# Length of Last Word
Given a string s consisting of words and spaces, return the length of the last word in the string.

## Example 1:  
Input: "Hello World"  
Output: 5  

**Approach: String Methods (Pythonic)**  
**Key Insight:** Handle trailing spaces first, then extract the last word.  
**Algorithm (Using Built-ins):**  
1.  `rstrip()` - remove trailing spaces      
2.  `split()` - split by whitespace into words      
3.  `[-1]` - get last word      
4.  `len()` - return its length      

**Example:** " fly me to the moon "  
-   rstrip() → " fly me to the moon"      
-   split() → \["fly", "me", "to", "the", "moon"\]      
-   \[-1\] → "moon"      
-   len() → 4 ✓      

**Alternative (Manual, O(1) Space):**  

`i = len(s) - 1 while i >= 0 and s[i] == ' ': # Skip trailing spaces i -= 1 length = 0 while i >= 0 and s[i] != ' ': # Count word chars length += 1 i -= 1 return length`  

**Trade-offs:**   
-   Built-in: Cleaner code, O(n) space for split    
-   Manual: O(1) space, more verbose      

**Time Complexity:** O(n) - traverse string **Space Complexity:** O(n) for split approach, O(1) for manual  
**Time: O(n)**  
**Space: O(n)**  

```
class Solution {
    public int lengthOfLastWord(String s) {
        s = s.trim();
        return s.length() - s.lastIndexOf(' ') - 1;
    }
}
```
