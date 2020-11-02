package algs2_sort.sec3_quick_sort.src;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;
import util.base.ExMath;
import util.datastructure.MyStack;

/**
 * <p>
 * 快速排序。它和归并排序是互补的：归并排序将数组分成两个子数组分别排序；
 * 快速排序当两个子数组都有序时整个数组也就自然有序了。在第一种情况中，
 * 递归调用发生在处理整个数组之前；在第二种情况中，递归调用发生在处理整个
 * 数组之后。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class QuickSort extends AbstractSort {

    private InsertionSort auxSort = new InsertionSort();


    @Override
    public void sort(Comparable[] a) {
//        sort(a, 0, a.length - 1);
//        sort3way(a, 0, a.length - 1);
        loopSort(a, 0, a.length - 1);
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new QuickSort());
    }


    private void sort(Comparable[] a, int lo, int hi) {
        if (lo < hi) {
            int p = partition(a, lo, hi);
            sort(a, lo, p - 1);
            sort(a, p + 1, hi);
        }
    }

    /*
    三向切分的快速排序，把数组分成小于、等于、大于的情况。这样会有利于快排在
    重复元素较多的情况。
     */
    private void sort3way(Comparable[] a, int lo, int hi) {
        if (hi - lo > 15) {
            int lt = lo, i = lo + 1, gt = hi;
            Comparable v = a[lo];

            while (i <= gt) {
                @SuppressWarnings("unchecked")
                int cmp = a[i].compareTo(v);
                if (cmp < 0)
                    exch(a, lt++, i++);
                else if (cmp > 0)
                    exch(a, i, gt--);
                else
                    i++;
            } // 现在 a[lo..lt-1] < v = a[lt..ht] < g[gt+1..hi]

            sort(a, lo, lt - 1);
            sort(a, gt + 1, hi);
        } else {
            auxSort.sort(a, lo, hi);
        }
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

    private void loopSort(Comparable[] a, int lo, int hi) {
        MyStack<Record> s = new MyStack<>(
                (int) ExMath.ln(hi - lo + 1) + 1);
        Record cur = new Record(lo, hi, 0);

        while (true) {
            if (cur.lo >= cur.hi || cur.state == 2) {
                if (s.isEmpty())
                    break;
                cur = s.pop();
            } else if (cur.state == 0) {
                int p = partition(a, cur.lo, cur.hi);
                s.push(new Record(p + 1, cur.hi, 1));
                cur.hi = p - 1;
            } else if (cur.state == 1) {
                s.push(new Record(cur.lo, cur.hi, 2));
                cur.state = 0;
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = median3(a, lo, hi);

        while (true) {
            while (less(a[++i], v) && i != hi);
            while (less(v, a[--j]) && j != lo);

            if (i >= j)
                break;
            exch(a, i, j);
        }
        exch(a, lo, j);

        return j;
    }

    /**
     * 三取样切分
     *
     * @param a
     * @param lo
     * @param hi
     * @return
     */
    @SuppressWarnings({"unchecked", "Duplicates"})
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
}
