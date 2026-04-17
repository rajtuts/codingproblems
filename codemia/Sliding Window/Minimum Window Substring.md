# Minimum Window Substring
Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".

Example 1:
Input: {"s":"ADOBECODEBANC","t":"ABC"}
Output: "BANC"

  **Approach: Sliding Window with Character Counts**

**Key Insight:** Expand the window to include all required characters, then shrink from the left to find the minimum valid window.

**Algorithm:**

1.  Create frequency map `need` from string t
    
2.  Use `window` map to track current window's character counts
    
3.  Expand right pointer, updating window counts
    
4.  When window is valid (contains all chars from t with correct frequencies):
    
    -   Update minimum if current window is smaller
        
    -   Shrink from left, updating counts
        
5.  Continue until right reaches end
    

**Example:** s = "ADOBECODEBANC", t = "ABC"

-   Expand until valid: "ADOBEC" (has A, B, C)
    
-   Shrink: "DOBEC" missing A, expand again
    
-   "DOBECODEBA" → shrink → "CODEBA" → "ODEBA" → "DEBA" → "EBA" missing C
    
-   Expand: "EBANC" → shrink → "BANC" (length 4, minimum found)
    

**Key Variables:**

-   need: Required character frequencies from t
    
-   window: Current window character frequencies
    
-   valid: Count of characters that have met their required frequency
    

**Time Complexity:** O(m + n) - each character visited at most twice **Space Complexity:** O(m + n) - hash maps for character counts

**Time: O(m + n)**

**Space: O(m + n)**

```
class Solution {
    public String minWindow(String s, String t) {
        if (t.length() == 0) return "";
        Map<Character, Integer> countT = new HashMap<>();
        for (char c : t.toCharArray()) countT.put(c, countT.getOrDefault(c, 0) + 1);
        
        int have = 0, need = countT.size();
        int l = 0;
        int[] res = {-1, -1};
        int resLen = Integer.MAX_VALUE;
        Map<Character, Integer> window = new HashMap<>();
        
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            window.put(c, window.getOrDefault(c, 0) + 1);
            
            if (countT.containsKey(c) && window.get(c).intValue() == countT.get(c).intValue()) {
                have++;
            }
            
            while (have == need) {
                if ((r - l + 1) < resLen) {
                    res[0] = l; res[1] = r;
                    resLen = r - l + 1;
                }
                
                char leftChar = s.charAt(l);
                window.put(leftChar, window.get(leftChar) - 1);
                if (countT.containsKey(leftChar) && window.get(leftChar) < countT.get(leftChar)) {
                    have--;
                }
                l++;
            }
        }
        return resLen != Integer.MAX_VALUE ? s.substring(res[0], res[1] + 1) : "";
    }
}
```
