package algs2_sort.sec2_merge_sort;

import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;
import util.datagen.ArrayConverter;
import util.datagen.ArrayData;
import util.datagen.CountingGenerator;
import util.datagen.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 不改变原数组，返回原数组元素排序后的下标idx。idx[i]是原数组中第i小的元素的下标
 */
public class RE20_IndirectSort {

    private static int[] idxAux;
    public static int[] indirectSort(Comparable[] arr) {
        int[] idx = ArrayConverter.to(ArrayData.array(Integer.class,
                new CountingGenerator.Integer(0, 1), arr.length));
        idxAux = new int[arr.length];
        indirectSort(arr, idx, 0, arr.length - 1);

        return idx;
    }

    private static void indirectSort(Comparable[] arr, int[] idx, int lo, int hi) {
        if (lo < hi) {
            int mid  = lo + (hi - lo) / 2;
            indirectSort(arr, idx, lo, mid);
            indirectSort(arr, idx, mid + 1, hi);
            indirectMerge(arr, idx, lo, mid, hi);
        }
    }

    private static void indirectMerge(Comparable[] arr, int[] idx, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            idxAux[k] = idx[k];

        for (int i = lo, j = mid + 1, k = lo; k <= hi; k++) {
            if (i > mid)
                idx[k] = idxAux[j++];
            else if (j > hi)
                idx[k] = idxAux[i++];
            else //noinspection unchecked
                if (arr[idxAux[i]].compareTo(arr[idxAux[j]]) <= 0)
                    idx[k] = idxAux[i++];
                else
                    idx[k] = idxAux[j++];
        }
    }

    @Test
    public void test_indirectSort() {
        Integer[] arr = ArrayData.array(Integer.class,
                new RandomGenerator.Integer(-100, 100), 100);
        int[] idx = indirectSort(arr);
        Integer[] brr = new Integer[arr.length];
        int j = 0;
        for (int i : idx) {
            brr[j++] = arr[i];
        }

        assertTrue(AbstractSort.isSorted(brr));
    }
}
