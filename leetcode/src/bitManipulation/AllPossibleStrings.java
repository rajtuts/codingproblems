package bitManipulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllPossibleStrings {
    public List<String> allPossibleStrings(String s)
    {
        int n = s.length();
        List<String> powerset = new ArrayList<>();
        int max = (1 << n) - 1;
        for (int i = 1; i <= max; i++) {
            String str = "";
            for (int bit = 0; bit < n; bit++) {
                if ((i & (1 << bit)) != 0) {
                    str = s.charAt(n-1-bit) + str;
                }
            }
            powerset.add(str);
        }
        Collections.sort(powerset);
        return powerset;
    }
}
