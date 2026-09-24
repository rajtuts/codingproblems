package twoPointers;

//Leetcode: 125
public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        int p1 = 0, p2 = s.length() - 1;
        while (p1 <= p2) {
            char c1 = s.charAt(p1);
            char c2 = s.charAt(p2);
            if (!Character.isLetterOrDigit(c1)) p1++;
            else if (!Character.isLetterOrDigit(c2)) p2--;
            else {
                if (Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                    return false;
                }
                p1++;
                p2--;
            }
        }
        return true;
    }
}
// TC: 0(n), SC: 0(1)