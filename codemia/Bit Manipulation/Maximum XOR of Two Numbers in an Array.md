# Maximum XOR of Two Numbers in an Array
Given an integer array nums, return the maximum result of nums[i] XOR nums[j], where 0 <= i <= j < n.

Example 1:
Input: [3,10,5,25,2,8]
Output: 28

**Approach: Greedy Bit-by-Bit Construction**

**Key Insight:** Build the maximum XOR from the most significant bit. At each bit position, check if we can set that bit to 1 using the property: if a XOR b = c, then a XOR c = b.

**Algorithm:**

1.  For each bit from MSB to LSB
    
2.  Store all prefixes (numbers masked to current position)
    
3.  Try to set current bit to 1 in result
    
4.  Check if candidate XOR prefix exists in prefixes
    

**Time Complexity:** O(32n) = O(n) **Space Complexity:** O(n)

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int findMaximumXOR(int[] nums) {
        int maxXor = 0;
        int mask = 0;

        for (int i = 31; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> prefixes = new HashSet<>();

            for (int num : nums) {
                prefixes.add(num & mask);
            }

            int candidate = maxXor | (1 << i);

            for (int prefix : prefixes) {
                if (prefixes.contains(candidate ^ prefix)) {
                    maxXor = candidate;
                    break;
                }
            }
        }

        return maxXor;
    }
}
```
