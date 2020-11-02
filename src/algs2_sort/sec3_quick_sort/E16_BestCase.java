package algs2_sort.sec3_quick_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static algs2_sort.src.AbstractSort.exch;

/**
 * <p>
 * 编写一段程序生成对快排表现最佳的数组：数组大小为 N 且没有重复元素，每次切分
 * 后两个子数组的大小最多差 1。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class E16_BestCase {

    /*
    对于取头一个元素切分的快排来说，最好的数组就是无重复元素，且数组和子数组的
    中位数都在开头。
    所以我们先构造一个有序数组，然后找到中点，对中点左右两侧数组进行改造，
    最后将中点和左边元素进行交换。
     */
    public static Integer[] bestCase(int n) {
        Integer[] cases = new Integer[n];
        for (int i = 0; i < n; i++) {
            cases[i] = i;
        }

        return bestCase(cases, 0, n - 1);
    }

    @Test
    public void test() throws Exception {
        System.out.println(Arrays.toString(bestCase(10)));
    }


    private static Integer[] bestCase(Integer[] a, int lo, int hi) {
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            a = bestCase(a, lo, mid - 1);
            a = bestCase(a, mid + 1, hi);
            exch(a, lo, mid);
        }

        return a;
    }
}
