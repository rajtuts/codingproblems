# Missing Number  
Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.  

## Example 1:  
Input: [3,0,1]  
Output: 2  

**Approach 1: XOR (Bit Manipulation)**  
XOR has properties: a ^ a = 0 and a ^ 0 = a. If we XOR all indices \[0, n\] with all array values, duplicates cancel out, leaving only the missing number.  
**Algorithm:**  
1.  XOR all indices from 0 to n      
2.  XOR all numbers in array      
3.  Result is the missing number   
**Example: \[3,0,1\]**  
`Indices: 0 ^ 1 ^ 2 ^ 3 = 0 Array: 3 ^ 0 ^ 1 = 2 Total: 0 ^ 2 = 2 (missing!)`  
**Approach 2: Sum Formula**  
`expected = n * (n + 1) // 2 actual = sum(nums) return expected - actual`  
-   Expected sum for \[0, n\] is n\*(n+1)/2      
-   Subtract actual sum to get missing number      
**Approach 3: Hash Set**  
`full_set = set(range(n + 1)) return (full_set - set(nums)).pop()`  
Uses O(n) extra space.  
**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - XOR/sum approach  
**Time: O(n)**    **Space: O(1)**
```
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int xor = 0;

        for (int i = 0; i <= n; i++) {
            xor ^= i;
        }

        for (int num : nums) {
            xor ^= num;
        }

        return xor;
    }
}
```
