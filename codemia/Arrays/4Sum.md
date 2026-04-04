# 4Sum
Find all unique quadruplets in the array which gives the sum of target.  

## Example 1:  
Input: {"nums":[1,0,-1,0,-2,2],"target":0}  
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]  

**Approach: Sort + Two Pointers**  
**Key Insight:** Fix two elements with nested loops, then use two pointers to find the remaining two elements that make the target sum.  
**Algorithm:**  
1.  Sort the array      
2.  For i from 0 to n-4:      
    -   For j from i+1 to n-3:          
        -   Use two pointers (left=j+1, right=n-1) to find pairs              
        -   If sum < target: left++              
        -   If sum > target: right--              
        -   If sum == target: record quadruplet, skip duplicates              
3.  Skip duplicates at each level to avoid duplicate quadruplets    

**Example:** nums = \[1,0,-1,0,-2,2\], target = 0  
-   Sorted: \[-2,-1,0,0,1,2\]      
-   i=0 (-2), j=1 (-1), two pointers find \[0,2\] and \[0,1,2\]...      
-   Result: \[\[-2,-1,1,2\],\[-2,0,0,2\],\[-1,0,0,1\]\]      

**Duplicate Handling:**  
-   Skip same values for i, j, left, right after finding a match      
-   Prevents duplicate quadruplets in output      

**Time Complexity:** O(n³) - two loops + two pointers **Space Complexity:** O(1) - ignoring output space  
**Time: O(n^3)**  
**Space: O(1)**  

```
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int l = j + 1, r = n - 1;
                while (l < r) {
                    long sum = (long) nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum < target) {
                        l++;
                    } else if (sum > target) {
                        r--;
                    } else {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        l++;
                        while (l < r && nums[l] == nums[l - 1]) {
                            l++;
                        }
                    }
                }
            }
        }
        return res;
    }
}
```
