# Bus Routes
Given an array routes where routes[i] is a bus route that the ith bus repeats forever. You start at bus stop source and want to go to bus stop target. Return the least number of buses you must take to travel from source to target, or -1 if impossible.

Example 1:
Input: {"routes":[[1,2,7],[3,6,7]],"source":1,"target":6}
Output: 2

BFS treating each bus as a node. Build stop-to-buses mapping. From each stop, explore all buses. From each bus, explore all stops. Count bus transitions, not stop transitions.

Time: O(sum of route lengths)
Space: O(sum of route lengths)

```
class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) return 0;

        Map<Integer, Set<Integer>> stopToBuses = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                stopToBuses.computeIfAbsent(stop, k -> new HashSet<>()).add(i);
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        Set<Integer> visitedBuses = new HashSet<>();
        Set<Integer> visitedStops = new HashSet<>();
        visitedStops.add(source);

        for (int bus : stopToBuses.getOrDefault(source, new HashSet<>())) {
            queue.offer(new int[]{bus, 1});
            visitedBuses.add(bus);
        }

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int bus = curr[0], dist = curr[1];
            for (int stop : routes[bus]) {
                if (stop == target) return dist;
                if (!visitedStops.contains(stop)) {
                    visitedStops.add(stop);
                    for (int nextBus : stopToBuses.getOrDefault(stop, new HashSet<>())) {
                        if (!visitedBuses.contains(nextBus)) {
                            visitedBuses.add(nextBus);
                            queue.offer(new int[]{nextBus, dist + 1});
                        }
                    }
                }
            }
        }

        return -1;
    }
}
```
