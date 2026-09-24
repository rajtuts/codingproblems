package recursion;

import java.util.ArrayList;
import java.util.List;

public class CombinationSumLeetCode39 {
    public static void dfs(int[] nums, List<List<Integer>> list, List<Integer> comb, int target, int sum, int start) {
        if (sum == target) {
            list.add(new ArrayList<>(comb));
            return;
        } else if (sum > target) return;

        for (int i = start; i < nums.length; i++) {
            comb.add(nums[i]);
            dfs(nums, list, comb, target, sum + nums[i], i);
            comb.remove(comb.size() - 1);
        }
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        dfs(candidates, list, new ArrayList<>(), target, 0, 0);
        return list;
    }
}