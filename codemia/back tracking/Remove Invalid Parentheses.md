# Remove Invalid Parentheses  
Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid. Return all possible results.  

## Example 1:  
Input: {"s":"()())()"}  
Output: ["(())()","()()()"]  

**BFS level by level. At each level, try removing each parenthesis. Stop when valid strings are found.**

**Time: O(2^n)  
Space: O(2^n)**  

```
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.offer(s);
        visited.add(s);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (isValid(curr)) result.add(curr);
            }

            if (!result.isEmpty()) return result;

            int queueSize = queue.size();
            List<String> currLevel = new ArrayList<>();
            while (!queue.isEmpty()) currLevel.add(queue.poll());

            for (String curr : currLevel) {
                for (int i = 0; i < curr.length(); i++) {
                    char c = curr.charAt(i);
                    if (c == '(' || c == ')') {
                        String next = curr.substring(0, i) + curr.substring(i + 1);
                        if (!visited.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                }
            }
        }

        return Arrays.asList("");
    }

    private boolean isValid(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') count++;
            else if (c == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }
}
```
