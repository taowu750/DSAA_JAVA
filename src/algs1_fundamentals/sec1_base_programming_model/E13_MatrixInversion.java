package algs1_fundamentals.sec1_base_programming_model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 编写一段代码，打印出一个 M 行 N 列的二维数组的转置。
 */
public class E13_MatrixInversion {

    public static int[][] inversion(int[][] a) {
        int m = a.length, n = a[0].length;
        int[][] at = new int[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                at[j][i] = a[i][j];
            }
        }

        return at;
    }

    @Test
    public void testInversion() throws Exception {
        int[][] a = {{1, 2, 3}, {4, 5, 6}};

        System.out.println(Arrays.deepToString(a));
        System.out.println(Arrays.deepToString(inversion(a)));
    }
}
