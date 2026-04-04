# Longest Common Prefix  
Write a function to find the longest common prefix string amongst an array of strings.  

Example 1:  
Input: ["flower","flow","flight"]  
Output: fl  

**Approach: Horizontal Scanning**  

**Key Insight:** Start with the first string as the candidate prefix. Compare with each subsequent string and shrink the prefix until it matches (or becomes empty).  

**Algorithm:**  
1.  Initialize prefix = strs\[0\]      
2.  For each string s in strs\[1:\]:      
    -   While s doesn't start with prefix:          
        -   Shrink prefix by removing last character              
        -   If prefix is empty, return ""              
3.  Return prefix      

**Example:** strs = \["flower", "flow", "flight"\]  
-   prefix = "flower"      
-   Compare with "flow": "flow" doesn't start with "flower"      
    -   Shrink to "flowe", "flow" → match!          
-   prefix = "flow"      
-   Compare with "flight": "flight" doesn't start with "flow"      
    -   Shrink to "flo", "fl" → match!          
-   Result: "fl" ✓      

**Alternative: Vertical Scanning**  
-   Compare characters column by column      
-   Stop when mismatch found or string ends      

**Time Complexity:** O(S) - S = sum of all character counts **Space Complexity:** O(1) - modifying prefix in-place  
**Time: O(S)**  
**Space: O(1)**  

```
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }
}
```
