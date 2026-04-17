# Reorganize String
Given a string s, rearrange the characters of s so that any two adjacent characters are not the same. Return any possible rearrangement of s or return "" if not possible.

Example 1:
Input: {"s":"aab"}
Output: "aba"

**Approach: Max Heap (Greedy)**

**Key Insight:** To avoid adjacent duplicates, always place the two most frequent characters alternately. Impossible if any char frequency > (n+1)/2.

**Algorithm:**

1.  Count character frequencies
    
2.  If max frequency > (n+1)/2, return ""
    
3.  Use max heap ordered by frequency
    
4.  Pop two chars, append both, decrement counts
    
5.  Push back if count > 0
    
6.  Handle remaining single char at end
    

**Example:** s = "aab"

-   Counts: {a:2, b:1}
    
-   Heap: \[(-2,'a'), (-1,'b')\]
    
-   Pop 'a','b' → result="ab", push 'a' back
    
-   Pop 'a' → result="aba"
    

**Time Complexity:** O(n log 26) - n operations on 26-element heap **Space Complexity:** O(26) - frequency map and heap

**Time: O(n log 26)**

**Space: O(26)**

```
class Solution {
    public String reorganizeString(String s) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) counts.put(c, counts.getOrDefault(c, 0) + 1);
        
        PriorityQueue<Character> maxHeap = new PriorityQueue<>((a, b) -> counts.get(b) - counts.get(a));
        maxHeap.addAll(counts.keySet());
        
        StringBuilder res = new StringBuilder();
        while (maxHeap.size() >= 2) {
            char a = maxHeap.poll();
            char b = maxHeap.poll();
            
            res.append(a);
            res.append(b);
            
            counts.put(a, counts.get(a) - 1);
            counts.put(b, counts.get(b) - 1);
            
            if (counts.get(a) > 0) maxHeap.add(a);
            if (counts.get(b) > 0) maxHeap.add(b);
        }
        
        if (!maxHeap.isEmpty()) {
            char c = maxHeap.poll();
            if (counts.get(c) > 1) return "";
            res.append(c);
        }
        
        return res.toString();
    }
}
````
