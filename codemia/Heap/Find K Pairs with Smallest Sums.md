# Find K Pairs with Smallest Sums
Given two sorted arrays nums1 and nums2, find k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.

Example 1:
Input: {"nums1":[1,7,11],"nums2":[2,4,6],"k":3}
Output: [[1,2],[1,4],[1,6]]

**Approach: Min-Heap with Indices**

**Key Insight:** Start with the k smallest sums (each pairing nums1\[0\] with nums2\[0..k-1\]). Pop the smallest, then add the next candidate from nums1.

**Algorithm:**

1.  Initialize min-heap with (nums1\[0\]+nums2\[j\], 0, j) for j in 0..min(k, len(nums2))
    
2.  Pop k times:
    
    -   Pop smallest (sum, i, j)
        
    -   Add to result
        
    -   Push (nums1\[i+1\]+nums2\[j\], i+1, j) if i+1 < len(nums1)
        
3.  Return k pairs
    

**Example:** nums1 = \[1,7,11\], nums2 = \[2,4,6\], k = 3

-   Heap: \[(3,0,0), (5,0,1), (7,0,2)\]
    
-   Pop (3,0,0) → result=\[(1,2)\], push (9,1,0)
    
-   Heap: \[(5,0,1), (7,0,2), (9,1,0)\]
    
-   Pop (5,0,1) → result=\[(1,2),(1,4)\], push (11,1,1)
    
-   Pop (7,0,2) → result=\[(1,2),(1,4),(1,6)\]
    
-   Result: \[(1,2),(1,4),(1,6)\] ✓
    

**Why Heap:**

-   Always gives next smallest sum
    
-   Avoids generating all n×m pairs
    

**Time Complexity:** O(k log k) - k heap operations **Space Complexity:** O(k) - heap size

**Time: O(k log k)**

**Space: O(k)**

```
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0) return result;
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        Set<String> visited = new HashSet<>();
        heap.offer(new int[]{nums1[0] + nums2[0], 0, 0});
        visited.add("0,0");
        while (!heap.isEmpty() && result.size() < k) {
            int[] curr = heap.poll();
            int i = curr[1], j = curr[2];
            result.add(Arrays.asList(nums1[i], nums2[j]));
            if (i + 1 < nums1.length && !visited.contains((i+1)+","+j)) {
                heap.offer(new int[]{nums1[i+1] + nums2[j], i+1, j});
                visited.add((i+1)+","+j);
            }
            if (j + 1 < nums2.length && !visited.contains(i+","+(j+1))) {
                heap.offer(new int[]{nums1[i] + nums2[j+1], i, j+1});
                visited.add(i+","+(j+1));
            }
        }
        return result;
    }
}
```
