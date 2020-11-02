package algs2_sort.sec3_quick_sort;

import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 哨兵。修改原来的快排算法，去掉内循环中的边界检查条件。由于切分元素
 * 本身就是一个哨兵（v 不可能小于 a[lo]），左侧的边界检查是多余的。要去掉
 * 另一个检查，可以在打乱数组后将数组的最大元素放在 a[length-1]中。该元素
 * 永远不会移动（除非和相等的元素交换），可以在所有包含它的子数组中成为哨兵。
 * </p>
 * <p>
 * 注意：在处理内部子数组时，右子数组中的最左侧元素可以作为左子数组右边界
 * 的哨兵。
 * </p>
 */
public class E17_Sentry extends AbstractSort {


    @Override
    public void sort(Comparable[] a) {
        int maxi = 0;
        for (int i = 1; i < a.length; i++) {
            if (less(a[maxi], a[i])) {
                maxi = i;
            }
        }
        exch(a, a.length - 1, maxi);

        sort(a, 0, a.length - 1);
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new E17_Sentry());
    }


    private void sort(Comparable[] a, int lo, int hi) {
        if (lo < hi) {
            int j = partition(a, lo, hi);
            sort(a, lo, j - 1);
            sort(a, j + 1, hi);
        }
    }

    private int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];

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
}
