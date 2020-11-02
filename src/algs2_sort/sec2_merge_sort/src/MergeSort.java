package algs2_sort.sec2_merge_sort.src;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;
import util.base.ExMath;
import util.datagen.ArrayData;
import util.datagen.RandomGenerator;
import util.datastructure.MyStack;
import util.test.SpeedTester;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * <p>
 * 归并排序。
 * </p>
 * <p>
 * 归并排序有两个特点：
 * （1）保证任意长度为 N 的数组排序所需时间与 NlogN 成正比。
 * （2）它所需的额外空间和 N 成正比。
 * </p>
 * <p>
 * 归并排序有两种实现方式：
 * （1）自顶向下的排序方式：这种方式一般使用递归实现。它不断的将大数组分成两半小数组，
 * 分到底时再向上归并。
 * （2）自底向上的排序方式：这种方式使用循环实现。它从底层的微小数组（长度 1）开始，
 * 不断向上归并。这种方式比较适合用链表组织的数据。想象一下将链表先按大小为 1 的子链表
 * 进行排序，然后是大小为 2 的子链表。这种方式只需要重新组织链表链接就能将链表原地排序。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class MergeSort extends AbstractSort {

    private static final int CUTOFF = 15;

    private static InsertionSort auxSort = new InsertionSort();

    private Comparable[] aux;


    @Override
    public void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sortTopDown(a, 0, a.length - 1);
//        sortDownTop(a);
        aux = null;
    }

    public void loopSort(Comparable[] a) {
        aux = new Comparable[a.length];
        // 通用循环的方法在小数据集上比递归的方法要慢，但在大数据集上比递归要快
        loopSortTopDown(a, 0, a.length - 1);
        aux = null;
    }

    @Test
    public void testSpeed() throws Exception {
        // 在10万量级上，通用循环性能开始反超递归
        int N = 1000000;
        int min = -(N * 2 / 3);
        int max = N * 2 / 3;
        Integer[] a = ArrayData.array(Integer.class, new RandomGenerator.Integer(min, max),
                N);
        Integer[] acy = new Integer[a.length];
        for (int i = 0; i < acy.length; i++) {
            acy[i] = (int) a[i];
        }
        MergeSort sort = new MergeSort();

        System.out.printf("递归归并排序在 %d 个整数下的性能：%.3fms\n", N, SpeedTester.testRunTime(
                () -> sort.sort(a)));
        assertTrue(AbstractSort.isSorted(a));
        System.out.printf("循环归并排序在 %d 个整数下的性能：%.3fms\n", N, SpeedTester.testRunTime(
                () -> sort.loopSort(acy)));
        assertTrue(AbstractSort.isSorted(acy));
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new MergeSort());
    }


    // 自顶向下的归并排序
    private void sortTopDown(Comparable[] a, int lo, int hi) {
        if (hi - lo > CUTOFF) {
            int mid = (lo + hi) / 2;
            sortTopDown(a, lo, mid);
            sortTopDown(a, mid + 1, hi);
            // 当左半部分的最大值小于右半部分的最小值时，就已经有序了
            if (lessEqual(a[mid], a[mid + 1])) {
                return;
            }
            merge(a, lo, mid, hi);
        } else if (lo < hi) {
            auxSort.sort(a, lo, hi);
        }
    }

    // 自底向上的归并排序
    private void sortDownTop(Comparable[] a) {
        // 将 a.length 缓存在 N 中，可以稍微提升性能
        // 自底向上性能不如自顶向下
        int N = a.length;
        for (int sz = 1; sz < N; sz += sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1,
                        N - 1));
    }

    private static class Record {
        int lo;
        int hi;
        int state;


        public Record(int lo, int hi, int state) {
            this.lo = lo;
            this.hi = hi;
            this.state = state;
        }
    }

    private void loopSortTopDown(Comparable[] a, int lo, int hi) {
        MyStack<Record> s = new MyStack<>(
                (int) ExMath.ln(hi - lo + 1) + 1);
        Record cur = new Record(lo, hi, 0);

        while (true) {
            if (cur.lo >= cur.hi) {
                if (s.isEmpty())
                    break;
                cur = s.pop();
            } else if (cur.hi - cur.lo <= CUTOFF) {
                // 在小数组上使用插入排序改善了一些性能
                auxSort.sort(a, cur.lo, cur.hi);
                if (s.isEmpty())
                    break;
                cur = s.pop();
            } else if (cur.state == 0) {
                s.push(new Record(cur.lo, cur.hi, 1));

                cur.hi = (cur.lo + cur.hi) / 2;
            } else if (cur.state == 1) {
                s.push(new Record(cur.lo, cur.hi, 2));

                cur.lo = (cur.lo + cur.hi) / 2 + 1;
                cur.state = 0;
            } else if (cur.state == 2) {
                int mid = (cur.lo + cur.hi) / 2;
                // 当左半部分的最大值小于右半部分的最小值时，就已经有序了
                if (less(a[mid + 1], a[mid])) {
                    merge(a, cur.lo, (cur.lo + cur.hi) / 2, cur.hi);
                }
                if (s.isEmpty()) {
                    break;
                }
                cur = s.pop();
            }
        }

        /*
        // 错误的代码
        while (true) {
            if (cur.lo >= cur.hi) {
                if (s.isEmpty())
                    break;
                cur = s.poll();
            } else if (cur.state == 0) {
                s.push(new Record(cur.lo, cur.mid, cur.hi, 1));

                cur.hi = cur.mid;
                cur.mid = (cur.lo + cur.hi) / 2;
            } else if (cur.state == 1) {
                s.push(new Record(cur.lo, cur.mid, cur.hi, 2));

                cur.lo = cur.mid + 1;
                cur.state = 0;
            } else if (cur.state == 2) {
                merge(a, cur.lo, cur.mid, cur.hi);
                if (s.isEmpty()) {
                    break;
                }
                cur = s.poll();
            }
        }*/
    }

    // 将 a[lo..mid] 和 a[mid+1..hi] 这两个有序数列归并
    private void merge(Comparable[] a, int lo, int mid, int hi) {
       int li = lo, ri = mid + 1, k = lo;

        // 将两个有序数列归并到 aux 中
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

        // 再将 aux 中的内容复制到 a 中
        for (k = lo; k <= hi; k++)
            a[k] = aux[k];


        /*// 另一种归并方法
        int i = lo, j = mid + 1;
        for (int m = lo; m <= hi; m++)
            aux[k] = a[k];
        for (int m = lo; m <= hi; m++) {
            if (i > mid)
                a[m] = aux[j++];
            else if (j > hi)
                a[m] = aux[i++];
            else if (less(aux[j], aux[i]))
                a[m] = aux[j++];
            else
                a[m] = aux[i++];
        }*/
    }
}
