package algs2_sort.sec2_merge_sort;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 次线性的额外空间。用大小 M 将数组分成 N/M 块（简单起见，设 M 是 N 的约数）。实现
 * 一个归并方法，使之所需要的额外空间减少到 max(M, N/M)。
 * </p>
 * <p>
 * (1)可以先将一个块看作一个元素，将块的第一个元素作为块的主键，用选择排序将块排序。
 * (2)遍历数组，将第一块和第二块合并，完成后将第二块和第三块合并，等等。
 * </p>
 * <p>
 * 注意：
 * （1）额外空间最大为 max(M, N/M)
 * </p>
 */
@SuppressWarnings("Duplicates")
public class E12_SublinearAdditionalSpace extends AbstractSort {

    private InsertionSort auxSort = new InsertionSort();


    @Override
    public void sort(Comparable[] a) {
        sort(a, 10);
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new E12_SublinearAdditionalSpace());
    }


    // 每块大小 M，有 N / M 块
    public void sort(Comparable[] a, int M) {
        int blocks = a.length / M;

        // 先对每块进行排序
        for (int i = 0; i < blocks; i++) {
            auxSort.sort(a, i * M, (i + 1) * M - 1);
        }

        Comparable[] aux = new Comparable[M];
        // 在根据每块的最小值对块进行排序，块的下标从 0 开始，也就是第0块、第1块...
        for (int block = 0; block < blocks - 1; block++) {
            int min = block;
            for (int i = block + 1; i < blocks; i++) {
                if (less(a[i * M], a[min * M])) {
                    min = i;
                }
            }
            // FIXME: 为了在最后一个块不满 M 的情况下也能正常排序，这里还需要改进
            if (min != block) {
                System.arraycopy(a, block * M, aux, 0, M);
                System.arraycopy(a, min * M, a, block * M, M);
                System.arraycopy(aux, 0, a, min * M, M);
            }
        }

        for (int block = 0; block < blocks - 1; block++) {
            merge(a, 0, (block + 1) * M - 1,
                    // 这样就算最后一个块大小不满 M，也能够正常排序
                    Math.min((block + 2) * M - 1, a.length - 1));
        }
    }


    private void merge(Comparable[] a, int lo, int mid, int hi) {
        Comparable[] aux = new Comparable[hi - lo + 1];
        int li = lo, ri = mid + 1, k = lo;

        while (li <= mid && ri <= hi) {
            if (less(a[li], a[ri]))
                aux[k++] = a[li++];
            else
                aux[k++] = a[ri++];
        }
        while (li <= mid)
            aux[k++] = a[li++];
        while (ri <= hi)
            aux[k++] = a[ri++];

        for (k = lo; k < aux.length; k++)
            a[k] = aux[k];
    }
}
