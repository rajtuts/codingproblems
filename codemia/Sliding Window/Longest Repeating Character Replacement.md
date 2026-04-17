# Longest Repeating Character Replacement
You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most k times. Return the length of the longest substring containing the same letter you can get after performing the above operations.

Example 1:
Input: {"s":"ABAB","k":2}
Output: 4

**Approach: Sliding Window**

**Key Insight:** A window is valid if we can make all characters the same by replacing at most k characters. This means: window\_length - max\_frequency <= k.

**Algorithm:**

1.  Expand window by moving right pointer
    
2.  Track frequency of each character in window
    
3.  Track maximum frequency of any single character
    
4.  While window invalid (length - maxFreq > k): shrink from left
    
5.  Update result with window size
    

**Example:** s = "ABAB", k = 2

-   Window "AB": len=2, maxFreq=1, 2-1=1 ≤ 2 ✓
    
-   Window "ABA": len=3, maxFreq=2, 3-2=1 ≤ 2 ✓
    
-   Window "ABAB": len=4, maxFreq=2, 4-2=2 ≤ 2 ✓
    
-   Result: 4
    

**Time Complexity:** O(n) - each char visited at most twice **Space Complexity:** O(26) - fixed-size frequency map

**Time: O(n)**

**Space: O(26)**

```
class Solution {
    public int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int l = 0, maxf = 0, res = 0;
        
        for (int r = 0; r < s.length(); r++) {
            count[s.charAt(r) - 'A']++;
            maxf = Math.max(maxf, count[s.charAt(r) - 'A']);
            
            while ((r - l + 1) - maxf > k) {
                count[s.charAt(l) - 'A']--;
                l++;
            }
            res = Math.max(res, r - l + 1);
        }
        return res;
    }
}
```
