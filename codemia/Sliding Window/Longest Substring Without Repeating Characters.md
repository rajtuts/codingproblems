# Longest Substring Without Repeating Characters
Given a string s, find the length of the longest substring without repeating characters.

Example 1:
Input: "abcabcbb"
Output: 3

**Approach: Sliding Window with Hash Map**

**Key Insight:** Maintain a window \[start, i\] that contains no repeating characters. When we see a repeat, jump start past the previous occurrence.

**Algorithm:**

1.  Use hash map to store {character: last\_seen\_index}
    
2.  Maintain a sliding window with 'start' pointer
    
3.  For each character at index i:
    
    -   If char was seen and its index >= start: move start past it
        
    -   Update char's position in map
        
    -   Update max\_length = max(max\_length, i - start + 1)
        

**Example:** "abcabcbb"

**icharstartmapwindowmax**0a0{a:0}"a"11b0{a:0,b:1}"ab"22c0{a:0,b:1,c:2}"abc"33a1{a:3,b:1,c:2}"bca"34b2{a:3,b:4,c:2}"cab"3...continues...

**Why This Works:** We never shrink the window unnecessarily. When a duplicate is found, we jump directly to the valid position.

**Time: O(n)** - single pass, **Space: O(min(n, m))** - m is charset size

**Time: O(n)**

**Space: O(min(n, m)) where m is charset size**

```
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int l = 0;
        int res = 0;
        
        for (int r = 0; r < s.length(); r++) {
            while (set.contains(s.charAt(r))) {
                set.remove(s.charAt(l));
                l++;
            }
            set.add(s.charAt(r));
            res = Math.max(res, r - l + 1);
        }
        return res;
    }
}
```
