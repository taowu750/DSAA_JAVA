package algs2_sort.sec3_quick_sort;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

// TODO: 不行
/**
 * 快速三向切分
 */
public class RE17_Quick3waySplit {

    @Test
    public void test_FastQuick3waySort() throws Exception {
        AbstractSort.simpleTest(new FastQuick3waySort(), 200, false);
    }
}

class FastQuick3waySort extends AbstractSort {

    private InsertionSort auxSort = new InsertionSort();

    public void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if (hi - lo > 10) {
            int[] pos = partition(a, lo, hi);
            sort(a, lo, pos[0] - 1);
            sort(a, pos[1] + 1, hi);
        } else {
            auxSort.sort(a, lo, hi);
        }
    }

    private int[] partition(Comparable[] a, int lo, int hi) {
        middle(a, lo, hi);
        Comparable v = a[lo];
        int p = lo + 1, q = hi, i = lo, j = hi + 1;

        while (true) {
            int c = a[++i].compareTo(v);
            while (c <= 0) {
                if (c == 0) {
                    exch(a, i, p++);
                }
                if (i == hi)
                    break;
                c = a[++i].compareTo(v);
            }
            c = v.compareTo(a[--j]);
            while (c <= 0) {
                if (c == 0) {
                    exch(a, j, q--);
                }
                if (j == lo)
                    break;
                c = v.compareTo(a[--j]);
            }

            if (i < j) {
                exch(a, i, j);
            }
            else
                break;
        }

        if (p > lo && p < j) {
            System.arraycopy(a, p, a, lo, j - p + 1);
            Arrays.fill(a, lo + j - p + 1, j + 1, v);
        }
        if (q < hi && q > i) {
            System.arraycopy(a, i, a, hi - q + i, q - i + 1);
            Arrays.fill(a, i, hi - q + i, v);
        }

        return new int[]{lo + j - p, hi - q + i};
    }

    protected void middle(Comparable[] a, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        if (less(a[mid], a[lo]))
            exch(a, lo, mid);
        if (less(a[hi], a[mid]))
            exch(a, hi, mid);
        if (less(a[mid], a[lo]))
            exch(a, lo, mid);
        exch(a, lo, mid);
    }
}
