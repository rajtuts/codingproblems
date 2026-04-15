# Single Element in a Sorted Array
You are given a sorted array consisting of only integers where every element appears exactly twice, except for one element which appears exactly once. Return the single element that appears only once.

Example 1:
Input: {"nums":[1,1,2,3,3,4,4,8,8]}
Output: 2

**Approach: Binary Search on Pair Alignment**

**Key Insight:** Before the single element, pairs start at even indices (0,1), (2,3). After, pairs shift to odd indices (1,2), (3,4). Check alignment to determine which side.

**Algorithm:**

1.  Binary search on even indices only
    
2.  At mid (ensure mid is even):
    
    -   If nums\[mid\] == nums\[mid+1\]: single is to the right
        
    -   Else: single is at mid or to the left
        
3.  When left == right, found the single element
    

**Example:** nums = \[1,1,2,3,3,4,4,8,8\]

-   mid=4: nums\[4\]=3, nums\[5\]=4 ≠ → single at/before 4
    
-   mid=2: nums\[2\]=2, nums\[3\]=3 ≠ → single at/before 2
    
-   mid=0: nums\[0\]=1, nums\[1\]=1 = → single after 0
    
-   left=2: nums\[2\]=2 is the single
    

**Time Complexity:** O(log n) - binary search **Space Complexity:** O(1) - only pointers

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int l = 0, r = nums.length - 1;
        
        while (l <= r) {
            int m = l + (r - l) / 2;
            
            if ((m - 1 < 0 || nums[m - 1] != nums[m]) && 
                (m + 1 == nums.length || nums[m] != nums[m + 1])) {
                return nums[m];
            }
            
            int leftSize = (m - 1 >= 0 && nums[m - 1] == nums[m]) ? m - 1 : m;
            
            if (leftSize % 2 == 1) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }
}
```
