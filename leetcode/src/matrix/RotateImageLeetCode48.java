package matrix;

public class RotateImageLeetCode48 {
    public void rotate(int[][] matrix) {
/*
1. find out transpose matrix
2. reverse each row of transpose matrix
*/
        int n = matrix.length;
        for (int r = 0; r < n; r++) {
            for (int c = r + 1; c < n; c++) {
// swap r,c and c,r
                int tmp = matrix[r][c];
                matrix[r][c] = matrix[c][r];
                matrix[c][r] = tmp;
            }
        }
        for (int r = 0; r < n; r++) {
            int left = 0, right = n - 1;
            while (left <= right) {
                int tmp = matrix[r][left];
                matrix[r][left] = matrix[r][right];
                matrix[r][right] = tmp;
                left++;
                right--;
            }
        }
    }
}// TC: 0(n^2), scx0(1)
