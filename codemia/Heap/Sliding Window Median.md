# Sliding Window Median
Given an array nums and an integer k, return an array of the median of each window of size k moving from left to right.

Example 1:
Input: {"nums":[1,3,-1,-3,5,3,6,7],"k":3}
Output: [1,-1,-1,3,5,6]

Maintain two heaps for lower and upper halves. Use lazy deletion to handle removed elements. Balance heaps after each operation. Median is top of max-heap (odd k) or average of both tops (even k).

Time: O(n log k)
Space: O(k)

```
class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        TreeMap<Integer, Integer> small = new TreeMap<>(Collections.reverseOrder());
        TreeMap<Integer, Integer> large = new TreeMap<>();
        int smallSize = 0, largeSize = 0;
        double[] result = new double[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            if (small.isEmpty() || nums[i] <= small.firstKey()) {
                small.merge(nums[i], 1, Integer::sum);
                smallSize++;
            } else {
                large.merge(nums[i], 1, Integer::sum);
                largeSize++;
            }

            // Balance
            while (smallSize > largeSize + 1) {
                int val = small.firstKey();
                large.merge(val, 1, Integer::sum);
                if (small.merge(val, -1, Integer::sum) == 0) small.remove(val);
                smallSize--; largeSize++;
            }
            while (largeSize > smallSize) {
                int val = large.firstKey();
                small.merge(val, 1, Integer::sum);
                if (large.merge(val, -1, Integer::sum) == 0) large.remove(val);
                largeSize--; smallSize++;
            }

            if (i >= k - 1) {
                if (k % 2 == 1) result[i - k + 1] = small.firstKey();
                else result[i - k + 1] = ((double) small.firstKey() + large.firstKey()) / 2;

                int toRemove = nums[i - k + 1];
                if (toRemove <= small.firstKey()) {
                    if (small.merge(toRemove, -1, Integer::sum) == 0) small.remove(toRemove);
                    smallSize--;
                } else {
                    if (large.merge(toRemove, -1, Integer::sum) == 0) large.remove(toRemove);
                    largeSize--;
                }
            }
        }
        return result;
    }
}
```
