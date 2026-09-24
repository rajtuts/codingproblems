package recursion;

import java.util.*;

public class FindPermutationDuplicates {
    public static void dfs(String str, String perm, List<String> list) {
        if (str.length() == 0) {
            list.add(perm);
        }
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!set.contains(ch)) {
                String tmp = str.substring(0, i) + str.substring(i + 1);
                dfs(tmp, perm + ch, list);
            }
            set.add(ch);
        }
    }
    public List<String> find_permutation(String S) {
        char[] arr = S.toCharArray();
        Arrays.sort(arr);
        String str = new String(arr);

        List<String> list = new ArrayList<>();
        dfs(str, "", list);
        return list;
    }
}
