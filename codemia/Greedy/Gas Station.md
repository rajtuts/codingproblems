# Gas Station
There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i]. You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations. Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.

Example 1:
Input: {"gas":[1,2,3,4,5],"cost":[3,4,5,1,2]}
Output: 3

**Approach: Greedy with Single Pass**

**Key Insight:** If total gas ≥ total cost, a solution exists. If we run out of gas at station i, we can't start from any station between our start and i.

**Algorithm:**

1.  If sum(gas) < sum(cost): return -1
    
2.  Track current tank and starting station
    
3.  For each station i:
    
    -   tank += gas\[i\] - cost\[i\]
        
    -   If tank < 0: can't reach from current start
        
        -   Reset: start = i + 1, tank = 0
            
4.  Return start
    

**Example:** gas = \[1,2,3,4,5\], cost = \[3,4,5,1,2\]

-   Total: 15 >= 15 ✓
    
-   i=0: tank = 1-3 = -2 < 0 → start=1, tank=0
    
-   i=1: tank = 2-4 = -2 < 0 → start=2, tank=0
    
-   i=2: tank = 3-5 = -2 < 0 → start=3, tank=0
    
-   i=3: tank = 4-1 = 3
    
-   i=4: tank = 3+5-2 = 6
    
-   Result: 3
    

**Time Complexity:** O(n) - single pass **Space Complexity:** O(1) - only variables

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (Arrays.stream(gas).sum() < Arrays.stream(cost).sum()) return -1;
        
        int total = 0;
        int start = 0;
        
        for (int i = 0; i < gas.length; i++) {
            total += gas[i] - cost[i];
            if (total < 0) {
                total = 0;
                start = i + 1;
            }
        }
        return start;
    }
}
```
