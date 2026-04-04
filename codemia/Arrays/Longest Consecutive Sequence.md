# Longest Consecutive Sequence
Find the length of the longest consecutive elements sequence in O(n) time.  

##  Example 1:  
Input: [100,4,200,1,3,2]  
Output: 4  

**Approach: Hash Set with Smart Starting Points**  

**Key Insight:** For O(n) time, only start counting from sequence beginnings. A number is a sequence start if (num-1) is not in the set. This avoids recounting elements.  

**Algorithm:**  
1.  Build set from nums for O(1) lookup      
2.  For each num in set:      
    -   If num-1 NOT in set: this is a sequence start          
        -   Count consecutive elements: num, num+1, num+2, ...              
        -   Update longest            
3.  Return longest     

**Example:** nums = \[100, 4, 200, 1, 3, 2\]  
-   Set: {100, 4, 200, 1, 3, 2}      
-   100: 99 not in set → start. 100, 101? No. Length 1      
-   4: 3 in set → skip (not a start)      
-   200: 199 not in set → start. Length 1      
-   1: 0 not in set → start! 1,2,3,4 → Length 4 ✓      
-   3: 2 in set → skip      
-   2: 1 in set → skip      

**Why O(n):**  
-   Each element is visited at most twice:      
    -   Once when checking if it's a start          
    -   Once when counting from another start          

**Time Complexity:** O(n) - each element processed at most twice **Space Complexity:** O(n) - hash set   
**Time: O(n)**  
**Space: O(n)**  

```
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) numSet.add(num);

        int longest = 0;

        for (int n : nums) {
            if (!numSet.contains(n - 1)) {
                int length = 0;
                while (numSet.contains(n + length)) {
                    length++;
                }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
```

