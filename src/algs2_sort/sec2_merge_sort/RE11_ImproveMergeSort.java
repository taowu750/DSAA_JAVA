package algs2_sort.sec2_merge_sort;

import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

// TODO: 不行
/**
 * 在归并中交换数组而不是复制
 */
public class RE11_ImproveMergeSort {

    @Test
    public void test_NoArrayCopyMergeSort() throws InterruptedException {
        AbstractSort.simpleTest(new NoArrayCopyMergeSort(), 200, false);
    }
}

class NoArrayCopyMergeSort extends AbstractSort {

    Comparable[] myAux;

    @Override
    public void sort(Comparable[] a) {
        myAux = Arrays.copyOf(a, a.length);
        sort(myAux, a, 0, a.length - 1, 0);
    }

    void sort(Comparable[] a, Comparable[] aux, int lo, int hi, int deep) {
        if (lo < hi) {
            if (hi - lo == 1) {
                if (less(a[hi], a[lo]))
                    exch(a, hi, lo);
            } else {
                int mid = (hi - lo) / 2 + lo;
                sort(a, aux, lo, mid, deep + 1);
                sort(a, aux, mid + 1, hi, deep + 1);
                // 当左半部分的最大值小于右半部分的最小值时，就已经有序了
                if (lessEqual(a[mid], a[mid + 1])) {
                    return;
                }
                if (deep % 2 == 0)
                    merge(aux, a, lo, mid, hi);
                else
                    merge(a, aux, lo, mid, hi);
            }
        }
    }

    void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (lessEqual(a[i], a[j])) {
                aux[k++] = a[i++];
            } else {
                aux[k++] = a[j++];
            }
        }
        while (i <= mid)
            aux[k++] = a[i++];
        while (j <= hi)
            aux[k++] = a[j++];
    }
}
