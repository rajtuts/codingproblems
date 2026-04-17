# Happy Number
Write an algorithm to determine if a number n is happy. A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.

Example 1:
Input: 19
Output: true

**Approach: Hash Set (Cycle Detection)**

The sequence of sum-of-squares will either:

1.  Eventually reach 1 (happy number)
    
2.  Enter a cycle that doesn't include 1 (not happy)
    

**Algorithm:**

1.  Use a set to track seen numbers
    
2.  While n != 1 and not in seen:
    
    -   Add n to seen
        
    -   Replace n with sum of squared digits
        
3.  Return true if n == 1
    

**Example: n = 19**

`19: 1² + 9² = 1 + 81 = 82 82: 8² + 2² = 64 + 4 = 68 68: 6² + 8² = 36 + 64 = 100 100: 1² + 0² + 0² = 1 → Happy!`

**Example: n = 2 (Not Happy)**

`2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4 (cycle!)`

**Alternative: Floyd's Cycle Detection**

`def getNext(n): return sum(int(d) ** 2 for d in str(n)) slow = n fast = getNext(n) while fast != 1 and slow != fast: slow = getNext(slow) fast = getNext(getNext(fast)) return fast == 1`

Uses O(1) space but same time complexity.

**Time Complexity:** O(log n) - digits decrease quickly **Space Complexity:** O(log n) - for hash set approach, O(1) for Floyd's

**Time: O(log n)**

**Space: O(log n)**

```
class Solution {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();

        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            int sum = 0;
            while (n > 0) {
                int digit = n % 10;
                sum += digit * digit;
                n /= 10;
            }
            n = sum;
        }

        return n == 1;
    }
}
```
