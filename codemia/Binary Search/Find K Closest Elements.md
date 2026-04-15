# Find K Closest Elements
Given a sorted array, find the k closest integers to x. The result should also be sorted in ascending order.

Example 1:
Input: {"arr":[1,2,3,4,5],"k":4,"x":3}
Output: [1,2,3,4]

**Approach: Binary Search for Window Start**

The key insight is that the answer must be k consecutive elements from the sorted array. We binary search for the optimal starting index of this window.

**Why Binary Search Works:**

-   The result is always a contiguous subarray of size k
    
-   We're searching for the left boundary in range \[0, n-k\]
    
-   At each position, we can decide if the window should shift left or right
    

**The Comparison:** For a window starting at index `mid`:

-   Left boundary element: `arr[mid]`
    
-   Right boundary element (just outside window): `arr[mid + k]`
    
-   Compare distances: `x - arr[mid]` vs `arr[mid + k] - x`
    

If `x - arr[mid] > arr[mid + k] - x`:

-   Left element is farther from x than right element
    
-   Window should shift right → `left = mid + 1`
    

Otherwise:

-   Right element is farther or equal distance
    
-   Window should shift left or stay → `right = mid`
    

**Algorithm:**

1.  Binary search on range \[0, n-k\] for window start position
    
2.  At each mid, compare distance of arr\[mid\] and arr\[mid+k\] to x
    
3.  If left is farther, search right half; otherwise search left half
    
4.  Return arr\[left:left+k\]
    

**Example: arr = \[1,2,3,4,5\], k = 4, x = 3**

-   Search space: \[0, 1\] (n-k = 5-4 = 1)
    
-   mid = 0: Compare dist(1,3)=2 vs dist(5,3)=2. Equal, so right=0
    
-   left == right == 0, done
    
-   Result: arr\[0:4\] = \[1,2,3,4\]
    

**Why not use abs() for comparison?**

-   We use `x - arr[mid]` and `arr[mid+k] - x` (not absolute values)
    
-   This naturally handles cases where x is outside the array range
    
-   When x < arr\[mid\], `x - arr[mid]` is negative, meaning left is always "closer"
    

**Time Complexity:** O(log(n-k) + k) - binary search + slicing **Space Complexity:** O(1) - excluding output array

**Time: O(log(n-k) + k)**

**Space: O(1)**

```
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0, right = arr.length - k;

        while (left < right) {
            int mid = (left + right) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
```
