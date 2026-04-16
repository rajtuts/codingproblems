# Frog Jump
A frog is crossing a river. The river is divided into some number of units, and at each unit, there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water. Given a list of stones positions in sorted ascending order, determine if the frog can cross the river by landing on the last stone. Initially, the frog is on the first stone and assumes the first jump must be 1 unit.

Example 1:
Input: {"stones":[0,1,3,5,6,8,12,17]}
Output: true

Track possible jump sizes at each stone. Start at stone 1 with jump size 1. For each stone, try k-1, k, k+1 jumps. If land on valid stone, add that jump size to that stone's set. Return true if reach last stone.

Time: O(n^2)
Space: O(n^2)

```
class Solution {
    public boolean canCross(int[] stones) {
        if (stones[1] != 1) return false;

        Set<Integer> stoneSet = new HashSet<>();
        for (int s : stones) stoneSet.add(s);
        int lastStone = stones[stones.length - 1];

        Map<Integer, Set<Integer>> dp = new HashMap<>();
        for (int stone : stones) dp.put(stone, new HashSet<>());
        dp.get(1).add(1);

        for (int i = 1; i < stones.length; i++) {
            int stone = stones[i];
            for (int k : dp.get(stone)) {
                for (int nextK : new int[]{k - 1, k, k + 1}) {
                    if (nextK > 0 && stoneSet.contains(stone + nextK)) {
                        if (stone + nextK == lastStone) return true;
                        dp.get(stone + nextK).add(nextK);
                    }
                }
            }
        }

        return false;
    }
}

```
