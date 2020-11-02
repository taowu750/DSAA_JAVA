package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.io.AlgsDataIO;
import util.test.SpeedTester;

import java.util.Arrays;

/**
 * 本节代码。
 */
public class Src_AnalysisOfAlgorithms {

    /**
     * 统计输入的整数序列中所有和为0的三元组的数量
     *
     * @param a
     * @return
     */
    public static int threeSum(int[] a) {
        int N = a.length;
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt++;
                    }
                }
            }
        }

        return cnt;
    }

    @Test
    public void testThreeSum() throws Exception {
        int[] ints = AlgsDataIO.get2Kints();
        Arrays.sort(ints);
        SpeedTester.printRunTime("threeSum: 2000", () ->
                System.out.println(threeSum(ints)));
    }

    /**
     * 统计输入的整数序列中所有和为0的三元组的数量。更快的实现。
     *
     * @param a
     * @return
     */
    public static int threeSumFast(int[] a) {
        int N = a.length;
        int cnt = 0;

        Arrays.sort(a);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (Arrays.binarySearch(a, -(a[i] + a[j])) > j) {
                    cnt++;
                }
            }
        }

        return cnt;
    }
}
