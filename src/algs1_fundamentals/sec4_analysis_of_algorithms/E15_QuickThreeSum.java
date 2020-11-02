package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.io.AlgsDataIO;
import util.test.SpeedTester;

import java.util.Arrays;

/**
 * <p>
 * 快速 3-sum 算法。作为热身，先使用一个线性级别（而非线性对数级别）的算法实现 TwoSum，
 * 在使用同样的思路为 3-sum 问题给出一个平方级别的算法。
 * </p>
 * <p>
 * 假定数组已经排序，且不能使用二分查找算法。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class E15_QuickThreeSum {

    public static int quickTwoSum(int[] a) {
        int count = 0;
        int low = 0, high = a.length - 1;

        while (low < high) {
            int r = a[low] + a[high];
            if (r < 0) {
                low++;
            } else if (r > 0) {
                high--;
            } else {
                count++;
                low++;
                high--;
            }
        }

        return count;
    }

    @Test
    public void testQuickTwoSum() throws Exception {
        int[] ints = AlgsDataIO.get1Mints();
        Arrays.sort(ints);
        SpeedTester.printRunTime("quickTwoSum: 1000000", () ->
                System.out.println(quickTwoSum(ints)));
    }

    public static int quickThreeSum(int[] a) {
        int count = 0;
        int low, high;

        for (int i = 0; i < a.length - 3; i++) {
            low = i + 1;
            high = a.length - 1;

            while (low < high) {
                int r = a[i] + a[low] + a[high];
                if (r < 0) {
                    low++;
                } else if (r > 0) {
                    high--;
                } else {
                    count++;
                    low++;
                    high--;
                }
            }
        }

        return count;
    }

    @Test
    public void testQuickThreeSum() throws Exception {
        int[] ints = AlgsDataIO.get2Kints();
        Arrays.sort(ints);
        SpeedTester.printRunTime("quickThreeSum: 2000", () ->
                System.out.println(quickThreeSum(ints)));
    }
}
