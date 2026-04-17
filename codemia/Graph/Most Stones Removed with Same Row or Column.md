# Most Stones Removed with Same Row or Column
On a 2D plane, we place n stones at some integer coordinate points. Each coordinate point may have at most one stone. A stone can be removed if it shares either the same row or the same column as another stone that has not been removed. Given an array stones of length n where stones[i] = [xi, yi] represents the location of the ith stone, return the largest possible number of stones that can be removed.

Example 1:
Input: [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
Output: 5

**Approach: Union-Find**

**Key Insight:** Stones connected by same row/column form components. In a component with k stones, we can remove k-1 stones (leaving one).

**Algorithm:**

1.  Union-Find with row and column as elements
    
2.  Use ~y (bitwise not) to differentiate columns from rows
    
3.  For each stone, union its row and column
    
4.  Count unique components, answer = stones - components
    

**Time Complexity:** O(n α(n)) **Space Complexity:** O(n)

**Time: O(n α(n))**

**Space: O(n)**

```
class Solution {
    Map<Integer, Integer> parent = new HashMap<>();

    public int removeStones(int[][] stones) {
        for (int[] stone : stones) {
            union(stone[0], ~stone[1]);
        }

        Set<Integer> roots = new HashSet<>();
        for (int[] stone : stones) {
            roots.add(find(stone[0]));
        }

        return stones.length - roots.size();
    }

    private int find(int x) {
        if (!parent.containsKey(x)) parent.put(x, x);
        if (parent.get(x) != x) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    private void union(int x, int y) {
        int px = find(x), py = find(y);
        if (px != py) parent.put(px, py);
    }
}
```
