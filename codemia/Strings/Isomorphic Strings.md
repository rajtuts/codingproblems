# Isomorphic Strings  
Given two strings s and t, determine if they are isomorphic. Two strings are isomorphic if the characters in s can be replaced to get t.  

## Example 1:  
Input: {"s":"egg","t":"add"}   
Output: true  

**Approach: Bidirectional Character Mapping**  

**Key Insight:** Isomorphism requires a bijective (one-to-one and onto) mapping. We need two maps to ensure no two characters in s map to the same character in t, AND no two characters in t are mapped from the same character in s.  

**Why Two Maps?**  
-   s→t map: Ensures each char in s consistently maps to same char in t      
-   t→s map: Ensures each char in t is mapped from only one char in s      

**Example:** s = "foo", t = "bar" → FALSE  
-   f→b (ok), o→a (ok), o→r (conflict! o already maps to a)      

**Example:** s = "ab", t = "aa" → FALSE  
-   a→a (ok), b→a (conflict! a in t already mapped from a in s)      
-   Only s→t map would miss this!      

**Algorithm:**  
1.  For each character pair (c1, c2):      
    -   If c1 already mapped and maps to different char → False          
    -   If c2 already mapped from different char → False          
    -   Otherwise, record both mappings          
2.  If no conflicts, return True      

**Time Complexity:** O(n) - single pass through both strings **Space Complexity:** O(1) - at most 256 characters in maps (ASCII)  
**Time: O(n)**   
**Space: O(1)**  

```
class Solution {
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] sToT = new int[256], tToS = new int[256];
        Arrays.fill(sToT, -1); Arrays.fill(tToS, -1);

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i), c2 = t.charAt(i);
            if (sToT[c1] != -1 && sToT[c1] != c2) return false;
            if (tToS[c2] != -1 && tToS[c2] != c1) return false;
            sToT[c1] = c2;
            tToS[c2] = c1;
        }

        return true;
    }
}
```
