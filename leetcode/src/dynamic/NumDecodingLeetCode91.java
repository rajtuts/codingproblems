package dynamic;

public class NumDecodingLeetCode91 {

    public int numDecodings(String s) {
        if (s.charAt(0) == '0') return 0;
        int n = s.length();

        int p = 1, pp = 1;
        for (int i = n - 1; i >= 0; i--) {
            int curr = s.charAt(i) == '0' ? 0 : p;
            if (i < n - 1 && (s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) <= '6')) {
                curr += pp;
            }
            pp = p;
            p = curr;
        }
        return p;
    }
} // TC: 0(n), SC: 0(1)
