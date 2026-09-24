package matrix;

public class DiagonalSumLeetCode1572 {

    public int diagonalSum(int[][] mat) {
        int n = mat.length; // nxn
        int sum = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (r == c && r + c == n - 1) { // overlapping only for n is odd
                    sum += mat[r][c];
                } else if (r == c) {// primary/main diagonal element
                    sum += mat[r][c];
                } else if (r + c == n - 1) {// secondary diagonal element
                    sum += mat[r][c];

                }
            }
        }
        return sum;
    }
}// TC: 0(n^2), SC: 0(1)