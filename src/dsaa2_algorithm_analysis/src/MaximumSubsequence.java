package dsaa2_algorithm_analysis.src;


/**
 * 求解最大子序列和 - 2.4.3节
 *
 * @author wutao
 */
public class MaximumSubsequence {

    /**
     * 求解最大子序列和的第一种方法，通过穷举所有可能暴力求解<br/>
     * 时间复杂度：O(N3)
     *
     * @param a 整数数组，包含有正数和负数
     * @return 最大子序列和
     */
    public static int maxSubSum1(int[] a) {
        int maxSum = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                int thisSum = 0;

                for (int k = i; k <= j; k++) {
                    thisSum += a[k];
                }

                if (thisSum > maxSum) {
                    maxSum = thisSum;
                }
            }
        }

        return maxSum;
    }

    /**
     * 求解最大子序列和的第二种方法，通过改进第一种方法减少不必要的计算<br/>
     * 时间复杂度：O(N2)
     *
     * @param a 整数数组，包含有正数和负数
     * @return 最大子序列和
     */
    public static int maxSubSum2(int[] a) {
        int maxSum = 0;

        for (int i = 0; i < a.length; i++) {
            int thisSum = 0;
            for (int j = i; j < a.length; j++) {
                thisSum += a[j];

                if (thisSum > maxSum) {
                    maxSum = thisSum;
                }
            }
        }

        return maxSum;
    }

    /**
     * 求解最大子序列和的第三种方法，使用了分治算法求解<br/>
     * 时间复杂度：O(NlogN)
     *
     * @param a 整数数组，包含有正数和负数
     * @return 最大子序列和
     */
    public static int maxSubSum3(int[] a) {
        return maxSubSum3(a, 0, a.length - 1);
    }

    /**
     * 联机算法，只对数据进行一次扫描<br/>
     * 时间复杂度：O(N)
     *
     * @param a 整数数组，包含有正数和负数
     * @return 最大子序列和
     */
    public static int maxSubSum4(int[] a) {
        int maxSum = 0, thisSum = 0;

        for (int i = 0; i < a.length; i++) {
            thisSum += a[i];

            if (thisSum > maxSum) {
                maxSum = thisSum;
            } else if (thisSum < 0) {
                thisSum = 0;
            }
        }

        return maxSum;
    }


    public static int max3(int a, int b, int c) {
        int max;

        max = (a > b ? a : b);
        max = (max > c ? max : c);

        return max;
    }


    private static int maxSubSum3(int[] a, int left, int right) {
        if (left == right) {
            if (a[left] > 0) {
                return a[left];
            } else {
                return 0;
            }
        }

        int center      = (left + right) / 2;
        int maxLeftSum  = maxSubSum3(a, left, center);
        int maxRightSum = maxSubSum3(a, center + 1, right);


        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += a[i];

            if (leftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = leftBorderSum;
            }
        }

        int maxRightBorderSum = 0, rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += a[i];

            if (rightBorderSum > maxRightBorderSum) {
                maxRightBorderSum = rightBorderSum;
            }
        }

        return max3(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);
    }
}
