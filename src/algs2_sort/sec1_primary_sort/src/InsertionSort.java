package algs2_sort.sec1_primary_sort.src;

import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 插入排序
 * </p>
 * <p>
 * 插入排序的排序时间取决于输入元素的顺序。输入数组越有序排序越快。<br/>
 * <strong>倒置</strong>指的是数组中两个顺序颠倒的元素，如果数组中的倒置
 * 数量小于数组大小的某个倍数，我们就称数组是部分有序的。<br/>
 * 插入数组对倒置很少的排序速度比其他任何算法都要快。
 * </p>
 */
public class InsertionSort extends AbstractSort {

    @Override
    public void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    public void sort(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            // 注意保存插入值
            Comparable temp = a[i];
            int j = i;
            // 注意要将插入值与被插入序列值比较，且数组不能越界
            for (; j > lo && less(temp, a[j - 1]); j--) {
                a[j] = a[j - 1];
            }
            if (j != i) {
                a[j] = temp;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new InsertionSort());
    }
}
