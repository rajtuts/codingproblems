package matrix;

public class TransposeMatrix867 {
    public class TransposeMatrix {
        public static int[][] transpose(int[][] matrix) {
            int n = matrix.length;
            for (int r = 0; r < n; r++) {
                for (int c = r + 1; c < n; c++) {
// r,c -> c,r
                    int tmp = matrix[r][c];
                    matrix[r][c] = matrix[c][r];
                    matrix[c][r] = tmp;
                }
            }
            return matrix;
        }
    }
}//  TC: 0(n^2), SC: 0(1)
