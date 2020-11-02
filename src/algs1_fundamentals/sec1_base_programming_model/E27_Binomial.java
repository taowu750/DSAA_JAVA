package algs1_fundamentals.sec1_base_programming_model;


/**
 * 1.1.27<br/>
 * 二项分布式计算，计算进行n次试验，每次试验成功的概率是p，返回成功k次的概率。
 */
public class E27_Binomial {

    private static double[][] M;


    /**
     * 1.<br/>
     * 这个递归重复的调用太多，时间复杂度将会非常高。
     *
     * @param n 试验次数
     * @param k 成功次数
     * @param p 成功概率
     * @return 这个二项分布式的概率
     */
    public static double binomial1(int n, int k, double p) {
        if (n == 0 && k == 0) {
            return 1.0;
        }
        if (n < 0 || k < 0) {
            return 0.0;
        }

        return (1.0 - p) * binomial1(n - 1, k, p) + p * binomial1(n - 1, k - 1, p);
    }

    /**
     * 改进的递归实现：通过存储计算结果的方式复制了重复计算。
     *
     * @param n 试验次数
     * @param k 成功次数
     * @param p 成功概率
     * @return 这个二项分布式的概率
     */
    public static double binomial2(int n, int k, double p) {
        M = new double[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                M[i][j] = -1;
            }
        }

        return binomial2_sub(n, k, p);
    }

    /**
     * 非递归实现，直接计算组合数和阶乘。
     *
     * @param n 试验次数
     * @param k 成功次数
     * @param p 成功概率
     * @return 这个二项分布式的概率
     */
    public static double binomial3(int n, int k, double p) {
        if (n == 0 && k == 0) {
            return 1.0;
        }
        if (n < 0 || k < 0) {
            return 0.0;
        }

        double combination = factorial(n, n - k + 1) / factorial(k, 1);

        return combination * Math.pow(p, k) * Math.pow(1.0 - p, n - k);
    }


    private static double binomial2_sub(int n, int k, double p) {
        if (n == 0 && k == 0) {
            return 1.0;
        }
        if (n < 0 || k < 0) {
            return 0.0;
        }

        // 将计算结果存储起来，以存储的直接可用，无需再次计算
        if (M[n][k] == -1) {
            M[n][k] = (1.0 - p) * binomial2_sub(n - 1, k, p) + p * binomial2_sub(n - 1, k - 1, p);
        }

        return M[n][k];
    }

    private static int factorial(int start, int end) {
        int result  = 1;
        for (int i = start; i >= end; i--) {
            result *= i;
        }

        return result;
    }


    public static void main(String[] args) {
        System.out.println(binomial1(10, 5, 0.5));
        System.out.println(binomial2(10, 5, 0.5));
        System.out.println(binomial3(10, 5, 0.5));
    }
}
