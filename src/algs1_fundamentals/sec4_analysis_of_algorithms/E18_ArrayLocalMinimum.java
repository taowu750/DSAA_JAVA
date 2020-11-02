package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.io.FormatPrint;

/**
 * <p>
 * 数组的局部最小元素。给定一个含 N 个不同整数的数组，找到一个局部最小元素：满足
 * a[i] < a[i - 1]，且 a[i] < a[i + 1] 的索引 i。
 * </p>
 * <p>
 * 最坏比较次数为 2lgN。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class E18_ArrayLocalMinimum {

    private static int cnt = 0;


    public static int localMinimum(int[] a) {
        if (a.length >= 3) {
            return localMinimum(a, 0, a.length - 1);
        }

        return -1;
    }

    @Test
    public void testLocalMinimum() throws Exception {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 6, 9};
        FormatPrint.group(() -> {
                    System.out.println("局部最小值：" + localMinimum(a));
                    System.out.println("函数运行次数：" + cnt);
                },
                "localMinimum");
    }


    private static int localMinimum(int[] a, int low, int high) {
        cnt++;
        if (high - low <= 1) {
            return Math.min(a[low], a[high]);
        }
        int mid = (low + high) / 2;
        if (a[mid] > a[mid - 1])
            return localMinimum(a, low, mid - 1);
        if (a[mid] > a[mid + 1])
            return localMinimum(a, mid + 1, high);

        return a[mid];
    }
}
