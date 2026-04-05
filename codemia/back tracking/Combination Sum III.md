# Combination Sum III
Find all valid combinations of k numbers that sum up to n such that: Only numbers 1 through 9 are used, each number is used at most once. Return a list of all possible valid combinations.  

## Example 1:  
Input: {"k":3,"n":7}  
Output: [[1,2,4]]  

**Approach: Backtracking**

**Key Insight:** Try each number 1-9, ensuring we use exactly k numbers that sum to n. Each number can only be used once, so start from the next number in each recursive call.

**Algorithm:**  
1.  Backtrack with parameters: start number, remaining count k, remaining sum n, current path      
2.  Base case: if k=0 and n=0, add path to result      
3.  For each number from start to 9:      
    -   If number > remaining sum, break (optimization)          
    -   Add number to path, recurse with k-1, n-number, start=number+1          
    -   Backtrack: remove number from path          

**Example:** k=3, n=7  
-   Start with 1: try \[1,2,4\] → sum=7 ✓      
-   Start with 1: try \[1,2,5\] → sum=8 > 7, skip     
-   Continue exploring...      
-   Result: \[\[1,2,4\]\] ✓      

**Pruning:**  
-   If current number > remaining sum: stop (can't make it smaller)      
-   Start from next number to avoid duplicates      

**Time Complexity:** O(C(9,k)) - combinations of 9 choose k **Space Complexity:** O(k) - recursion depth  
**Time: O(C(9,k))**
**Space: O(k)**

```
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(1, new ArrayList<>(), n, k, result);
        return result;
    }

    private void backtrack(int start, List<Integer> path, int remaining, int k, List<List<Integer>> result) {
        if (path.size() == k && remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (path.size() >= k || remaining < 0) return;

        for (int i = start; i <= 9; i++) {
            path.add(i);
            backtrack(i + 1, path, remaining - i, k, result);
            path.remove(path.size() - 1);
        }
    }
}
```
