package strings;

import java.util.HashMap;

public class ValidAnagramHashMap {
    public boolean isAnagram(String s, String t) {
        // if the length of s and t is not equal return false
        if (s.length() != t.length()) {
            return false;
        }
        HashMap<Character, Integer> hm1 = new HashMap<>();
        HashMap<Character, Integer> hm2 = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!hm1.containsKey(s.charAt(i))) {
                hm1.put(s.charAt(i), 1);
            } else {
                hm1.put(s.charAt(i), hm1.get(s.charAt(i)) + 1);
            }
        }
        for (int i = 0; i < t.length(); i++) {
            if (!hm2.containsKey(t.charAt(i))) {
            } else {
                hm2.put(t.charAt(i), hm2.get(t.charAt(i)) + 1);
            }
        }
        for (Character ch : hm1.keySet()) {
            if (!hm1.get(ch).equals(hm2.get(ch))) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagramOptimized1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] counter = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
            counter[t.charAt(i) - 'a']--;
        }
        for (int count : counter) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
    public boolean isAnagramOptimized2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] table = new int[26];
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            table[t.charAt(i) - 'a']--;
            if (table[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
