package algs2_sort.sec5_application.src;

import static algs2_sort.src.AbstractSort.exch;
import static algs2_sort.src.AbstractSort.less;

/**
 * 选取 k 小值。
 */
public class SelectK {

    public static Comparable select(Comparable[] a, int k) {
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int j = partition(a, lo, hi);
            if (j == k)
                return a[k];
            else if (j > k)
                hi = j - 1;
            else
                lo = j + 1;
        }

        return a[k];
    }


    @SuppressWarnings("Duplicates")
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = median3(a, lo, hi);

        while (true) {
            while (less(a[++i], v));
            while (less(v, a[--j]));

            if (i >= j)
                break;
            exch(a, i, j);
        }
        exch(a, lo, j);

        return j;
    }

    @SuppressWarnings("Duplicates")
    private static Comparable median3(Comparable[] a, int lo, int hi) {
        int center = (lo + hi) / 2;

        if (a[lo].compareTo(a[center]) > 0)
            exch(a, lo, center);
        if (a[lo].compareTo(a[hi]) > 0)
            exch(a, lo, hi);
        if (a[center].compareTo(a[hi]) > 0)
            exch(a, center, hi);

        exch(a, lo, center);

        return a[lo];
    }
}
