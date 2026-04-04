# Majority Element
Given an array nums of size n, return the majority element. The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
  
## Example 1:  
Input: [2,2,1,1,1,2,2]   
Output: 2  

**Approach: Boyer-Moore Voting Algorithm**  
The key insight is that the majority element appears more than n/2 times, so it will "survive" any pairing with non-majority elements.  
**Algorithm:**  
1.  Initialize candidate = None, count = 0      
2.  For each number:      
    -   If count == 0: set candidate = current number         
    -   If current == candidate: increment count          
    -   Else: decrement count          
3.  Return candidate (guaranteed majority)  
**Visual Example: \[2,2,1,1,1,2,2\]**  
`num: 2, count=0 → candidate=2, count=1 num: 2, count=1, ==candidate → count=2 num: 1, count=2, !=candidate → count=1 num: 1, count=1, !=candidate → count=0 num: 1, count=0 → candidate=1, count=1 num: 2, count=1, !=candidate → count=0 num: 2, count=0 → candidate=2, count=1 Result: 2`  
**Why It Works:** 
-   Majority appears > n/2 times      
-   When we pair majority with non-majority, majority "wins" overall  
-   Like voting: majority always has more votes remaining  
**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - only two variables  
**Time: O(n)**  **Space: O(1)**

```
class Solution {
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }
}
```
