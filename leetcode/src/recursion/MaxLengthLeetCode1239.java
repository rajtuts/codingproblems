package recursion;

import java.util.List;

public class MaxLengthLeetCode1239 {
    public boolean isUnique(String str) {
        int[] arr = new int[26];
        for (int i = 0; i < str.length(); i++) {
            arr[str.charAt(i) - 'a']++;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 1) return false;
        }
        return true;
    }
    public int dfs(List<String> arr, String str, int curr) {
        if (curr == arr.size()) {
            return str.length();
        }
        int left = 0, right = 0;
        String tmp = str + arr.get(curr);
        if (isUnique(tmp)) {
            left = dfs(arr, tmp, curr + 1);
        }
        right = dfs(arr, str, curr + 1);
        return Math.max(left, right);
    }
    public int maxLength(List<String> arr) {
        return dfs(arr, "", 0);
    }
} // TC: O(n * 2^n), SC: O(n)