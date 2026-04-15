# Median of Two Sorted Arrays
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

Example 1:
Input: {"nums1":[1,3],"nums2":[2]}
Output: 2.0

**Approach: Binary Search on Partitions**

**Key Insight:** We can partition both arrays such that all elements in the left halves are ≤ all elements in the right halves. The median comes from the boundary elements.

**Algorithm:**

1.  Ensure nums1 is the smaller array (binary search on smaller is faster)
    
2.  Binary search on partition position in nums1 (0 to m)
    
3.  Calculate corresponding partition in nums2: (m+n+1)/2 - partition1
    
4.  Check if partition is valid: max\_left1 ≤ min\_right2 AND max\_left2 ≤ min\_right1
    
5.  If valid: median = max(max\_left1, max\_left2) for odd total, average with min(min\_right1, min\_right2) for even
    

**Example:** nums1 = \[1,3\], nums2 = \[2\]

-   Total = 3 (odd), need (3+1)/2 = 2 elements in left half
    
-   Try partition1 = 1: left1 = \[1\], right1 = \[3\]
    
-   partition2 = 2-1 = 1: left2 = \[2\], right2 = \[\]
    
-   Check: max(1) ≤ min(∞) ✓ AND max(2) ≤ min(3) ✓
    
-   Median = max(1, 2) = 2 ✓
    

**Edge Cases:**

-   Empty partitions use -∞ for max\_left, +∞ for min\_right
    
-   Handle arrays of length 0
    

**Time Complexity:** O(log(min(m,n))) - binary search on smaller array **Space Complexity:** O(1) - only variables used

**Time: O(log(min(m,n)))**

**Space: O(1)**

```
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] A = nums1;
        int[] B = nums2;
        int total = nums1.length + nums2.length;
        int half = total / 2;
        
        if (B.length < A.length) {
            int[] temp = A; A = B; B = temp;
        }
        
        int l = 0, r = A.length - 1;
        while (true) {
            int i = (int)Math.floor((l + r) / 2.0);
            int j = half - (i + 1) - 1;
            
            double Aleft = i >= 0 ? A[i] : Double.NEGATIVE_INFINITY;
            double Aright = (i + 1) < A.length ? A[i + 1] : Double.POSITIVE_INFINITY;
            double Bleft = j >= 0 ? B[j] : Double.NEGATIVE_INFINITY;
            double Bright = (j + 1) < B.length ? B[j + 1] : Double.POSITIVE_INFINITY;
            
            if (Aleft <= Bright && Bleft <= Aright) {
                if (total % 2 != 0) return Math.min(Aright, Bright);
                return (Math.max(Aleft, Bleft) + Math.min(Aright, Bright)) / 2.0;
            } else if (Aleft > Bright) {
                r = i - 1;
            } else {
                l = i + 1;
            }
        }
    }
}
```
