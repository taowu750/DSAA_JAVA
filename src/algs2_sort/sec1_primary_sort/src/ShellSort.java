package algs2_sort.sec1_primary_sort.src;

import algs2_sort.src.AbstractSort;

/**
 * <p>
 * 希尔排序。
 * </p>
 * <p>
 * 希尔排序是对插入排序的改进。<br/>
 * 对于大规模乱序数组插入排序很乱，因为它只会交换相邻元素，元素只能一点点从数组的一端
 * 到另一端。<br/>
 * 希尔排序交换不相邻的元素对数组局部进行排序，并最终使用插入将局部有序的数组排序。
 * </p>
 * <p>
 * 希尔排序的思想是使数组中任意间隔为 h 的元素都是有序的。这样的数组被称为 h 有序
 * 数组。换句话说，一个 h 有序数组就是 h 个相互独立的有序数组编织在一起组成的数组。
 * 如果 h 很大，在排序时我们就可以把元素移动到很远的地方，为实现更小的 h 有序创造方便。
 * 用这种方式，对于任意以 1 结尾的 h 序列，我们都能够将数组排序。
 * </p>
 * <p>
 * 希尔排序高效的原因在于它权衡了数组的规模和有效性。排序起初，各个子数组都很短，
 * 排序之后的子数组都是有序的，这两种情况的都很适合插入排序。子数组的部分有序的
 * 程度取决于递增序列的选择。
 * </p>
 */
public class ShellSort extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        int h = 1;
        // 使用序列 (3**k - 1) / 2
        while (h < a.length / 3)
            h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < a.length; i += h) {
                Comparable temp = a[i];
                int j = i;
                for (; j > 0 && less(temp, a[j - h]); j -= h) {
                    a[j] = a[j - h];
                }
                if (j != i) {
                    a[j] = temp;
                }
            }
            h /= 3;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new ShellSort());
    }
}
