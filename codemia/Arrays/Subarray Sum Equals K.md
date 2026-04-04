# Subarray Sum Equals K
Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.  

## Example 1:
Input: {"nums":[1,1,1],"k":2}  
Output: 2  

**Approach: Prefix Sum + Hash Map**
The key insight is that if `prefix[j] - prefix[i] = k`, then the subarray from index `i+1` to `j` has sum `k`.
**Algorithm:**
1.  Initialize a hash map with `{0: 1}` - this handles subarrays starting from index 0.
2.  Keep a running prefix sum as we iterate.    
3.  At each position, check if `(currentSum - k)` exists in our map. If it does, we found that many subarrays ending at the current position with sum `k`.    
4.  Add the current prefix sum to the map.
**Why it works:**
-   We're essentially asking: "How many previous prefix sums equal `currentSum - k`?"    
-   Each such prefix sum represents a starting point where the subarray to the current position sums to `k`.
**Example with \[1, 1, 1\], k = 2:**
-   i=0: sum=1, check for -1 (not found), add {0:1, 1:1}    
-   i=1: sum=2, check for 0 (found! count=1), add {0:1, 1:1, 2:1}    
-   i=2: sum=3, check for 1 (found! count=2), add {0:1, 1:1, 2:1, 3:1}
**Time Complexity:** O(n) - single pass through the array **Space Complexity:** O(n) - hash map can store up to n different prefix sums

**Time: O(n)** 
**Space: O(n)**

```
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);
        int sum = 0, count = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int diff = sum - k;
            if (prefixCount.containsKey(diff)) {
                count += prefixCount.get(diff);
            }
            prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
```
