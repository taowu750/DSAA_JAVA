package algs2_sort.sec1_primary_sort;

import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 插入排序的哨兵。在插入排序的实现中先找出最小的元素并将其置于数组的最左边，
 * 这样就能去掉内循环的判断条件 j > 0。
 * </p>
 * <p>
 *     这是一种常见的规避边界测试的方法，能够省略判断条件的元素通常被称为哨兵。
 * </p>
 */
public class E24_InsertionSentry {

    public static void main(String[] args) throws InterruptedException {
        InsertionSortWithSentry.simpleTest(new InsertionSortWithSentry());
    }
}


class InsertionSortWithSentry extends AbstractSort {

    @Override
    public void sort(Comparable[] a) {
        int sentry = 0;
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[sentry])) {
                sentry = i;
            }
        }
        exch(a, sentry, 0);

        for (int i = 2; i < a.length; i++) {
            Comparable temp = a[i];
            int j = i;
            for (; less(temp, a[j - 1]); j--)
                a[j] = a[j - 1];
            if (j != i) {
                a[j] = temp;
            }
        }
    }
}
