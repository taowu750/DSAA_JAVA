package algs2_sort.sec3_quick_sort;

import org.junit.jupiter.api.Test;
import util.algs.StdRandom;

import java.util.Arrays;

/**
 * <p>
 * 螺丝和螺帽。假设有 N 个螺丝和 N 个螺帽混在一堆，你需要快速将它们配对。一个
 * 螺丝只会匹配一个螺帽，一个螺帽也只会匹配一个螺丝。你可以试着把一个螺丝和一个
 * 螺帽拧在一起看看谁大了，但不能直接比较两个螺丝或者两个螺帽。给出一个解决此问题
 * 的有效方法。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class E15_ScrewsAndNuts {

    @Test
    public void testMatch() throws Exception {
        int[][] sns = generateScrewsAndNuts(10);
        System.out.println(Arrays.toString(sns[0]));
        System.out.println(Arrays.toString(sns[1]));

        sort(sns[0], sns[1], 0, sns[0].length - 1);
        System.out.println("\n" + Arrays.toString(sns[0]));
        System.out.println(Arrays.toString(sns[1]));
    }

    /*
    只能够比较螺丝和螺帽谁大。。
    螺丝和螺帽中没有重复的，且必能够一一匹配。
     */
    private void sort(int[] screws, int[] nuts, int lo, int hi) {
        if (lo < hi) {
            int p = partition(screws, nuts, lo, hi);
            sort(screws, nuts, lo, p - 1);
            sort(screws, nuts, p + 1, hi);
        }
    }

    private int partition(int[] screws, int[] nuts, int lo, int hi) {
        int i = lo, j = hi + 1;
        int screw = screws[lo];
        // 找到对应的螺帽
        for (int k = lo; k <= hi; k++) {
            if (nuts[k] == screw) {
                exch(nuts, lo, k);
                break;
            }
        }

        // 根据螺丝划分螺母
        while (true) {
            while (nuts[++i] < screw && i != hi);
            while (nuts[--j] > screw && j != lo);

            if (i >= j)
                break;
            exch(nuts, i, j);
        }
        exch(nuts, j, lo);

        // 再根据螺母划分螺丝
        int nut = nuts[j];
        i = lo;
        j = hi + 1;
        while (true) {
            while (screws[++i] < nut && i != hi);
            while (screws[--j] > nut && j != lo);

            if (i >= j)
                break;
            exch(screws, i, j);
        }
        exch(screws, j, lo);

        return j;
    }

    private void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private int[][] generateScrewsAndNuts(int N) {
        int[] screws = new int[N];
        for (int i = 0; i < screws.length; i++) {
            screws[i] = i;
        }
        StdRandom.shuffle(screws);

        int[] nuts = new int[N];
        for (int i = 0; i < nuts.length; i++) {
            nuts[i] = i;
        }
        StdRandom.shuffle(nuts);

        return new int[][]{screws, nuts};
    }
}
