# Minimum Number of Refueling Stops
A car travels from a starting position to a destination which is target miles east. Along the way, there are gas stations. Return the minimum number of refueling stops the car must make to reach destination. If it cannot reach the destination, return -1.

Example 1:
Input: {"target":100,"startFuel":10,"stations":[[10,60],[20,30],[30,30],[60,40]]}
Output: 2

Greedy with max heap. Add reachable stations to heap. When out of fuel, refuel from station with most fuel. Minimizes stops by always taking max available.

Time: O(n log n)
Space: O(n)

```
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int fuel = startFuel;
        int stops = 0;
        int i = 0;

        while (fuel < target) {
            while (i < stations.length && stations[i][0] <= fuel) {
                maxHeap.offer(stations[i][1]);
                i++;
            }

            if (maxHeap.isEmpty()) return -1;

            fuel += maxHeap.poll();
            stops++;
        }

        return stops;
    }
}
```
