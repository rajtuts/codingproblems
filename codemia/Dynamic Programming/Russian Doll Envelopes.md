# Russian Doll Envelopes
You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] represents the width and height of an envelope. Return the maximum number of envelopes you can Russian doll (i.e., put one inside other).

Example 1:
Input: {"envelopes":[[5,4],[6,4],[6,7],[2,3]]}
Output: 3

Sort by width asc, height desc. This ensures same-width envelopes can't nest. Then find LIS on heights using binary search.

Time: O(n log n)
Space: O(n)

```
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        // Sort by width ascending, height descending
        Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);

        // Find LIS on heights using binary search
        List<Integer> lis = new ArrayList<>();

        for (int[] env : envelopes) {
            int h = env[1];
            int pos = Collections.binarySearch(lis, h);
            if (pos < 0) pos = -(pos + 1);
            if (pos == lis.size()) {
                lis.add(h);
            } else {
                lis.set(pos, h);
            }
        }

        return lis.size();
    }
}

```
