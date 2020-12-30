package util.datastructure;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

/**
 * 对{@link CharSequence}进行 3 向排序，比第 5 章字符串排序的算法更加通用。
 *
 * 它的方法还会返回原来数组中的元素在排序后的数组中下标。
 */
public class Quick3CharSequence {

    private static int[] sortedIndexes;

    public static int[] sort(CharSequence[] sequences) {
        return sort(sequences, 0, sequences.length);
    }

    public static int[] sort(CharSequence[] sequences, int fromIdx, int toIdx) {
        Objects.requireNonNull(sequences);
        if (fromIdx < 0 || toIdx <= fromIdx || toIdx > sequences.length)
            throw new IllegalArgumentException("incorrect index: fromIdx=" + fromIdx + ", toIdx=" + toIdx);

        sortedIndexes = new int[sequences.length];
        Arrays.parallelSetAll(sortedIndexes, i -> i);
        sort(sequences, fromIdx, toIdx - 1, 0);

        int[] tmp = sortedIndexes;
        sortedIndexes = null;

        return tmp;
    }

    @Test
    public void test() {
        String[] strings = {
                "she", "sells", "seashells", "by", "the", "seashore", "the", "shells", "she",
                "sells", "are", "surely", "seashells"
        };
        String[] copied = Arrays.copyOf(strings, strings.length);
        int[] indexes = sort(strings);

        for (int i = 1; i < strings.length; i++) {
            if (strings[i - 1].compareTo(strings[i]) > 0)
                throw new IllegalStateException("incorrect sort: " + strings[i - 1] + ", " + strings[i]);
        }
        for (int i = 0; i < strings.length; i++) {
            if (!strings[i].equals(copied[indexes[i]]))
                throw new IllegalStateException("sorted indexes wrong");
        }
        System.out.println(Arrays.toString(strings));
        System.out.println(Arrays.toString(indexes));
    }

    private static void sort(CharSequence[] sequences, int lo, int hi, int idx) {
        if (hi <= lo)
            return;

        int lt = lo, gt = hi;
        int v = charAt(sequences[lo], idx);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(sequences[i], idx);
            if (t < v) {
                exch(sequences, lt, i);
                exch(sortedIndexes, lt, i);
                lt++;
                i++;
            } else if (t > v) {
                exch(sequences, gt, i);
                exch(sortedIndexes, gt, i);
                gt--;
            } else {
                i++;
            }
        }
        sort(sequences, lo, lt - 1, idx);
        if (v >= 0)
            sort(sequences, lt, gt, idx + 1);
        sort(sequences, gt + 1, hi, idx);
    }

    /**
     * 当指定位置超过字符序列末尾时返回-1
     */
    private static int charAt(CharSequence cs, int idx) {
        if (idx < cs.length())
            return cs.charAt(idx);
        else
            return -1;
    }

    private static void exch(CharSequence[] a, int i, int j) {
        CharSequence tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void exch(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
