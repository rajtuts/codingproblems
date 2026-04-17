# Find All Anagrams
Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer in any order.

Example 1:
Input: {"s":"cbaebabacd","p":"abc"}
Output: [0,6]

**Approach: Sliding Window with Character Frequency Matching**

This problem is an extension of "Permutation in String" where we need to find ALL anagram occurrences.

**Algorithm:**

1.  Create frequency arrays for p and the initial window of s (size = len(p)).
    
2.  Count how many character frequencies match (0-26).
    
3.  Slide the window:
    
    -   If all 26 characters match (matches == 26), add current index to result.
        
    -   Add the new right character and update matches.
        
    -   Remove the old left character and update matches.
        
4.  Don't forget to check the last window position.
    

**Key Optimization:** Instead of comparing the entire frequency arrays (O(26)) at each step, we only update the match count incrementally based on how the add/remove affects each character's count.

**Example with s="cbaebabacd", p="abc":**

-   Initial window "cba": matches all → result=\[0\]
    
-   Slide to "bae": 'e' doesn't match → continue
    
-   Slide to "aeb": no match
    
-   ... continue sliding ...
    
-   Window "bac" at index 6: matches all → result=\[0,6\]
    

**Time Complexity:** O(n) where n = len(s) **Space Complexity:** O(1) - fixed size arrays of 26

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) return result;
        int[] pCount = new int[26];
        int[] sCount = new int[26];
        for (int i = 0; i < p.length(); i++) {
            pCount[p.charAt(i) - 'a']++;
            sCount[s.charAt(i) - 'a']++;
        }
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (pCount[i] == sCount[i]) matches++;
        }
        for (int i = 0; i < s.length() - p.length(); i++) {
            if (matches == 26) result.add(i);
            int left = s.charAt(i) - 'a';
            int right = s.charAt(i + p.length()) - 'a';
            sCount[right]++;
            if (sCount[right] == pCount[right]) matches++;
            else if (sCount[right] == pCount[right] + 1) matches--;
            sCount[left]--;
            if (sCount[left] == pCount[left]) matches++;
            else if (sCount[left] == pCount[left] - 1) matches--;
        }
        if (matches == 26) result.add(s.length() - p.length());
        return result;
    }
}
```
