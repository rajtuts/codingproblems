# Top K Frequent (Medium)
Given an integer array nums and an integer k, return the k most frequent elements.

## Example 1:
Input: {"nums":[1,1,1,2,2,3],"k":2}
Output: [1,2]

## Approach
The bucket sort approach works in O(n) time:

1.  **Count Frequencies**: Use a hash map to count how many times each element appears.    
2.  **Bucket Sort**: Create an array of buckets where index i contains all elements that appear i times. The max frequency is at most n (length of input).
3.  **Collect Results**: Iterate from the highest frequency bucket down, collecting elements until we have k elements.

**Why this works**: By using the frequency as an index, we avoid sorting (O(n log n)) and achieve O(n) time complexity.
**Alternative approaches**:
-   **Heap**: Use a min-heap of size k to track top k frequent elements - O(n log k)
-   **Quick Select**: Partition-based selection - O(n) average, O(n²) worst case
**Time: O(n)**   **Space: O(n)**

```
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }
        
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int key : count.keySet()) {
            int freq = count.get(key);
            if (buckets[freq] == null) buckets[freq] = new ArrayList<>();
            buckets[freq].add(key);
        }
        
        int[] res = new int[k];
        int index = 0;
        for (int i = buckets.length - 1; i > 0 && index < k; i--) {
            if (buckets[i] != null) {
                for (int n : buckets[i]) {
                    res[index++] = n;
                    if (index == k) return res;
                }
            }
        }
        return res;
    }
}
```
