##### **Contains Duplicate**

Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.

###### **Example 1:**

**Input:** \[1,2,3,1\]

**Output:** true

**Approach: Hash Set**
**Key Insight:** A set provides O(1) lookup to check if we've seen a number before.
**Algorithm:**
1.  Create an empty set
2.  For each number in the array:
    -   If it's already in the set, return True (duplicate found)        
    -   Otherwise, add it to the set        
3.  If we finish without finding duplicates, return False
**Alternative (One-liner):** Compare array length with set length. If different, duplicates exist: `len(nums) != len(set(nums))`
**Trade-offs:**
-   Hash Set: O(n) time, O(n) space - best for most cases
-   Sorting: O(n log n) time, O(1) space - better when memory is limited
    
**Time: O(n)** **Space: O(n)**

```
class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            if (set.contains(x)) {
                return true;
            }
            set.add(x);
        }
        return false;
    }
}
```
