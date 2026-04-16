# Make Array Strictly Increasing
Given two integer arrays arr1 and arr2, return the minimum number of operations to make arr1 strictly increasing. In one operation, you can replace any element in arr1 with any element from arr2. If impossible, return -1.

Example 1:
Input: {"arr1":[1,5,3,6,7],"arr2":[1,3,2,4]}
Output: 1

Sort arr2 and deduplicate. DP where state is previous value. For each position, either keep current value (if > prev) or replace with smallest arr2 value > prev. Track min operations.

Time: O(n * m * log m)
Space: O(n + m)

```
class Solution {
    int[] arr1, arr2;
    Map<Long, Integer> memo = new HashMap<>();

    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        this.arr1 = arr1;
        TreeSet<Integer> set = new TreeSet<>();
        for (int x : arr2) set.add(x);
        this.arr2 = set.stream().mapToInt(i -> i).toArray();

        int ans = dp(0, -1);
        return ans >= 1e9 ? -1 : ans;
    }

    private int dp(int i, int prev) {
        if (i == arr1.length) return 0;

        long key = (long) i * 2000001 + prev + 1;
        if (memo.containsKey(key)) return memo.get(key);

        int result = (int) 1e9;

        if (arr1[i] > prev) {
            result = dp(i + 1, arr1[i]);
        }

        int idx = upperBound(arr2, prev);
        if (idx < arr2.length) {
            result = Math.min(result, 1 + dp(i + 1, arr2[idx]));
        }

        memo.put(key, result);
        return result;
    }

    private int upperBound(int[] arr, int val) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] <= val) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
```
