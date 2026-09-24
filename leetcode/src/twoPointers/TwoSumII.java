package twoPointers;
//Leetcode: 167
public class TwoSumII {

    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sumOfCurrPair = numbers[left] + numbers[right];
            if (sumOfCurrPair == target) {
                break;
            } else if (sumOfCurrPair > target) {
                right--;
            } else {
                left++;
            }
        }
        return new int[]{left + 1, right + 1};
    }
}

// TC: 0(m), SC: 0(1)