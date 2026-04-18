# Range Sum Query - Mutable
Given an integer array nums, handle multiple queries of the following types: Update the value of an element in nums. Calculate the sum of the elements of nums between indices left and right inclusive where left <= right. Implement the NumArray class with update(index, val) and sumRange(left, right) methods.

Example 1:
Input: {"nums":[1,3,5],"operations":["sumRange","update","sumRange"],"params":[[0,2],[1,2],[0,2]]}
Output: [9,null,8]

Approach: Segment Tree (Iterative)

Key Insight: Use an array of size 2n. Leaves are at indices [n, 2n-1], internal nodes at [1, n-1].

Build: Bottom-up, each parent = sum of children Update: Update leaf, then propagate up Query: Process range from leaves upward, collecting values

Time Complexity: O(log n) for update and sumRange Space Complexity: O(n)

Time: O(log n)
Space: O(n)

```
class NumArray {
    private int n;
    private int[] tree;

    public NumArray(int[] nums) {
        n = nums.length;
        tree = new int[2 * n];
        for (int i = 0; i < n; i++) {
            tree[n + i] = nums[i];
        }
        for (int i = n - 1; i > 0; i--) {
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
    }

    public void update(int index, int val) {
        index += n;
        tree[index] = val;
        while (index > 1) {
            index /= 2;
            tree[index] = tree[2 * index] + tree[2 * index + 1];
        }
    }

    public int sumRange(int left, int right) {
        left += n;
        right += n;
        int total = 0;
        while (left <= right) {
            if (left % 2 == 1) { total += tree[left]; left++; }
            if (right % 2 == 0) { total += tree[right]; right--; }
            left /= 2;
            right /= 2;
        }
        return total;
    }
}
```
