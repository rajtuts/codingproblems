# Merge Sorted Array
Merge nums2 into nums1 as one sorted array. nums1 has enough space (size m + n) to hold additional elements from nums2.

## Example 1:  
Input: {"nums1":[1,2,3,0,0,0],"m":3,"nums2":[2,5,6],"n":3}  
Output: [1,2,2,3,5,6]  

**Approach: Three Pointers from End**

**Key Insight:** Merge from the end of nums1 to avoid overwriting elements we haven't processed yet. The extra space at the end of nums1 is perfect for placing merged elements.
**Algorithm:**
1.  Set p1 = m-1 (end of nums1 elements)
2.  Set p2 = n-1 (end of nums2)    
3.  Set p = m+n-1 (end of nums1 array)    
4.  While p2 >= 0:    
    -   If p1 >= 0 and nums1\[p1\] > nums2\[p2\]: place nums1\[p1\] at p, decrement p1        
    -   Else: place nums2\[p2\] at p, decrement p2        
    -   Decrement p        
**Example:** nums1 = \[1,2,3,0,0,0\], m=3, nums2 = \[2,5,6\], n=3
-   p1=2, p2=2, p=5: 3<6 → nums1\[5\]=6, p2=1    
-   p1=2, p2=1, p=4: 3<5 → nums1\[4\]=5, p2=0    
-   p1=2, p2=0, p=3: 3>2 → nums1\[3\]=3, p1=1    
-   p1=1, p2=0, p=2: 2==2 → nums1\[2\]=2, p2=-1    
-   Result: \[1,2,2,3,5,6\] ✓    
**Why From End:**
-   No extra space needed    
-   Larger elements "bubble" to the back naturally    
-   Once p2 < 0, remaining nums1 elements are already in place    
**Time Complexity:** O(m+n) - each element processed once **Space Complexity:** O(1) - in-place merge

**Time: O(m+n)**  **Space: O(1)**

```
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1, p2 = n - 1, p = m + n - 1;

        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
    }
}
```
