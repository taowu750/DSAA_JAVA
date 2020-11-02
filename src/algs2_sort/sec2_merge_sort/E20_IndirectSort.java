package algs2_sort.sec2_merge_sort;

import org.junit.jupiter.api.Test;
import util.datagen.ArrayData;
import util.datagen.RandomGenerator;

import java.util.Arrays;

/**
 * <p>
 * 间接排序。编写一个不改变数组的归并排序，它返回一个 int[] 数组 perm，其中
 * perm[i] 的值是原数组中第 i 小的位置。
 * </p>
 */
public class E20_IndirectSort {

    private int[] indAux;


    /**
     * 思路，让 index 和 indAux 也经历一样的递归过程。
     */
    public int[] index(Comparable[] a) {
        indAux = new int[a.length];
        int[] indexs = new int[a.length];
        for (int i = 0; i < indexs.length; i++) {
            indexs[i] = i;
        }
        index(a, indexs, 0, a.length - 1);
        indAux = null;

        return indexs;
    }

    @Test
    public void test() throws Exception {
        Integer[] a = ArrayData.array(Integer.class, new RandomGenerator.Integer(0, 20),
                6);
        int[] indexs = index(a);
        System.out.println("\n" + Arrays.toString(a));
        System.out.println(Arrays.toString(indexs));
    }


    private void index(Comparable[] a, int[] indexs, int lo, int hi) {
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            index(a, indexs, lo, mid);
            index(a, indexs, mid + 1, hi);
            merge(a, indexs, lo, mid, hi);
        }
    }

    @SuppressWarnings("unchecked")
    private void merge(Comparable[] a, int[] indexs, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            indAux[k] = indexs[k];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                indexs[k] = indAux[j++];
            } else if (j > hi) {
                indexs[k] = indAux[i++];
            } else if (a[indAux[j]].compareTo(a[indAux[i]]) < 0) {
                indexs[k] = indAux[j++];
            } else {
                indexs[k] = indAux[i++];
            }
        }
    }
}
