# Permutations
Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.

## Example 1:  
Input: [1,2,3]  
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]  

**Approach: Backtracking**  

**Key Insight:** A permutation is an arrangement where order matters. For each position, try placing each unused element, then recursively permute the remaining elements.  

**Algorithm:**  
1.  Define backtrack(path, remaining):      
    -   If no remaining elements: add path copy to result          
    -   For each element in remaining:          
        -   Add to path              
        -   Recurse with remaining minus this element             
        -   Remove from path (backtrack)              
2.  Start with empty path and full nums     

**Example:** nums = \[1,2,3\]  
-   Position 0: try 1 → recurse \[2,3\]      
    -   Position 1: try 2 → \[3\] → \[1,2,3\] ✓         
    -   Position 1: try 3 → \[2\] → \[1,3,2\] ✓          
-   Position 0: try 2 → \[1,3\]      
    -   \[2,1,3\] ✓, \[2,3,1\] ✓          
-   Position 0: try 3 → \[1,2\]      
    -   \[3,1,2\] ✓, \[3,2,1\] ✓          

**Alternative: Swap-Based**  
-   Swap current position with each remaining position      
-   More efficient: O(1) for each swap      

**Time Complexity:** O(n! × n) - n! permutations, O(n) to copy each **Space Complexity:** O(n) - recursion depth and path  
**Time: O(n! \* n)**
**Space: O(n)**

```

```
