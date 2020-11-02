package algs1_fundamentals.sec1_base_programming_model;


/**
 * 1.1.33<br/>
 * 编写矩阵库，实现矩阵向量的点乘、矩阵和矩阵之积、转置矩阵、矩阵和向量之积、向量和矩阵之积等方法
 */
public class E33_Matrix {

    /**
     * 矩阵向量的点乘
     *
     * @param x 矩阵向量
     * @param y 矩阵向量
     * @return 两者乘积
     */
    public static double dot(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("The vector 'x' must be the same length as the vector 'y'");
        }
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            result += x[i] * y[i];
        }

        return result;
    }

    /**
     * 矩阵的乘积
     *
     * @param a 矩阵
     * @param b 矩阵
     * @return 两者乘积
     */
    public static double[][] mult(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = a[i][j] * b[i][j];
            }
        }

        return result;
    }

    /**
     * 转置矩阵
     *
     * @param a 矩阵
     * @return 转置后的结果矩阵
     */
    public static double[][] transpose(double[][] a) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; i++) {
                result[i][j] = a[result.length - i - 1][result[i].length - j - 1];
            }
        }

        return result;
    }

    /**
     * 将矩阵与向量相乘
     *
     * @param a 矩阵
     * @param x 向量
     * @return 结果向量
     */
    public static double[] mult(double[][] a, double[] x) {
        double[] result = new double[a.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < x.length; j++) {
                result[i] += a[i][j] * x[j];
            }
        }

        return result;
    }

    /**
     * 向量乘以矩阵
     *
     * @param x 向量
     * @param a 矩阵
     * @return 结果向量
     */
    public static double[] mult(double[] x, double[][] a) {
        double[] result = new double[a[0].length];

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < a.length; j++) {
                result[j] += x[i] * a[j][i];
            }
        }

        return result;
    }
}
