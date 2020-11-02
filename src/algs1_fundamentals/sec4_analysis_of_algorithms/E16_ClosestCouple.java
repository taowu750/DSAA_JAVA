package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.io.FormatPrint;

import java.util.Arrays;

/**
 * <p>
 * 最接近的一对（一维）。给定一个含有 N 个 double 值的数组 a[]，在其中找到一对最接近
 * 的值：两者之差（绝对值）最小的两个数。
 * </p>
 * <p>
 * 最坏运行时间为 O(NlgN)。
 * </p>
 */
public class E16_ClosestCouple {

    public int[] closestCouple(double[] a) {
        Arrays.sort(a);
        int[] pos = {-1, -1};
        double diff = Double.MAX_VALUE;

        for (int i = 0; i < a.length - 1; i++) {
            if (Double.compare(Math.abs(a[i] - a[i + 1]), diff) < 0) {
                diff = a[i] - a[i + 1];
                pos[0] = i;
                pos[1] = i + 1;
            }
        }

        return pos;
    }

    @Test
    public void testClosestCouple() throws Exception {
        double[] a = {0.1, 0.3, 0.6, 0.8, 0};
        FormatPrint.group(() -> {
            int[] pos = closestCouple(a);
            System.out.printf("最接近一对：%.1f - %.1f\n", a[pos[0]], a[pos[1]]);
        }, "closestCouple");
    }
}
