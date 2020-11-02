package algs2_sort.sec1_primary_sort.src;

import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 选择排序。
 * </p>
 * <p>
 * 它有两个特征：
 * （1）运行时间与输入无关。
 * （2）数据移动是最少的，它用了 N 次交换。
 * </p>
 */
public class SelectionSort extends AbstractSort {

    @Override
    public void sort(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int low = i;
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[low])) {
                    low = j;
                }
            }
            if (low != i) {
                exch(a, low, i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new SelectionSort());
    }
}
