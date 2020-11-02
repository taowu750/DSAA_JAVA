package algs2_sort.src;


import util.algs.StdOut;
import util.algs.visual.VisualBarChart;
import util.datagen.ArrayData;
import util.datagen.RandomGenerator;
import util.test.SpeedTester;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 排序算法的基类
 */
@SuppressWarnings("unchecked")
public abstract class AbstractSort {

    public abstract void sort(Comparable[] a);


    public String name() {
        return getClass().getSimpleName();
    }

    /**
     * 假设数组从小到大排序。
     *
     * @param a
     * @return
     */
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }

        return true;
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static boolean lessEqual(Comparable v, Comparable w) {
        return v.compareTo(w) <= 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 在单行打印数组。
     *
     * @param a 数组
     */
    public static void show(Comparable[] a) {
        System.out.print("[");
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println("]");
    }

    /**
     * 对算法进行简单的正确性检测。
     *
     * @param sort 排序类
     * @throws FileNotFoundException
     */
    public static void simpleTest(AbstractSort sort, int size, boolean showChart) throws InterruptedException {
        int min = -1000;
        int max = 1000;
        Integer[] a = ArrayData.array(Integer.class, new RandomGenerator.Integer(min, max),
                size);
        VisualBarChart chart = null;
        if (showChart) {
            chart = new VisualBarChart("Sort");
            chart.show(a, min, max);
        }
        System.out.printf("待排序元素：%d, 排序时间：%.3fms\n", size,
                SpeedTester.testRunTime(() -> sort.sort(a)));
        if (showChart) {
            TimeUnit.SECONDS.sleep(3);
            chart.clear();
            chart.show(a, min, max);
        }
        assertTrue(isSorted(a));
    }

    public static void simpleTest(AbstractSort sort) throws InterruptedException {
        simpleTest(sort, 200, true);
    }
}
