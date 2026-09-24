package twoPointers;

//leetcode: 75
public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        int left = 0, right = 0; // 0 ~ left
        while (right < nums.length) {
            if (nums[left] != nums[right]) {
                left++;
                nums[left] = nums[right];
            }
            right++;
        }
        return left + 1;
    }
}
// TC: O(n), SC: 0ch