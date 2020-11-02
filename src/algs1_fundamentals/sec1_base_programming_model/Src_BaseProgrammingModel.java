package algs1_fundamentals.sec1_base_programming_model;

import org.junit.jupiter.api.Test;
import util.algs.StdDraw;
import util.datagen.ArrayConverter;
import util.datagen.ArrayData;
import util.datagen.CountingGenerator;
import util.datagen.RandomGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 1.1 节基础编程模型中的代码。
 */
@SuppressWarnings("Duplicates")
public class Src_BaseProgrammingModel {

    /**
     * 二分查找，要求参数数组 a 已排好序。
     *
     * @param a
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> int binarySearch(T[] a, T key) {
        int low = 0, high = a.length - 1;
        while (low <= high) {
            int center = (low + high) / 2;
            int compareResult = key.compareTo(a[center]);
            if (compareResult < 0) {
                high = center - 1;
            } else if (compareResult > 0) {
                low = center + 1;
            } else {
                return center;
            }
        }
        System.out.println("Src_BaseProgrammingModel.binarySearch: low=" + low + ", high=" + high);

        return -1;
    }

    @Test
    public void testBinarySearch() throws Exception {
        Integer[] a = ArrayData.array(Integer.class, new CountingGenerator.Integer(), 100);
        assertEquals(binarySearch(a, 50), 50);
    }

    /**
     * 牛顿迭代法求平方根。
     *
     * @param c
     * @return
     */
    public static double sqrt(double c) {
        if (c < 0) {
            return Double.NaN;
        }

        double err = 1e-15;
        double t = c;
        while (Math.abs(t - c / t) > err * t) {
            t = (c / t + t) / 2.0;
        }

        return t;
    }

    @Test
    public void testSqrt() throws Exception {
        assertEquals(sqrt(4), 2.);
    }

    public static void testStdDrawFunc() {
        int N = 100;
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N * N);
        StdDraw.setPenRadius(.01);

        for (int i = 0; i < N; i++) {
            StdDraw.point(i, i);
            StdDraw.point(i, i * i);
            StdDraw.point(i, i * Math.log(i));
        }
    }

    public static void testStdDrawArray() {
        int N = 50;
        double[] a = ArrayConverter.to(ArrayData.array(Double.class,
                new RandomGenerator.Double(0., 1.), N));
        for (int i = 0; i < N; i++) {
            double x = 1. * i / N;
            double y = a[i] / 2.;
            double rw = .5 / N;
            double rh = a[i] / 2.;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }

    public static void testStdDrawSortedArray() {
        int N = 50;
        double[] a = ArrayConverter.to(ArrayData.array(Double.class,
                new RandomGenerator.Double(0., 1.), N));
        Arrays.sort(a);
        for (int i = 0; i < N; i++) {
            double x = 1. * i / N;
            double y = a[i] / 2.;
            double rw = .5 / N;
            double rh = a[i] / 2.;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }

    public static void main(String[] args) {
//        testStdDrawFunc();
//        testStdDrawArray();
        testStdDrawSortedArray();
    }
}
