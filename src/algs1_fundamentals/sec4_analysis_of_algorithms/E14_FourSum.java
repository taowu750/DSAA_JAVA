package algs1_fundamentals.sec4_analysis_of_algorithms;

import java.util.Arrays;

/**
 * 为 4-sum 设计一个算法。
 */
public class E14_FourSum {

    public static int fourSum(int[] a) {
        int count = 0;

        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                for (int k = j + 1; k < a.length; k++) {
                    if (Arrays.binarySearch(a, -a[i] - a[j] - a[k]) > k) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
