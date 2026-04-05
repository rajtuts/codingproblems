# Combinations
Given two integers n and k, return all possible combinations of k numbers chosen from the range [1, n]. You may return the answer in any order.  

## Example 1:  
Input: {"n":4,"k":2}  
Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]  

**Approach: Backtracking with Pruning**

**Key Insight:** Combinations are order-independent: {1,2} = {2,1}. To avoid duplicates, only consider numbers GREATER than the last chosen number.

**Algorithm:**

1.  Backtrack with current combination and starting number      
2.  When combination has k elements → add to result      
3.  For each number from start to n:      
    -   Add number to current combination          
    -   Recurse with start = i + 1          
    -   Remove number (backtrack)          

**Example:** n=4, k=2  
`Start backtrack(1, []) Add 1 → backtrack(2, [1]) Add 2 → [1,2] ✓, backtrack Add 3 → [1,3] ✓, backtrack Add 4 → [1,4] ✓, backtrack Add 2 → backtrack(3, [2]) Add 3 → [2,3] ✓, backtrack Add 4 → [2,4] ✓, backtrack Add 3 → backtrack(4, [3]) Add 4 → [3,4] ✓, backtrack Add 4 → backtrack(5, [4]) (no more numbers, can't reach k=2) Result: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]`  
**Optimization (Pruning):** Stop early if remaining numbers can't complete k elements:  
`for i in range(start, n - (k - len(current)) + 2):`  
**Time Complexity:** O(k × C(n,k)) - generate C(n,k) combinations, each of size k **Space Complexity:** O(k) - recursion depth and current combination  
**Time: O(k \* C(n,k))**  
**Space: O(k)**

```
class Solution {
    public java.util.List<java.util.List<Integer>> combine(int n, int k) {
        java.util.List<java.util.List<Integer>> result = new java.util.ArrayList<>();
        backtrack(result, new java.util.ArrayList<>(), 1, n, k);
        return result;
    }

    private void backtrack(java.util.List<java.util.List<Integer>> result, java.util.List<Integer> current,
                          int start, int n, int k) {
        if (current.size() == k) {
            result.add(new java.util.ArrayList<>(current));
            return;
        }

        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrack(result, current, i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }
}
```
