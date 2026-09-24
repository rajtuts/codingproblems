package dynamic;

public class IsInterleaveLeetCode97 {

    public boolean isInterleave(String s1, String s2, String s3) {
        int n1 = s1.length(), n2 = s2.length(), n3 = s3.length();
        if (n1 + n2 != n3) return false;
        boolean[][] dp = new boolean[n1 + 1][n2 + 1];
        dp[n1][n2] = true;
        for (int i = n1; i >= 0; i--) {
            for (int j = n2; j >= 0; j--) {
                if (i < n1 && s1.charAt(i) == s3.charAt(i + j) && dp[i + 1][j] == true) {
                    dp[i][j] = true;
                }
                if (j < n2 && s2.charAt(j) == s3.charAt(i + j) && dp[i][j + 1] == true) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[0][0];
    }
}// TC: 0(S*T), SC: 0(S*T)
