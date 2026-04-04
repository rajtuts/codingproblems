# Valid Anagram
Given two strings s and t, return true if t is an anagram of s, and false otherwise.

## Example 1:
Input: {"s":"anagram","t":"nagaram"}
Output: true

**Approach: Character Frequency Comparison**
**Key Insight:** Anagrams contain exactly the same characters with the same frequencies, just rearranged.
**Method 1 - Sorting:** Sort both strings and compare. Anagrams will be identical when sorted.
-   Time: O(n log n), Space: O(n)
**Method 2 - Hash Map:** Count character frequencies in both strings and compare.
-   Time: O(n), Space: O(1) - since alphabet is fixed at 26 letters
**Method 3 - Single Counter:** Increment for s, decrement for t. If all counts are 0, they're anagrams.
**Edge Cases:**
-   Different lengths → not anagrams (quick early return)
-   Empty strings → both empty are anagrams
-   Case sensitivity → problem states lowercase only
**Time: O(n log n)**   **Space: O(n)**

```
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            count[c - 'a']--;
            if (count[c - 'a'] < 0) return false;
        }
        return true;
    }
}
```
