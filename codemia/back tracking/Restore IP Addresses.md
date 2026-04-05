# Restore IP Addresses
A valid IP address consists of exactly four integers separated by single dots. Each integer is between 0 and 255 (inclusive) and cannot have leading zeros. Given a string s containing only digits, return all possible valid IP addresses that can be formed by inserting dots into s.  

## Example 1:  
Input: "25525511135"  
Output: ["255.255.11.135","255.255.111.35"]  

**Approach: Backtracking**

**Key Insight:** Try placing dots to create 4 segments. Each segment must be a valid IP octet (0-255, no leading zeros except "0" itself).

**Algorithm:**  
1.  Backtrack with: current position, number of segments, current path      
2.  Base case: 4 segments and reached end of string → valid IP      
3.  For segment length 1 to 3:      
    -   Extract substring, check if valid octet          
    -   Valid: 0-255, no leading zeros (except "0")          
    -   Recurse with next position, segment count + 1          
4.  Collect all valid IPs  
**Example:** s = "25525511135"  
-   Segment "2", "5", "5", "25511135" → "25511135" too long, backtrack      
-   Segment "255", "255", "11", "135" → all valid!  
-   Result includes "255.255.11.135" ✓        
**Validation Rules:**  
-   Length 1: always valid (0-9)      
-   Length 2-3: no leading zero, value ≤ 255      
-   Segment length > remaining characters: skip       
**Time Complexity:** O(3⁴) = O(1) - at most 3 choices × 4 segments **Space Complexity:** O(1) - fixed recursion depth  
**Time: O(1)**
**Space: O(1)**  

```
class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(String s, int start, List<String> path, List<String> result) {
        if (path.size() == 4) {
            if (start == s.length()) {
                result.add(String.join(".", path));
            }
            return;
        }

        for (int len = 1; len <= 3; len++) {
            if (start + len > s.length()) break;
            String segment = s.substring(start, start + len);
            if ((segment.length() > 1 && segment.charAt(0) == '0') || Integer.parseInt(segment) > 255) continue;
            path.add(segment);
            backtrack(s, start + len, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

