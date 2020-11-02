package algs2_sort.sec2_merge_sort;

import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 快速归并。实现一个 merge() 方法，按<strong>升序</strong>将 a[] 的前半部分复制到 aux[] 中，
 * 按<strong>降序</strong>将 a[] 的后半部分复制到 aux[]，
 * 然后将其归并回 a[] 中。这样就可以去掉内循环中检测某半边是否用尽的代码。
 * </p>
 * <p>
 * 注意，这样的排序产生的结果是不稳定的。
 * </p>
 */
public class E10_QuickMerge extends AbstractSort {

    private Comparable[] aux;


    @Override
    public void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
        aux = null;
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new E10_QuickMerge());
    }


    private void sort(Comparable[] a, int lo, int hi) {
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            sort(a, lo, mid);
            sort(a, mid + 1, hi);
            merge(a, lo, mid, hi);
        }
    }

    private void merge(Comparable[] a, int lo, int mid, int hi) {
        for (int k = lo; k <= mid; k++) {
            aux[k] = a[k];
        }
        for (int k = hi, m = mid + 1; k >= mid + 1; k--, m++) {
            aux[m] = a[k];
        }

        int i = lo, j = hi;
        for (int k = lo; k <= hi; k++) {
            if (less(aux[i], aux[j])) {
                a[k] = aux[i++];
            } else {
                a[k] = aux[j--];
            }
        }
    }
}
