# Letter Combinations of a Phone Number
Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.  

## Example 1:  
Input: {"digits":"23"}  
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]  

**Approach: Backtracking with Phone Mapping**

**Key Insight:** Each digit maps to 3-4 letters. Build all combinations by trying each letter for the current digit, then recursively processing remaining digits.

**Algorithm:**  
1.  Create mapping: '2'→"abc", '3'→"def", etc.    
2.  Define backtrack(index, current\_path):      
    -   If index == len(digits): add path to result          
    -   For each letter in mapping\[digits\[index\]\]:          
        -   Recurse with index+1, path + letter              
3.  Handle empty input: return \[\]     

**Example:** digits = "23"  
-   '2' → a, b, c; '3' → d, e, f      
-   Start with 'a' → try 'd' → "ad" ✓      
-   Start with 'a' → try 'e' → "ae" ✓      
-   ... (9 combinations total)      

**Phone Mapping:**  
`2: abc 5: jkl 8: tuv 3: def 6: mno 9: wxyz 4: ghi 7: pqrs`  
**Branching Factor:**  
-   Most digits have 3 letters      
-   '7' and '9' have 4 letters      
-   Worst case: O(4^n)      
**Time Complexity:** O(4^n × n) - 4^n combinations, n to build each **Space Complexity:** O(n) - recursion depth  
**Time: O(4^n)**  
**Space: O(n)**

```
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) return res;
        
        String[] mapping = {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
        };
        
        backtrack(0, "", digits, mapping, res);
        return res;
    }
    
    private void backtrack(int i, String curStr, String digits, String[] mapping, List<String> res) {
        if (curStr.length() == digits.length()) {
            res.add(curStr);
            return;
        }
        
        String letters = mapping[digits.charAt(i) - '0'];
        for (char c : letters.toCharArray()) {
            backtrack(i + 1, curStr + c, digits, mapping, res);
        }
    }
}
```
