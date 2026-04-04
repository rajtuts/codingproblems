# Contains Duplicate II
Given an integer array nums and an integer k, return true if there are two distinct indices i and j in the array such that nums[i] == nums[j] and abs(i - j) <= k.

## Example 1:  
Input: {"nums":[1,2,3,1],"k":3}  
Output: true  

**Approach: Hash Map for Last Index**
**Key Insight:** Store the most recent index of each number. When we see a number again, check if the distance is ≤ k.
**Algorithm:**
1.  Create hash map: number → last seen index    
2.  For each index i:    
    -   If nums\[i\] in map and i - map\[nums\[i\]\] <= k: return True        
    -   Update map\[nums\[i\]\] = i        
3.  Return False    

**Example:** nums = \[1,2,3,1\], k = 3
-   i=0: map = {1: 0}    
-   i=1: map = {1: 0, 2: 1}    
-   i=2: map = {1: 0, 2: 1, 3: 2}    
-   i=3: nums\[3\]=1 in map, 3-0=3 <= 3 ✓    
-   Result: True ✓    

**Example:** nums = \[1,2,3,1,2,3\], k = 2
-   i=3: nums\[3\]=1, 3-0=3 > 2, update map\[1\]=3    
-   i=4: nums\[4\]=2, 4-1=3 > 2, update map\[2\]=4    
-   i=5: nums\[5\]=3, 5-2=3 > 2, update map\[3\]=5    
-   Result: False ✗    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(min(n, k)) - at most k+1 entries in map
**Time: O(n)**
**Space: O(min(n, k))**

```
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }
}
```
