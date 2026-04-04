# Group Anagrams
Given an array of strings strs, group the anagrams together. You can return the answer in any order.

## Example 1:
Input: ["eat","tea","tan","ate","nat","bat"]
Output: [["eat","tea","ate"],["tan","nat"],["bat"]]

**Approach: Hash Map with Canonical Key**

**Key Insight:** All anagrams share the same sorted form. Use this as a hash key to group them.

**Algorithm:**

1.  Create a hash map: {sorted\_string: \[original\_strings\]}
    
2.  For each string:
    
    -   Sort the string to get its "canonical form"
        
    -   Add original string to the list for that key
        
3.  Return all values (lists) from the hash map
    

**Alternative Key: Character Count** Instead of sorting, use a tuple of character counts as key:
`key = tuple(sorted(Counter(s).items())) # or: key = tuple(count[c] for c in 'abcdefghijklmnopqrstuvwxyz')`
This gives O(k) key computation instead of O(k log k) for sorting.

**Example:** \["eat","tea","tan","ate","nat","bat"\]
-   "eat" → sorted: "aet" → group\["aet"\] = \["eat"\]
-   "tea" → sorted: "aet" → group\["aet"\] = \["eat", "tea"\]
-   "tan" → sorted: "ant" → group\["ant"\] = \["tan"\]
-   "ate" → sorted: "aet" → group\["aet"\] = \["eat", "tea", "ate"\]
-   "nat" → sorted: "ant" → group\["ant"\] = \["tan", "nat"\]
-   "bat" → sorted: "abt" → group\["abt"\] = \["bat"\]
**Time: O(n × k log k)** where n = number of strings, k = max length **Space: O(n × k)** for storing all strings
**Time: O(n \* k log k) where k is max string length**
**Space: O(n \* k)**

```
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] hash = new char[26];
            for (char c : s.toCharArray()) {
                hash[c - 'a']++;
            }
            String key = new String(hash);
            map.computeIfAbsent(key, k -> new ArrayList<>());
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
```
