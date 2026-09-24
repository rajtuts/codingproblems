package recursion;

import java.util.LinkedList;
import java.util.List;

public class LetterCombinationsLeetCode17 {
    public List<String> letterCombinations(String digits) {
        LinkedList<String> list = new LinkedList<>();
        if (digits.length() == 0) return list;
        list.add("");
        String[] map = new String[] { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
        for (int i = 0; i < digits.length(); i++) { // "234"
            int index = Character.getNumericValue(digits.charAt(i));
            while (list.peek().length() == i) {
                String tmp = list.remove();
                for (char ch : map[index].toCharArray()) {
                    list.add(tmp + ch);
                }
            }
        }
        return list;
    }
} // TC: O(4^n), SC: O(n)
