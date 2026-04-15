# Kth Missing Positive Number
Given an array arr of positive integers sorted in a strictly increasing order, and an integer k, return the kth positive integer that is missing from this array.

Example 1:
Input: {"arr":[2,3,4,7,11],"k":5}
Output: 9


**Approach: Binary Search on Missing Count**

**Key Insight:** For a sorted array, we can calculate how many positive integers are missing before any position:

-   At index i, if array had no gaps: value would be i+1
    
-   Missing count = arr\[i\] - (i + 1)
    

**Formula:** missing\_before(i) = arr\[i\] - (i + 1)

**Example:** arr = \[2,3,4,7,11\]

-   Index 0: arr\[0\]=2, missing = 2-1 = 1 (missing: 1)
    
-   Index 1: arr\[1\]=3, missing = 3-2 = 1 (still 1)
    
-   Index 2: arr\[2\]=4, missing = 4-3 = 1 (still 1)
    
-   Index 3: arr\[3\]=7, missing = 7-4 = 3 (missing: 1,5,6)
    
-   Index 4: arr\[4\]=11, missing = 11-5 = 6 (missing: 1,5,6,8,9,10)
    

**Algorithm:**

1.  Binary search for first index where missing\_count >= k
    
2.  When search ends, `left` is this index
    
3.  Answer = left + k (k missing numbers + left present numbers)
    

**Example:** k=5

-   Search finds left=4 (missing=6 >= 5)
    
-   But we want 5th missing, not 6th
    
-   Answer = 4 + 5 = 9 ✓
    

**Why left + k?**

-   `left` = count of array elements before our answer
    
-   `k` = count of missing elements up to our answer
    
-   Answer = left + k (total count of positive integers)
    

**Time Complexity:** O(log n) - binary search **Space Complexity:** O(1) - constant space

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int findKthPositive(int[] arr, int k) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // Number of missing positives before arr[mid]
            int missing = arr[mid] - (mid + 1);
            if (missing < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // left is the first index where missing >= k
        // Answer = k + left (since left elements are present before answer)
        return left + k;
    }
}
```
