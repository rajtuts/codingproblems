# First Bad Version
You are a product manager and currently leading a team to develop a new product. Since each version is developed based on the previous version, all the versions after a bad version are also bad. Find the first bad version.

Example 1:
Input: {"n":10,"badVersion":4}
Output: 4

**Approach: Binary Search**

**Key Insight:** All versions from first bad onwards are bad. Use binary search to find the leftmost bad version.

**Algorithm:**

1.  Initialize left = 1, right = n
    
2.  While left < right:
    
    -   mid = left + (right - left) // 2 (avoid overflow)
        
    -   If isBadVersion(mid): right = mid (first bad is mid or earlier)
        
    -   Else: left = mid + 1 (first bad is after mid)
        
3.  Return left
    

**Example:** n = 5, first bad = 4

-   left=1, right=5, mid=3: isBadVersion(3)=False, left=4
    
-   left=4, right=5, mid=4: isBadVersion(4)=True, right=4
    
-   left=4, right=4: done
    
-   Result: 4 ✓
    

**Why This Binary Search Pattern:**

-   Looking for leftmost True (first bad)
    
-   When True: answer could be mid or earlier → right = mid
    
-   When False: answer is definitely after → left = mid + 1
    

**API Call Optimization:**

-   Binary search minimizes API calls to O(log n)
    
-   Each call to isBadVersion is expensive
    

**Time Complexity:** O(log n) - binary search **Space Complexity:** O(1) - only pointers

**Time: O(log n)**

**Space: O(1)**

```
public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
```
