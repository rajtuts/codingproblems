# Shortest Palindrome
You are given a string s. You can convert s to a palindrome by adding characters in front of it. Return the shortest palindrome you can find by performing this transformation.  

## Example 1:  
Input: {"s":"aacecaaa"}  
Output: "aaacecaaa"  

## Use KMP failure function on s + '#' + reverse(s). The last failure value gives length of longest palindrome prefix.

## Time: O(n)  
## Space: O(n)  

```
class Solution {
    public String shortestPalindrome(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + rev;

        // Build KMP failure table
        int n = combined.length();
        int[] fail = new int[n];
        for (int i = 1; i < n; i++) {
            int j = fail[i - 1];
            while (j > 0 && combined.charAt(i) != combined.charAt(j)) {
                j = fail[j - 1];
            }
            if (combined.charAt(i) == combined.charAt(j)) j++;
            fail[i] = j;
        }

        return rev.substring(0, s.length() - fail[n - 1]) + s;
    }
}
```
