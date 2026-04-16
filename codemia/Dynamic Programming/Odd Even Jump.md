# Odd Even Jump
You are given an integer array arr. From some starting index, you can make a series of jumps. The first jump is odd, the second is even, and so on. From index i, during odd jumps you can jump to j where arr[j] is the smallest value >= arr[i] with j > i. During even jumps, jump to j where arr[j] is the largest value <= arr[i] with j > i. Return the number of good starting indices.

Example 1:
Input: {"arr":[10,13,12,14,15]}
Output: 2

Work backwards. For each position, find the next odd jump destination (smallest value >= current) and even jump destination (largest value <= current). Use DP: odd[i] = True if can reach end starting with odd jump. odd[i] depends on even[next], and vice versa.

Time: O(n log n)
Space: O(n)

```
class Solution {
    public int oddEvenJumps(int[] arr) {
        int n = arr.length;
        boolean[] odd = new boolean[n];
        boolean[] even = new boolean[n];
        odd[n - 1] = even[n - 1] = true;

        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(arr[n - 1], n - 1);

        for (int i = n - 2; i >= 0; i--) {
            Integer oddKey = map.ceilingKey(arr[i]);
            Integer evenKey = map.floorKey(arr[i]);

            if (oddKey != null) odd[i] = even[map.get(oddKey)];
            if (evenKey != null) even[i] = odd[map.get(evenKey)];

            map.put(arr[i], i);
        }

        int count = 0;
        for (boolean b : odd) if (b) count++;
        return count;
    }
}
```
