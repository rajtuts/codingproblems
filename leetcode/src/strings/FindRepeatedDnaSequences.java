package strings;

import java.util.*;

public class FindRepeatedDnaSequences {

    public static void main(String[] args) {
        System.out.println(findRepeatedDnaSequences("sdssesssdsdklklll000"));
        System.out.println(findRepeatedDnaSequencesUsingHashMap("sssesdsdklklll000"));

    }
    public static List<String> findRepeatedDnaSequences(String s) {
        Set<String> set = new HashSet<>();
        Set<String> list = new HashSet<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String substring = s.substring(i, i + 10);
            if (set.contains(substring)) {
                list.add(substring);
            } else {
                set.add(substring);
            }
        }
        return new ArrayList<>(list);
    }

    //Using HashMap
    public static List<String> findRepeatedDnaSequencesUsingHashMap(String s) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String substring = s.substring(i, i + 10);
            map.put(substring, map.getOrDefault(substring, 0) + 1);
        }
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> item : map.entrySet()) {
            if (item.getValue() > 1) {
                res.add(item.getKey());
            }
        }
        return res;
    }
}
