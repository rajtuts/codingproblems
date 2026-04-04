# Find the Duplicate Number
Given an array of n + 1 integers where each integer is in the range [1, n], find the one repeated number without modifying the array and using only O(1) space.  

## Example 1:
Input: [1,3,4,2,2]  
Output: 2  

**Approach: Floyd's Cycle Detection (Tortoise and Hare)**
The brilliant insight is to treat the array as a linked list where `nums[i]` represents a pointer to the next node at index `nums[i]`.
**Why does a duplicate create a cycle?**
-   Array has n+1 elements, values in range \[1, n\]
-   Each value is a valid index (since values are 1 to n)    
-   If number `d` appears twice at indices i and j, both point to the same "next node" at index `d`    
-   This creates a cycle, and `d` is the entrance to this cycle   
**Example: nums = \[1, 3, 4, 2, 2\]**
Index: 0 → 1 → 3 → 2 → 4 → 2 → 4 → 2 → ... 
Value: 1 3 2 4 2 
       ↓ ↓ ↓ ↓ ↓ 
Next: 1 3 4 2 2 
Graph: 0 → 1 → 3 → 2 ⟲ 4 
               ↑_____↓`

The cycle entrance is 2 (the duplicate).
**Algorithm - Two Phases:**
**Phase 1: Find Intersection Point**
-   Slow pointer moves 1 step: `slow = nums[slow]`
-   Fast pointer moves 2 steps: `fast = nums[nums[fast]]`    
-   They will meet somewhere inside the cycle
    
**Phase 2: Find Cycle Entrance**
-   Reset slow to start: `slow = nums[0]`    
-   Move both pointers 1 step at a time    
-   They meet at the cycle entrance = duplicate number
  
**Why Phase 2 Works (Math):** Let's say:
-   Distance from start to cycle entrance = `a`    
-   Distance from entrance to meeting point = `b`
-   Cycle length = `c`
When they meet in Phase 1:
-   Slow traveled: `a + b`
-   Fast traveled: `a + b + c` (at least one extra cycle)
-   Since fast travels 2x: `2(a + b) = a + b + c` → `a = c - b`
This means: distance from start to entrance = distance from meeting point to entrance (going forward in cycle)!
**Time Complexity:** O(n) - each phase is O(n) **Space Complexity:** O(1) - only two pointers
**Time: O(n)**    **Space: O(1)**

```
class Solution {
    public int findDuplicate(int[] nums) {
        // Phase 1: Find intersection point in cycle
        int slow = nums[0];
        int fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2: Find entrance to cycle (the duplicate)
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}
```
