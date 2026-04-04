# Largest Number
Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.  

## Example 1:  
Input: {"nums":[10,2]}  
Output: "210"  

**Approach: Custom Sort by Concatenation**

**Key Insight:** To compare two numbers a and b, compare strings "ab" vs "ba". If "ab" > "ba", then a should come first.

**Algorithm:**  
1.  Convert numbers to strings      
2.  Sort with custom comparator: compare a+b vs b+a      
3.  If a+b > b+a: a comes first (for largest number)      
4.  Join sorted strings      
5.  Handle edge case: all zeros → return "0"      

**Example:** nums = \[3, 30, 34, 5, 9\]  
-   Compare "3" and "30": "330" > "303" → 3 before 30      
-   Compare "3" and "34": "334" < "343" → 34 before 3      
-   Compare "5" and "9": "59" < "95" → 9 before 5      
-   Sorted: \["9", "5", "34", "3", "30"\]      
-   Result: "9534330" ✓      

**Why This Works:**  
-   Direct string comparison doesn't work ("9" < "30" lexicographically)     
-   Comparing concatenations simulates which order is larger      
-   This comparison is transitive      

**Time Complexity:** O(n log n) - sorting with custom comparator **Space Complexity:** O(n) - string storage  
**Time: O(n log n)**  
**Space: O(n)**  

```
class Solution {
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));

        if (strs[0].equals("0")) return "0";

        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return sb.toString();
    }
}
```
