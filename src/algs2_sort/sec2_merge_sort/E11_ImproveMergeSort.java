package algs2_sort.sec2_merge_sort;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;

import java.util.Arrays;

/**
 * <p>
 * 改进。加快小数组的排序速度；检测数组是否有序；在递归中交换参数避免数组复制。
 * </p>
 * <p>
 * 要避免数组复制，我们需要调用两种排序方法，一种将数据从输入数组排序到辅助数组，
 * 一种将数据从辅助数组排序到输入数组。这种方法需要一些技巧，我们要在递归调用的
 * 每个层次交换输入和输出数组的角色。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class E11_ImproveMergeSort extends AbstractSort {

    private Comparable[] aux;
    private InsertionSort auxSort = new InsertionSort();


    @Override
    public void sort(Comparable[] a) {
        // aux 必须是 a 的副本，否则可能会在比较时抛出空指针异常
        // 又因为 aux 是 a 的副本，所以 aux 和 a 都是一样的
        aux = Arrays.copyOf(a, a.length);
        // 要注意的是，归并次数总是一个奇数（左侧归并+右侧归并+总归并），因此在第
        // 一次调用 Sort 方法时应该把 aux 和 a 互换传入。
        sort(aux, a, 0, a.length - 1);
        aux = null;
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new E11_ImproveMergeSort());
    }


    /*
    把 src 排序到 dest。
    dest 存放归并结果，这个数组中的元素是无关紧要的。src 保存归并前的数组，
    用于实际的归并过程。
    归并结束后，dest 变成归并后的有序结果（也就是下一次归并时的「归并前数组」），
    src 中的内容则不再有用。
    我们可以看到这两个数组的角色在下一次归并时正好可以互换。

    这个和汉诺塔的情况很像。
     */
    private void sort(Comparable[] src, Comparable[] dest, int lo, int hi) {
        if (hi - lo > 15) {
            int mid = (lo + hi) / 2;
            // 交换辅助数组和原数组
            sort(dest, src, lo, mid);
            sort(dest, src, mid + 1, hi);
            // 此时 dest 已经排序到 src 中，dest 中的内容无关紧要
            // 所以把 src 中的内容归并到 dest 中
            if (less(src[mid + 1], src[mid])) {
                merge(src, dest, lo, mid, hi);
            }
        } else if (lo < hi) {
            // 这里应该对输出数组排序，因为输出数组是结果
            auxSort.sort(dest, lo, hi);
        }
    }

    private void merge(Comparable[] src, Comparable[] dest, int lo, int mid, int hi) {
        int li = lo, ri = mid + 1, k = lo;

        while (li <= mid && ri <= hi) {
            if (less(src[li], src[ri]))
                dest[k++] = src[li++];
            else
                dest[k++] = src[ri++];
        }
        while (li <= mid)
            dest[k++] = src[li++];
        while (ri <= hi)
            dest[k++] = src[ri++];
    }
}
