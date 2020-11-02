package dsaa2_algorithm_analysis.practice;

import dsaa2_algorithm_analysis.src.MaximumSubsequence;

/**
 * 关于数组中两个数a[i]、a[j](i <= j)之间的数学运算 - 练习2.28
 *
 * @author wutao
 */
public class TwoNumber {

    /**
     * 求解a[i] + a[j]的最大值 - 2.28a
     *
     * @param a 正整数数组
     * @return a[i] + a[j]的最大值
     */
    public static int maxSum(int[] a) {
        return maxSum(a, 0, a.length - 1);
    }

    /**
     * 求解a[j] - a[i]的最大值 - 2.28b
     *
     * @param a 正整数数组
     * @return a[j] - a[i]的最大值
     */
    public static int maxDiff(int[] a) {
        return maxDiff(a, 0, a.length - 1);
    }


    private static int maxSum(int[] a, int left, int right) {
        if (left == right) {
            return a[left];
        }

        int center      = (left + right) / 2;
        int leftMaxSum  = maxSum(a, left, center);
        int rightMaxSum = maxSum(a, center + 1, right);

        int leftMax = a[left];
        for (int i = left + 1; i <= center; i++) {
            if (a[i] > leftMax) {
                leftMax = a[i];
            }
        }

        int rightMax = a[center + 1];
        for (int i = center + 2; i <= right; i++) {
            if (a[i] > rightMax) {
                rightMax = a[i];
            }
        }

        return MaximumSubsequence.max3(leftMaxSum, rightMaxSum, leftMax + rightMax);
    }

    private static int maxDiff(int[] a, int left, int right) {
        if (left == right) {
            return 0;
        }

        int center       = (left + right) / 2;
        int leftMaxDiff  = maxDiff(a, left, center);
        int rightMaxDiff = maxDiff(a, center + 1, right);

        int leftMin = a[left];
        for (int i = left + 1; i <= center; i++) {
            if (a[i] < leftMin) {
                leftMin = a[i];
            }
        }

        int rightMax = a[center + 1];
        for (int i = center + 2; i <= right; i++) {
            if (a[i] > rightMax) {
                rightMax = a[i];
            }
        }

        return MaximumSubsequence.max3(leftMaxDiff, rightMaxDiff, rightMax - leftMin);
    }
}
