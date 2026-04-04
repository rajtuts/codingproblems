# Encode and Decode Strings
Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is decoded back to the original list of strings.

## Example 1:  
Input: {"strs":["lint","code","love","you"]}  
Output: ["lint","code","love","you"]  

###### **Approach**

**Approach: Length Prefix Encoding**
**Key Insight:** Any delimiter could appear in strings. Using length prefix "len#string" is safe because we know exactly how many characters to read.
**Algorithm:** 
Encode:  
1.  For each string s, output: length(s) + "#" + s     
2.  Concatenate all encoded strings    

Decode:  
1.  Read digits until '#' to get length    
2.  Read exactly length characters as the string    
3.  Repeat until end 
**Example:** \["Hello", "World"\]
-   Encode: "5#Hello5#World"    
-   Decode:    
    -   Read "5#" → length=5, read "Hello"        
    -   Read "5#" → length=5, read "World"       

**Time Complexity:** O(n) - n is total characters **Space Complexity:** O(n) - encoded string  
**Time: O(n)**  **Space: O(n)**
```
public class Solution {
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s.length()).append("#").append(s);
        }
        return sb.toString();
    }

    public List<String> decode(String s) {
        List<String> res = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int j = i;
            while (s.charAt(j) != '#') j++;
            int len = Integer.parseInt(s.substring(i, j));
            res.add(s.substring(j + 1, j + 1 + len));
            i = j + 1 + len;
        }
        return res;
    }
}
```
