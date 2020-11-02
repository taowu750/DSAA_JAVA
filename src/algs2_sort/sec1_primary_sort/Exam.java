package algs2_sort.sec1_primary_sort;

import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

public class Exam {

    static class SelectSort extends AbstractSort {

        @Override
        public void sort(Comparable[] a) {
            for (int i = 0; i < a.length - 1; i++) {
                int min_index = i;
                for (int j = i + 1; j < a.length; j++) {
                    if (less(a[j], a[min_index])) {
                        min_index = j;
                    }
                }
                if (min_index != i)
                    exch(a, min_index, i);
            }
        }
    }

    @Test
    public void test_SelectSort() throws InterruptedException {
        AbstractSort.simpleTest(new SelectSort(), 200, false);
    }

    static class InsertSort extends AbstractSort {

        @Override
        public void sort(Comparable[] a) {
            for (int i = 1; i < a.length; i++) {
                Comparable inserted = a[i];
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if (less(inserted, a[j]))
                        a[j + 1] = a[j];
                    else
                        break;
                }
                a[j + 1] = inserted;
            }
        }
    }

    @Test
    public void test_InsertSort() throws InterruptedException {
        AbstractSort.simpleTest(new InsertSort(), 200, false);
    }

    static class ShellSort extends AbstractSort {

        @Override
        public void sort(Comparable[] a) {
            int N = a.length, h = 1;
            while (h < N / 3)
                h = 3 * h + 1;
            while (h >= 1) {
                for (int i = h; i < N; i++) {
                    Comparable inserted = a[i];
                    int j;
                    for (j = i - h; j >= 0 && less(inserted, a[j]); j -= h)
                        a[j + h] = a[j];
                    a[j + h] = inserted;
                }
                h /= 3;
            }
        }
    }

    @Test
    public void test_ShellSort() throws InterruptedException {
        AbstractSort.simpleTest(new ShellSort(), 200, false);
    }

    /**
     * ###14. 只能查看最左边的两个数字，可以交换它们，或是将最左边的一个数字放到最右边。将这样的序列排序。
     * @param list
     */
    public static void dequeueSort(LinkedList<Integer> list) {
        boolean exchanged = true;
        while (exchanged) {
            exchanged = false;
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    if (list.get(0) > list.get(1)) {
                        int temp = list.get(0);
                        list.set(0, list.get(1));
                        list.set(1, temp);
                        exchanged = true;
                    }
                }
                list.add(list.remove(0));
            }
        }
    }

    @Test
    public void test_dequeueSort() {
        LinkedList<Integer> list = new LinkedList<>();
        Collections.addAll(list, 3, 1, 9, 5, 2, 1, 8, 4, 2, 3, 5);
        dequeueSort(list);
        System.out.println(list);
    }
}
