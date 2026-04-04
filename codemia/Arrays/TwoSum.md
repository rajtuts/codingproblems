# Two Sum
Given an array of integers, return indices of the two numbers such that they add up to a specific target.

## Example 1:
Input: {"nums":[2,7,11,15],"target":9}
Output: [0,1]

Approach: Hash Map (One Pass)

Key Insight: Instead of checking all pairs (O(n²)), we can use a hash map to find the complement in O(1) time.

Algorithm:

Create an empty hash map to store {number: index}
For each number at index i:
Calculate complement = target - number
If complement exists in hash map, we found our pair
Otherwise, add current number and its index to the map
Why it works: As we iterate, we've already stored all previous numbers. So when we find a number whose complement was seen before, we have our answer.

Example walkthrough: nums = [2,7,11,15], target = 9

i=0: complement = 9-2 = 7, not in map, store {2:0}
i=1: complement = 9-7 = 2, found in map! Return [0, 1]
Time: O(n)
Space: O(n)

```
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                return new int[] { map.get(diff), i };
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }
}
```
