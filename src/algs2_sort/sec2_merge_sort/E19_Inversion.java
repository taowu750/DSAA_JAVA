package algs2_sort.sec2_merge_sort;

import org.junit.jupiter.api.Test;
import util.datagen.ArrayData;
import util.datagen.RandomGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * 倒置。编写一个线性对数级别（O(NlogN)）的算法统计给定数组中的“倒置”的数量。倒置指的是
 * 数组中两个顺序颠倒的元素，也就是前面的元素大于后面的元素。
 * </p>
 */
public class E19_Inversion {

    private static Comparable[] aux;


    public static int count(Comparable[] a) {
        aux = new Comparable[a.length];
        int r = count(a, 0, a.length - 1);
        aux = null;

        return r;
    }

    @SuppressWarnings("unchecked")
    public static int checkCount(Comparable[] a) {
        int cnt = 0;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i].compareTo(a[j]) > 0) {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    @Test
    public void testCount() throws Exception {
        Integer[] a = ArrayData.array(Integer.class, new RandomGenerator.Integer(0, 20),
                10);
        Integer[] aCopy = Arrays.copyOf(a, a.length);
        System.out.println(Arrays.toString(a));

        int cnt = count(a);
        assertEquals(cnt, checkCount(aCopy));
        System.out.println("倒置数为: " + cnt);
    }


    private static int count(Comparable[] a, int lo, int hi) {
        int inversions = 0;
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            inversions += count(a, lo, mid);
            inversions += count(a, mid + 1, hi);

            return inversions + merge(a, lo, mid, hi);
        }

        return inversions;
    }

    /*
    TODO: 算倒置数的可以和 E20 的方法结合起来，这样就不需要排序数组。
     */
    @SuppressWarnings("unchecked")
    private static int merge(Comparable[] a, int lo, int mid, int hi) {
        int inversions = 0;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j].compareTo(aux[i]) < 0) {
                a[k] = aux[j++];
                inversions += mid - i + 1; // j - k - 1 也行
            } else {
                a[k] = aux[i++];
            }
        }

        return inversions;
    }
}
