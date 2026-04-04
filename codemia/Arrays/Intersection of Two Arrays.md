# Intersection of Two Arrays
Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique and you may return the result in any order.

## Example 1:  
Input: {"nums1":[1,2,2,1],"nums2":[2,2]}  
Output: [2]  

**Approach: Set Intersection**  
**Key Insight:** Sets automatically handle duplicates and provide O(1) lookup. Python's set intersection operator `&` returns common elements.  

**Algorithm:**  
1.  Convert nums1 to a set (removes duplicates)    
2.  Convert nums2 to a set (removes duplicates)    
3.  Use `&` operator to get intersection      
4.  Convert result back to list      

**Example:** nums1 = \[1,2,2,1\], nums2 = \[2,2\]
-   set(nums1) = {1, 2}      
-   set(nums2) = {2}      
-   {1, 2} & {2} = {2}      
-   Result: \[2\] ✓    

**Alternative Approaches:**  
1.  **Two-pointer (if sorted):** O(n log n) time, O(1) extra space   
2.  **Hash set lookup:** Build set from smaller array, iterate larger array      

**When to Use Each:**  
-   Set intersection: Simplest, good for most cases      
-   Two-pointer: Better space if arrays are already sorted   
-   Hash lookup: Good when one array is much smaller      

**Time Complexity:** O(n + m) - building sets and intersection **Space Complexity:** O(n + m) - storing both sets  
**Time: O(n + m)**  **Space: O(n + m)**

```
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        for (int n : nums1) set1.add(n);

        Set<Integer> result = new HashSet<>();
        for (int n : nums2) {
            if (set1.contains(n)) result.add(n);
        }

        int[] arr = new int[result.size()];
        int i = 0;
        for (int n : result) arr[i++] = n;
        return arr;
    }
}
```
