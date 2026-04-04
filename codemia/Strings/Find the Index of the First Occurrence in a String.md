# Find the Index of the First Occurrence in a String
Given two strings needle and haystack, return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

## Example 1:  
Input: {"haystack":"sadbutsad","needle":"sad"}  
Output: 0  

**Approach: Built-in String Search**  

**Key Insight:** Python's `find()` method is optimized and handles edge cases.  

**Algorithm (Built-in):**  
-   `haystack.find(needle)` returns first index or -1 if not found      

**Manual Sliding Window Approach:**  
`for i in range(len(haystack) - len(needle) + 1): if haystack[i:i + len(needle)] == needle: return i return -1`  
**Example:** haystack = "sadbutsad", needle = "sad"   
-   i=0: "sad" == "sad" ✓ → return 0      

**Advanced Algorithms (for reference):**  
-   **KMP (Knuth-Morris-Pratt):** O(n+m) using failure function      
-   **Rabin-Karp:** O(n+m) average using rolling hash      
-   **Boyer-Moore:** O(n/m) best case using bad character rule      

**When to Use Each:**  
-   Built-in: Simple, reliable, usually sufficient      
-   KMP/Boyer-Moore: Very long texts with repeated patterns      
-   Rabin-Karp: Multiple pattern matching
    
**Time Complexity:** O(n × m) worst case for naive, O(n + m) for KMP **Space Complexity:** O(1) for naive, O(m) for KMP  

**Time: O(n \* m)**  
**Space: O(1)**  

```
class Solution {
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}
```
