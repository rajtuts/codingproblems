Queue Reconstruction by Height
You are given an array of people, people, which are the attributes of some people in a queue (not necessarily in order). Each people[i] = [hi, ki] represents the ith person of height hi with exactly ki other people in front who have a height >= hi. Reconstruct and return the queue that is represented by the input array.

Example 1:
Input: {"people":[[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]}
Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]

**Approach: Greedy Insertion**

**Key Insight:** Process tallest people first. When inserting a person at index k, all previously inserted people are taller, so the k constraint is satisfied.

**Algorithm:**

1.  Sort by height descending, then by k ascending
    
2.  Insert each person at position k
    
3.  Shorter people inserted later don't affect taller people's k values
    

**Example:** \[\[7,0\],\[7,1\],\[6,1\],\[5,0\],\[5,2\],\[4,4\]\]

-   Insert \[7,0\] at 0: \[\[7,0\]\]
    
-   Insert \[7,1\] at 1: \[\[7,0\],\[7,1\]\]
    
-   Insert \[6,1\] at 1: \[\[7,0\],\[6,1\],\[7,1\]\]
    
-   And so on...
    

**Time Complexity:** O(n²) **Space Complexity:** O(n)

**Time: O(n²)**

**Space: O(n)**

```
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        // Sort by height desc, then by k asc
        Arrays.sort(people, (a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);

        List<int[]> result = new ArrayList<>();
        for (int[] person : people) {
            // Insert at index k
            result.add(person[1], person);
        }

        return result.toArray(new int[people.length][]);
    }
}
````
