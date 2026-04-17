# Smallest Range Covering Elements from K Lists
You have k lists of sorted integers. Find the smallest range that includes at least one number from each of the k lists.

Example 1:
Input: {"nums":[[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]}
Output: [20, 24]

Use min heap with one element from each list. Track max. Pop min, update range if better, advance that list's pointer.

Time: O(n * log k)
Space: O(k)

```
class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int maxVal = Integer.MIN_VALUE;

        for (int i = 0; i < nums.size(); i++) {
            heap.offer(new int[]{nums.get(i).get(0), i, 0});
            maxVal = Math.max(maxVal, nums.get(i).get(0));
        }

        int[] result = {heap.peek()[0], maxVal};

        while (true) {
            int[] curr = heap.poll();
            int minVal = curr[0], listIdx = curr[1], elemIdx = curr[2];

            if (maxVal - minVal < result[1] - result[0]) {
                result = new int[]{minVal, maxVal};
            }

            if (elemIdx + 1 == nums.get(listIdx).size()) break;

            int nextVal = nums.get(listIdx).get(elemIdx + 1);
            maxVal = Math.max(maxVal, nextVal);
            heap.offer(new int[]{nextVal, listIdx, elemIdx + 1});
        }

        return result;
    }
}

```
