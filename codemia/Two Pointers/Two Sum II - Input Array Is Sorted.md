# Two Sum II - Input Array Is Sorted
Find two numbers that add up to a specific target number in a sorted array. Return 1-based indices.

Example 1:
Input: {"numbers":[2,7,11,15],"target":9}
Output: [1,2]

**Approach: Two Pointers**

**Key Insight:** In a sorted array, if sum is too small, increasing the left element helps. If too large, decreasing the right element helps.

**Algorithm:**

1.  Initialize left = 0, right = n-1
    
2.  While left < right:
    
    -   If nums\[left\] + nums\[right\] == target: return \[left+1, right+1\]
        
    -   If sum < target: left++ (need larger sum)
        
    -   If sum > target: right-- (need smaller sum)
        

**Example:** numbers = \[2,7,11,15\], target = 9

-   left=0, right=3: 2+15=17 > 9 → right--
    
-   left=0, right=2: 2+11=13 > 9 → right--
    
-   left=0, right=1: 2+7=9 ✓ → return \[1,2\]
    

**Time Complexity:** O(n) - single pass with two pointers **Space Complexity:** O(1) - only pointer variables

**Time: O(n)**

**Space: O(1)**

````
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int l = 0;
        int r = numbers.length - 1;

        while (l < r) {
            int sum = numbers[l] + numbers[r];

            if (sum > target) {
                r--;
            } else if (sum < target) {
                l++;
            } else {
                return new int[] { l + 1, r + 1 };
            }
        }
        return new int[0];
    }
}
```
