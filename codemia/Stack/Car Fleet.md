Car Fleet
There are n cars going to the same destination along a one-lane road. The destination is target miles away. You are given two integer arrays position and speed, both of length n, where position[i] is the position of the ith car and speed[i] is the speed of the ith car (in miles per hour). A car can never pass another car ahead of it, but it can catch up to it and drive bumper to bumper at the same speed. The distance between these two cars is ignored - they are assumed to have the same position. A car fleet is some non-empty set of cars driving at the same position and same speed. Note that a single car is also a car fleet. If a car catches up to a car fleet at the destination point, it will still be considered as one car fleet. Return the number of car fleets that will arrive at the destination.

Example 1:
Input: {"target":12,"position":[10,8,0,5,3],"speed":[2,4,1,1,3]}
Output: 3

Sort by position descending. Stack tracks fleet times. If car takes longer than current fleet, it forms a new fleet.

Time: O(n log n)
Space: O(n)

```
class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        double[][] pair = new double[n][2];
        for (int i = 0; i < n; i++) {
            pair[i][0] = position[i];
            pair[i][1] = speed[i];
        }
        Arrays.sort(pair, (a, b) -> Double.compare(b[0], a[0]));
        
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            stack.push((target - pair[i][0]) / pair[i][1]);
            if (stack.size() >= 2 && stack.peek() <= stack.get(stack.size() - 2)) {
                stack.pop();
            }
        }
        return stack.size();
    }
}
```
