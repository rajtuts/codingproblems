# Permutation in String
Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise. In other words, return true if one of s1's permutations is the substring of s2.

Example 1:
Input: {"s1":"ab","s2":"eidbaooo"}
Output: true

**Approach: Sliding Window with Character Count**

Since a permutation has the same characters with the same frequencies, we can use a fixed-size sliding window.

**Algorithm:**

1.  Create frequency arrays for both s1 and the first window of s2 (size = len(s1)).
    
2.  Count how many of the 26 character frequencies match between s1 and the window.
    
3.  Slide the window one character at a time:
    
    -   Add the new right character and update matches.
        
    -   Remove the left character and update matches.
        
    -   If all 26 frequencies match (matches == 26), we found a permutation.
        

**Key Insight:** Instead of comparing entire frequency arrays (O(26) per position), we track how many characters have matching frequencies and update incrementally.

**Time Complexity:** O(n) where n = len(s2) **Space Complexity:** O(1) - only using fixed-size arrays of 26

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;
        int[] s1Count = new int[26];
        int[] s2Count = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (s1Count[i] == s2Count[i]) matches++;
        }
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            if (matches == 26) return true;
            int left = s2.charAt(i) - 'a';
            int right = s2.charAt(i + s1.length()) - 'a';
            s2Count[right]++;
            if (s2Count[right] == s1Count[right]) matches++;
            else if (s2Count[right] == s1Count[right] + 1) matches--;
            s2Count[left]--;
            if (s2Count[left] == s1Count[left]) matches++;
            else if (s2Count[left] == s1Count[left] - 1) matches--;
        }
        return matches == 26;
    }
}
```
