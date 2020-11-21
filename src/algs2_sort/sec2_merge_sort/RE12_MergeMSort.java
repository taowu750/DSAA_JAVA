package algs2_sort.sec2_merge_sort;

import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 用大小M将数组分成N/M块，简单起见M是N的约数。实现一个归并方法，使额外空间减少到max(M, N/M)
 */
public class RE12_MergeMSort {

    @Test
    public void test_MergeMSort() throws InterruptedException {
        AbstractSort.simpleTest(new MergeMSort(), 200, false);
    }
}

class MergeMSort extends AbstractSort {

    Comparable[] aux;
    int M = 10;

    @Override
    public void sort(Comparable[] a) {
        aux = new Comparable[M];

        int N = a.length;
        // 分别对每一块进行排序
        for (int i = 0; i < N / M; i++) {
            Arrays.sort(a, i * M, (i + 1) * M);
        }

        // 将每块的最小值当作键值，按照键值对块进行选择排序
        for (int i = 0; i < N / M - 1; i++) {
            int min_chunk_ind = i;
            for (int j = i + 1; j < N / M; j++) {
                if (less(a[j * M], a[min_chunk_ind * M]))
                    min_chunk_ind = j;
            }
            if (min_chunk_ind != i) {
                System.arraycopy(a, min_chunk_ind * M, aux, 0, M);
                System.arraycopy(a, i * M, a, min_chunk_ind * M, M);
                System.arraycopy(aux, 0, a, i * M, M);
            }
        }

        // 合并第一块和第二块，然后是合并后的块和第三块，这样一直到最后一块
        for (int n = 0; n < N / M - 1; n++) {
            merge(a, 0, (n + 1) * M - 1, (n + 2) * M - 1);
        }
    }

    private void merge(Comparable[] a, int lo, int mid, int hi) {
        // 将右边的块复制到aux中
        for (int k = mid + 1; k <= hi; k++)
            aux[k - mid - 1] = a[k];

        int i = mid, j = hi - mid - 1;
        for (int k = hi; k >= lo; k--) {
            if (i < lo) {
                a[k] = aux[j--];
            } else if (j < 0) {
                a[k] = a[i--];
            } else if (less(aux[j], a[i])) {
                a[k] = a[i--];
            } else {
                a[k] = aux[j--];
            }
        }
    }
}
