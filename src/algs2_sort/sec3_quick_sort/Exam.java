package algs2_sort.sec3_quick_sort;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;
import util.algs.StdRandom;
import util.datagen.ArrayConverter;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class Exam {

    public static abstract class AbstractQuickSort extends AbstractSort {

        protected void middle(Comparable[] a, int lo, int hi) {
            int mid = lo + (hi - lo) / 2;
            if (less(a[mid], a[lo]))
                exch(a, lo, mid);
            if (less(a[hi], a[mid]))
                exch(a, hi, mid);
            if (less(a[mid], a[lo]))
                exch(a, lo, mid);
            exch(a, lo, mid);
        }
    }

    public static class QuickSort extends AbstractQuickSort {

        @Override
        public void sort(Comparable[] a) {
            sort(a, 0, a.length - 1);
        }

        private void sort(Comparable[] a, int lo, int hi) {
            if (lo < hi) {
                if (hi - lo == 1) {
                    if (less(a[hi], a[lo]))
                        exch(a, lo, hi);
                    return;
                }

                int j = partition(a, lo, hi);
                sort(a, lo, j - 1);
                sort(a, j + 1, hi);
            }
        }

        private int partition(Comparable[] a, int lo, int hi) {
            middle(a, lo, hi);
            Comparable v = a[lo];
            int i = lo, j = hi + 1;

            while (true) {
                while (less(a[++i], v));
                while (less(v, a[--j]));

                if (i < j)
                    exch(a, i, j);
                else
                    break;
            }
            exch(a, lo, j);

            return j;
        }
    }

    @Test
    public void test_QuickSort() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            AbstractSort.simpleTest(new QuickSort(), 2000, false);
        }
    }

    public static class Quick3waySort extends AbstractQuickSort {

        @Override
        public void sort(Comparable[] a) {
            sort(a, 0, a.length - 1);
        }

        private void sort(Comparable[] a, int lo, int hi) {
            if (lo < hi) {
                if (hi - lo == 1) {
                    if (less(a[hi], a[lo]))
                        exch(a, lo, hi);
                    return;
                }

                int[] sp = partition(a, lo, hi);
                sort(a, lo, sp[0] - 1);
                sort(a, sp[1] + 1, hi);
            }
        }

        private int[] partition(Comparable[] a, int lo, int hi) {
            middle(a, lo, hi);
            Comparable v = a[lo];
            int lt = lo, i = lo + 1, gt = hi - 1;

            for (int c = a[i].compareTo(v); i <= gt; c = a[i].compareTo(v)) {
                if (c < 0)
                    exch(a, lt++, i++);
                else if (c > 0)
                    exch(a, gt--, i);
                else
                    i++;
            }

            return new int[]{lt, gt};
        }
    }

    @Test
    public void test_Quick3waySort() throws Exception {
        for (int i = 0; i < 20; i++) {
            AbstractSort.simpleTest(new Quick3waySort(), 200, false);
        }
    }

    // TODO: 接近
    /**
     * ###15. 有N个螺丝和N个螺帽，一个螺丝只和一个螺帽配对。螺丝只能和螺帽比较，两个螺丝或两个螺帽之间不能对比。
     * 请将它们快速配对。
     * @param screws
     * @param nuts
     * @return
     */
    public static int[] screwAndNut(int[] screws, int[] nuts) {
        int[] matched = new int[screws.length];
        int m = 0;
        int p = snPartition(screws, nuts[0], 0, screws.length - 1);
        matched[m++] = screws[p];
        for (int i = 1; i < nuts.length; i++) {
            if (nuts[i] < screws[p]) {
                p = snPartition(screws, nuts[i], 0, p - 1);
            } else if (nuts[i] > screws[p]) {
                p = snPartition(screws, nuts[i], p + 1, screws.length - 1);
            }
            matched[m++] = screws[p];
        }

        return matched;
    }

    private static int snPartition(int[] screws, int nut, int lo, int hi) {
        int i = lo, j = hi;

        while (true) {
            while (screws[i] < nut)
                i++;
            while (screws[j] > nut)
                j--;

            if (i < j) {
                int temp = screws[i];
                screws[i] = screws[j];
                screws[j] = temp;
            } else
                break;
        }

        return i;
    }

    @Test
    public void test_screwAndNut() throws Exception {
        int[] arr = IntStream.range(0, 100).toArray();
        int[] screws = Arrays.copyOf(arr, arr.length);
        int[] nuts = Arrays.copyOf(arr, arr.length);

        StdRandom.shuffle(screws);
        StdRandom.shuffle(nuts);

        int[] matched = screwAndNut(screws, nuts);
        Arrays.sort(matched);
        assertArrayEquals(matched, arr);
    }

    // TODO: 神秘
    /**
     * ###16. 生成最佳数组。无重复元素，每次切分后子数组大小最多差1。
     * @param N
     * @return
     */
    public static int[] bestCase(int N) {
        int[] arr = new int[N];
        bestCase(arr, 0, arr.length - 1, 0, 0);

        return arr;
    }

    private static void bestCase(int[] arr, int lo, int hi, int ind, int debugDeep) {
        if (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
//            System.out.println(IntStream.range(0, debugDeep)
//                    .mapToObj(i -> "-").collect(Collectors.joining()) +
//                    "lo=" + lo + ", hi=" + hi + ", mid=" + mid + ", ind=" + ind);
            arr[ind] = mid;
            // 递归调用有层数，不能遍历整个数组
            if (lo < hi)
                arr[lo + 1] = lo;
            // 左子数组的中点需要放在mid，因为快排中a[lo]最后会和中间元素交换位置
            bestCase(arr, lo, mid - 1, mid, debugDeep + 1);
            bestCase(arr, mid + 1, hi, mid + 1, debugDeep + 1);
        }
    }

    @Test
    public void test_bestCase() throws Exception {
        int N = 100;
        assertArrayEquals(bestCase(N), ArrayConverter.to(E16_BestCase.bestCase(N)));
    }

    // TODO: 不行
    /**
     * ###22. 快速三向切分
     */
    public static class FastQuick3waySort extends AbstractQuickSort {

        private InsertionSort auxSort = new InsertionSort();

        @Override
        public void sort(Comparable[] a) {
            sort(a, 0, a.length - 1);
        }

        private void sort(Comparable[] a, int lo, int hi) {
            if (hi - lo > 10) {
                int[] pos = partition(a, lo, hi);
                sort(a, lo, pos[0] - 1);
                sort(a, pos[1] + 1, hi);
            } else {
                auxSort.sort(a, lo, hi);
            }
        }

        private int[] partition(Comparable[] a, int lo, int hi) {
            middle(a, lo, hi);
            Comparable v = a[lo];
            int p = lo + 1, q = hi, i = lo, j = hi + 1;

            while (true) {
                int c = a[++i].compareTo(v);
                while (c <= 0) {
                    if (c == 0) {
                        exch(a, i, p++);
                    }
                    if (i == hi)
                        break;
                    c = a[++i].compareTo(v);
                }
                c = v.compareTo(a[--j]);
                while (c <= 0) {
                    if (c == 0) {
                        exch(a, j, q--);
                    }
                    if (j == lo)
                        break;
                    c = v.compareTo(a[--j]);
                }

                if (i < j) {
                    exch(a, i, j);
                }
                else
                    break;
            }

            if (p > lo && p < j) {
                System.arraycopy(a, p, a, lo, j - p + 1);
                Arrays.fill(a, lo + j - p + 1, j + 1, v);
            }
            if (q < hi && q > i) {
                System.arraycopy(a, i, a, hi - q + i, q - i + 1);
                Arrays.fill(a, i, hi - q + i, v);
            }

            return new int[]{lo + j - p, hi - q + i};
        }
    }

    @Test
    public void test_FastQuick3waySort() throws Exception {
        AbstractSort.simpleTest(new FastQuick3waySort(), 200, false);
    }
}
