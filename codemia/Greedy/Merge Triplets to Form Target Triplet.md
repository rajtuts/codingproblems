# Merge Triplets to Form Target Triplet
A triplet is an array of three integers. You are given a 2D integer array triplets, where triplets[i] = [ai, bi, ci]. You are also given an integer array target = [x, y, z] that describes the triplet you want to obtain. To obtain target, you may apply the following operation on triplets any number of times (possibly zero): Choose two indices (0-indexed) i and j (i != j) and update triplets[j] to become [max(ai, aj), max(bi, bj), max(ci, cj)]. Return true if it is possible to obtain the target triplet [x, y, z] as an element of triplets, or false otherwise.

Example 1:
Input: {"triplets":[[2,5,3],[1,8,4],[1,7,5]],"target":[2,7,5]}
Output: true

**Approach: Greedy Filtering**

**Key Insight:** A triplet can only contribute if ALL its elements are ≤ corresponding target elements. Otherwise, taking max would exceed target.

**Algorithm:**

1.  Filter triplets where all elements ≤ target
    
2.  For valid triplets, track which target positions they can satisfy (where triplet\[i\] == target\[i\])
    
3.  Return True if all 3 positions are covered
    

**Example:** triplets = \[\[2,5,3\],\[1,8,4\],\[1,7,5\]\], target = \[2,7,5\]

-   \[2,5,3\]: all ≤ target ✓, covers position 0 (2==2)
    
-   \[1,8,4\]: 8>7 ✗, skip
    
-   \[1,7,5\]: all ≤ target ✓, covers positions 1,2 (7==7, 5==5)
    
-   All positions covered: True
    

**Time Complexity:** O(n) - single pass through triplets **Space Complexity:** O(1) - only track 3 positions

**Time: O(n)**

**Space: O(1)**

```
class Solution {
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        Set<Integer> good = new HashSet<>();
        
        for (int[] t : triplets) {
            if (t[0] > target[0] || t[1] > target[1] || t[2] > target[2]) {
                continue;
            }
            
            for (int i = 0; i < 3; i++) {
                if (t[i] == target[i]) {
                    good.add(i);
                }
            }
        }
        return good.size() == 3;
    }
}
