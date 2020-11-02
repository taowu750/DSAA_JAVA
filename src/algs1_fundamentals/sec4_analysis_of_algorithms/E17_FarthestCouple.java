package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.io.FormatPrint;

/**
 * <p>
 * 最遥远的一对（一维）。给定一个含有 N 个 double 值的数组 a[]，在其中找到一对
 * 最遥远的值：两者之差（绝对值）最大的两个数。
 * </p>
 * <p>
 * 最坏运行时间应该是 O(N)。
 * </p>
 */
public class E17_FarthestCouple {

    public static double[] farthestCouple(double[] a) {
        // values[0] 是最小值，values[1] 是最大值
        double[] values = {Double.MAX_VALUE, Double.MIN_VALUE};

        for (int i = 0; i < a.length; i++) {
            if (Double.compare(a[i], values[0]) < 0) {
                values[0] = a[i];
            }
            if (Double.compare(a[i], values[1]) > 0) {
                values[1] = a[i];
            }
        }

        return values;
    }

    @Test
    public void testFarthestCouple() throws Exception {
        double[] a = {0.1, 1.3, 4.5, -1.0, 0.0, 8.9, -0.5};
        FormatPrint.group(() -> {
            double[] couple = farthestCouple(a);
            System.out.printf("最遥远的一对：%.1f - %.1f\n", couple[0], couple[1]);
        }, "farthestCouple");
    }
}
