# Text Justification
Given an array of words and a maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.

## Example 1:  
Input: {"words":["This","is","an","example","of","text","justification."],"maxWidth":16}  
Output: ["This is an","example of text","justification. "]  

**Greedily pack words into lines. For full lines, distribute extra spaces evenly with more on left. Last line is left-justified.**  

**Time: O(n)**  
**Space: O(maxWidth)** 

```
class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        List<String> line = new ArrayList<>();
        int lineLen = 0;

        for (String word : words) {
            if (lineLen + word.length() + line.size() > maxWidth) {
                // Justify current line
                for (int i = 0; i < maxWidth - lineLen; i++) {
                    int idx = i % Math.max(line.size() - 1, 1);
                    line.set(idx, line.get(idx) + " ");
                }
                result.add(String.join("", line));
                line = new ArrayList<>();
                lineLen = 0;
            }
            line.add(word);
            lineLen += word.length();
        }

        // Last line: left-justified
        StringBuilder lastLine = new StringBuilder(String.join(" ", line));
        while (lastLine.length() < maxWidth) lastLine.append(" ");
        result.add(lastLine.toString());
        return result;
    }
}
```
