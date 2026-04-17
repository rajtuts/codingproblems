# The Skyline Problem
A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the locations and heights of all the buildings, return the skyline formed by these buildings collectively.

Example 1:
Input: {"buildings":[[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]}
Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]

Use sweep line with events at building starts and ends. Maintain a max heap of active heights. When max height changes, add a key point.

Time: O(n log n)
Space: O(n)

```
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<int[]> events = new ArrayList<>();
        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2], b[1]});
            events.add(new int[]{b[1], 0, 0});
        }
        events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        List<List<Integer>> result = new ArrayList<>();
        result.add(Arrays.asList(0, 0));

        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        heap.offer(new int[]{0, Integer.MAX_VALUE});

        for (int[] e : events) {
            int x = e[0], negH = e[1], end = e[2];

            while (heap.peek()[1] <= x) {
                heap.poll();
            }

            if (negH != 0) {
                heap.offer(new int[]{-negH, end});
            }

            int maxH = heap.peek()[0];
            if (result.get(result.size() - 1).get(1) != maxH) {
                result.add(Arrays.asList(x, maxH));
            }
        }

        return result.subList(1, result.size());
    }
}
```

