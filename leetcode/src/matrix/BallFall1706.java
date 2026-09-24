package matrix;

import java.util.Arrays;

public class BallFall1706 {

    public int[] findBall(int[][] grid) {
        int[] result = new int[grid[0].length];
        Arrays.fill(result, -1);
        for (int col = 0; col < grid[0].length; col++) {
            int currCol = col;
            for (int currRow = 0; currRow < grid.length; currRow++) {
                int nextCol = currCol + grid[currRow][currCol];
                if (nextCol < 0 || nextCol > grid[0].length - 1) break;
                if (grid[currRow][currCol] != grid[currRow][nextCol]) break;
                if (currRow == grid.length - 1) {
                    result[col] = nextCol;
                }
                currCol = nextCol;
            }
        }

        return result;
    }
} //TC:O(M), SC: O(C)