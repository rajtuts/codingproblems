# Partition Labels
You are given a string s. We want to partition the string into as many parts as possible so that each letter appears in at most one part. Return a list of integers representing the size of these parts.

Example 1:
Input: {"s":"ababcbacadefegdehijhklij"}
Output: [9,7,8]

**Approach: Greedy with Last Occurrence**

**Key Insight:** Each partition must include all occurrences of every character it contains. Find the last occurrence of each character, then greedily extend partitions.

**Algorithm:**

1.  Build map of last occurrence for each character
    
2.  Track partition start and end
    
3.  For each character, extend end to its last occurrence
    
4.  When current index equals end, close partition
    

**Example:** s = "ababcbacadefegdehijhklij"

-   'a' last at 8, 'b' last at 5, 'c' last at 7
    
-   At i=8: all a,b,c accounted → partition \[0,8\], size=9
    
-   Continue similarly for remaining partitions
    
-   Result: \[9, 7, 8\]
    

**Time Complexity:** O(n) - two passes **Space Complexity:** O(26) - last occurrence map

**Time: O(n)**

**Space: O(26)**


```
class Solution {
    public List<Integer> partitionLabels(String s) {
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }
        
        List<Integer> res = new ArrayList<>();
        int size = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            size++;
            end = Math.max(end, lastIndex[s.charAt(i) - 'a']);
            
            if (i == end) {
                res.add(size);
                size = 0;
            }
        }
        return res;
    }
}
```
