package dsaa2_algorithm_analysis.practice;

import java.util.Arrays;

/**
 * 求解与子序列相关的问题 - 练习2.17
 *
 * @author wutao
 */
public class Subsequence {

    /**
     * 求解最小子序列和 - 2.17a
     * 时间复杂度：O(NlogN)
     *
     * @param a 整数数组，包含有正数和负数
     * @return 最小子序列和
     */
    public static int minSubSum(int[] a) {
        return minSubSum(a, 0, a.length - 1);
    }

    /**
     * 求解最小正子序列和，其中最小正子序列和指的是结果为最小正整数 - 2.17b
     * 时间复杂度：O(N2)
     *
     * @param a 整数数组，包含有正数和负数
     * @return 最小正子序列和
     */
    public static int minPositiveSubSum1(int[] a) {
        int minPositiveSum = Integer.MAX_VALUE;

        for (int i = 0; i < a.length; i++) {
            int thisSum = 0;
            for (int j = i; j < a.length; j++) {
                thisSum += a[j];

                if (thisSum > 0 && minPositiveSum > thisSum) {
                    minPositiveSum = thisSum;
                }
            }
        }

        return minPositiveSum;
    }

    /**
     * 先求一下从第一位开始的到第i位的累加，最前面加上0（一个都不选的情况）<br/>
     * 4，-1，5，-2，-1，2，6，-2 => 0 4 3 8 6 5 7 13 11
     * <p>
     * 对这个累加的数列排个序，0 3 4 5 6 7 8 11 13，并记录对应的下标0 2 1 5 4 6 3 8 7，然后只要判断邻近的两个数是否可以组成序列，<br/>
     * 比如4和3就不可以，因为4 > 3而4对应下标为1,3对应为2。4和5就可以，4对应下标1,5对应下标5，这样的话，从第1个数到第4个数，就组成了一个和为1的序列。
     * <p>
     * 解释一下为什么只需检查相邻2个数就可以，设ABC是排序后的结果，如果A同B不能组成序列，而A同C可以组成序列，那么B同C也可以组成序列，并且BC会是一个更优的解。
     * <p>
     * 排序的时候处理一下，数字相等的话可以合并成1个元素，只记录索引的最大或最小值就可以。
     * <p>
     * 2.17b
     * 时间复杂度：O(NlogN)
     *
     * @param a 整数数组，包含有正数和负数
     * @return 最小正子序列和
     */
    public static int minPositiveSubSum2(int[] a) {
        int minPositiveSum = Integer.MAX_VALUE;

        SubSumAndIndex[] sis = new SubSumAndIndex[a.length];
        for (int i = 0; i < sis.length; i++) {
            sis[i] = new SubSumAndIndex();
        }

        int thisSum = 0;
        for (int i = 0; i < a.length; i++) {
            thisSum += a[i];
            sis[i].index = i;
            sis[i].subSum = thisSum;
        }
        Arrays.sort(sis); //O(NlogN)

        for (int i = 1; i < sis.length; i++) {
            if (sis[i].subSum <= 0) {
                continue;
            }
            if (sis[i].index > sis[i - 1].index) {
                thisSum = Math.abs(sis[i].subSum) - Math.abs(sis[i - 1].subSum);
                if (thisSum > 0 && minPositiveSum > thisSum) {
                    minPositiveSum = thisSum;
                }
            }
        }

        return minPositiveSum == Integer.MAX_VALUE ? 0 : minPositiveSum;
    }

    /**
     * 动态规划求解最大子序列乘积 - 2.17c
     * 时间复杂度：O(N)
     * <p>
     * 思路:
     * 以元素i结尾序列提供的最大正数记做 pos, 最小负数记做 neg<br/>
     * （注：n表示当前的意思）<br/>
     * a[n] 大于零时:<br/>
     *      pos[n] = max{pos[n-1] * a[n], a[n]}<br/>
     *      max_value = max{max_value, pos[n]}<br/>
     *      若n-1位置存在最小负数, 更新 neg[n] = neg[n-1] * a[n]<br/>
     * a[n] 小于零时:<br/>
     *      pos[n] = max{neg[n-1] * a[n], 0.0}<br/>
     *      max_value = max{max_value, pos[n]}<br/>
     *      更新 neg[n] = min{pos[n-1] * a[n], a[n]}<br/>
     * a[n] 等于零时:<br/>
     *      清空 neg[n] 与 pos[n]<br/>
     *
     * @param a 浮点数数组，包含有浮点正数和负数
     * @return 最大子序列乘积
     */
    public static float maxSubProduct(float[] a) {
        float maxSubProduct = 0;

        float maxPos = 0.0F, minNeg = 1.0F, old = 0.0F;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > 1e-6F) {
                maxPos = max2(old * a[i], a[i]);
                maxSubProduct = max2(maxSubProduct, maxPos);
                if (minNeg < 1e-6F) {
                    minNeg *= a[i];
                }
            } else if (a[i] < 1e-6F) {
                maxPos = max2(1e-6F, a[i] * minNeg);
                maxSubProduct = max2(maxSubProduct, maxPos);
                minNeg = old * a[i] < a[i] ? old * a[i] : a[i];
            } else {
                maxPos = 0.0F;
                minNeg = 1.0F;
            }
            old = maxPos;
        }

        return maxSubProduct;
    }


    private static class SubSumAndIndex implements Comparable<SubSumAndIndex> {
        int subSum;
        int index;

        @Override
        public int compareTo(SubSumAndIndex o) {
            if (this.subSum == o.subSum) {
                this.index = o.index = this.index < o.index ? this.index : o.index;
                return 0;
            }

            return this.subSum < o.subSum ? -1 : 1;
        }
    }


    private static int minSubSum(int[] a, int left, int right) {
        if (left == right) {
            if (a[left] < 0) {
                return a[left];
            } else {
                return 0;
            }
        }

        int center      = (left + right) / 2;
        int minLeftSum  = minSubSum(a, left, center);
        int minRightSum = minSubSum(a, center + 1, right);

        int minLeftBorderSum = 0, leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += a[i];

            if (leftBorderSum < minLeftBorderSum) {
                minLeftBorderSum = leftBorderSum;
            }
        }

        int minRightBorderSum = 0, rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += a[i];

            if (rightBorderSum < minRightBorderSum) {
                minRightBorderSum = rightBorderSum;
            }
        }

        return min3(minLeftSum, minRightSum, minLeftBorderSum + minRightBorderSum);
    }

    private static int min3(int a, int b, int c) {
        int min;

        min = (a < b ? a : b);
        min = (min < c ? min : c);

        return min;
    }


    private static float max2(float a, float b) {
        return (a > b ? a : b);
    }
}
