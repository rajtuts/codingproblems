# Kth Smallest Element in a Sorted Matrix
Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix. Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example 1:
Input: {"matrix":[[1,5,9],[10,11,13],[12,13,15]],"k":8}
Output: 13

**Approach: Binary Search on Value**

**Key Insight:** Instead of finding the kth element directly, binary search on the value range \[min, max\]. For each mid value, count how many elements are ≤ mid. Adjust search based on count vs k.

**Algorithm:**

1.  left = matrix\[0\]\[0\], right = matrix\[-1\]\[-1\]
    
2.  While left < right:
    
    -   mid = (left + right) / 2
        
    -   count = count\_less\_equal(mid)
        
    -   If count < k: left = mid + 1
        
    -   Else: right = mid
        
3.  Return left
    

**Count Function (Staircase):**

-   Start from bottom-left
    
-   If value ≤ mid: add row+1 elements, move right
    
-   Else: move up
    

**Example:** matrix = \[\[1,5,9\],\[10,11,13\],\[12,13,15\]\], k = 8

-   Range: \[1, 15\]
    
-   mid = 8: count elements ≤ 8 = 2 < 8 → left = 9
    
-   mid = 12: count ≤ 12 = 6 < 8 → left = 13
    
-   mid = 14: count ≤ 14 = 8 ≥ 8 → right = 14
    
-   mid = 13: count ≤ 13 = 8 ≥ 8 → right = 13
    
-   Return 13 ✓
    

**Why Binary Search on Value:**

-   We don't know exact values, but can count efficiently
    
-   O(n) counting per iteration, O(log(max-min)) iterations
    

**Time Complexity:** O(n × log(max-min)) - n for counting **Space Complexity:** O(1) - constant variables

**Time: O(n \* log(max-min))**

**Space: O(1)**

```
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int l = matrix[0][0], r = matrix[n - 1][n - 1];
        int res = -1;
        
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (countLessEqual(matrix, mid) >= k) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }
    
    private int countLessEqual(int[][] matrix, int mid) {
        int count = 0, n = matrix.length, c = n - 1;
        for (int r = 0; r < n; r++) {
            while (c >= 0 && matrix[r][c] > mid) c--;
            count += (c + 1);
        }
        return count;
    }
}
```
