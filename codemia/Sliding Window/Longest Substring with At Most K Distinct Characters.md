# Longest Substring with At Most K Distinct Characters
Given a string s and an integer k, return the length of the longest substring of s that contains at most k distinct characters.

Example 1:
Input: {"s":"eceba","k":2}
Output: 3

**Approach: Sliding Window with Hash Map**

**Key Insight:** Maintain a window \[left, right\] with at most k distinct characters. Expand right, shrink left when constraint violated.

**Algorithm:**

1.  Use hash map to track character counts in window
    
2.  Expand window by moving right pointer
    
3.  If distinct chars > k: shrink from left until ≤ k
    
4.  Update max\_len after each valid window
    

**Example:** s = "eceba", k = 2

`right=0: window="e", counts={e:1}, max_len=1 right=1: window="ec", counts={e:1,c:1}, max_len=2 right=2: window="ece", counts={e:2,c:1}, max_len=3 right=3: window="eceb", counts={e:2,c:1,b:1} → 3 distinct! Shrink: remove 'e' → counts={e:1,c:1,b:1} Shrink: remove 'c' → counts={e:1,b:1} window="eb", max_len=3 right=4: window="eba", counts={e:1,b:1,a:1} → 3 distinct! Shrink: remove 'e' → counts={b:1,a:1} window="ba", max_len=3 Result: 3 (substring "ece")`

**Window Shrinking:**

`while len(char_count) > k: char_count[s[left]] -= 1 if char_count[s[left]] == 0: del char_count[s[left]] # Remove from distinct count left += 1`

**Time Complexity:** O(n) - each character added/removed at most once **Space Complexity:** O(k) - hash map stores at most k+1 characters

**Time: O(n)**

**Space: O(k)**

```
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;

        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            charCount.put(s.charAt(right), charCount.getOrDefault(s.charAt(right), 0) + 1);

            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```
