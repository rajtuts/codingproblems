# Count of Smaller Numbers After Self
Given an integer array nums, return an integer array counts where counts[i] is the number of smaller elements to the right of nums[i].

## Example 1:  
Input: {"nums":[5,2,6,1]}  
Output: [2,1,1,0]  

**Approach: Binary Search with Sorted List**  

Key Insight: Process from right to left, maintaining a sorted list of elements seen so far. For each element, the number of smaller elements to the right equals its insertion position in the sorted list.  

## Algorithm:  

1. Iterate through nums from right to left  
1. For each number, find its position using binary search  
1. This position equals the count of smaller elements  
1. Insert the number into the sorted list  
1. Time Complexity: O(n log n) with balanced BST or O(n²) with array Space Complexity: O(n)  

### Time: O(n log n)  
### Space: O(n)  

```
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        Integer[] result = new Integer[nums.length];
        List<Integer> sorted = new ArrayList<>();

        for (int i = nums.length - 1; i >= 0; i--) {
            int idx = bisectLeft(sorted, nums[i]);
            result[i] = idx;
            sorted.add(idx, nums[i]);
        }

        return Arrays.asList(result);
    }

    int bisectLeft(List<Integer> arr, int target) {
        int lo = 0, hi = arr.size();
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr.get(mid) < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
```
