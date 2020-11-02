package algs2_sort.sec3_quick_sort;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 快速三向切分。用将重复元素放在子数组两端的方式实现一个信息量最优的排序
 * 算法。
 * </p>
 * <p>
 * 使用两个索引 p 和 q，使得 a[lo..p-1] 和 a[q+1..hi] 的元素都和
 * a[lo] 相等。使用另外的两个索引 i 和 j，使得 a[p..i-1] 小于 a[lo]，
 * a[j+i..q] 大于 a[lo]。在内循环中加入循环，在 a[i] 和 v 相当时将其与
 * a[p] 交换（并将 p 加 1），在 a[j] 和 v 相等且 a[i] 和 a[j] 尚未和
 * v 进行比较之前将其与 a[q] 交换。
 * </p>
 * <p>
 * 注意，这里实现的代码和正文给出的代码是等价的，因为这里额外交换用于和切分
 * 元素相等的元素，正文中的代码将额外交换用于和切分元素不等的元素。
 * </p>
 */
@SuppressWarnings({"Duplicates", "unchecked"})
public class E22_Quick3waySplit extends AbstractSort {

    private InsertionSort auxSort = new InsertionSort();


    @Override
    public void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new E22_Quick3waySplit());
    }


    private void sort(Comparable[] a, int lo, int hi) {
        if (hi - lo > 10) {
            int i = lo, j = hi + 1;
            int p = lo, q = hi + 1;

            Comparable v = median3(a, lo, hi);
            while (true) {
                while (less(a[++i], v));
                while (less(v, a[--j]));

                if (i == j && equal(a[i], v))
                    exch(a, ++p, i);
                else if (i < j) {
                    exch(a, i, j);
                    if (equal(a[i], v))
                        exch(a, ++p, i);
                    if (equal(a[j], v))
                        exch(a, --q, j);
                } else
                    break;
            }

            i = j + 1;
            for (int k = lo; k <= p; k++)
                exch(a, k, j--);
            for (int k = hi; k >= q; k--)
                exch(a, k, i++);

            sort(a, lo, j);
            sort(a, i, hi);
        } else if (lo < hi) {
            auxSort.sort(a, lo, hi);
        }
    }

    private Comparable median3(Comparable[] a, int lo, int hi) {
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

    private boolean equal(Comparable i, Comparable j) {
        return i.compareTo(j) == 0;
    }
}
