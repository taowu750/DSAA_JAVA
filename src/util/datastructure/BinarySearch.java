package util.datastructure;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinarySearch {

    @Test
    public void test() {
        Integer[] integers = {1, 3, 3, 5, 7, 9, 11, 11};

        assertEquals(search(integers, 1), 0);
        assertEquals(search(integers, 3), 1);
        assertEquals(search(integers, 9), 5);
        assertEquals(search(integers, 11), 6);
        assertEquals(search(integers, 0), 0);
        assertEquals(search(integers, 2), -1);
        assertEquals(search(integers, 4), -3);
        assertEquals(search(integers, 6), -4);
        assertEquals(search(integers, 8), -5);
        assertEquals(search(integers, 10), -6);
        assertEquals(search(integers, 12), -8);
    }

    /**
     * 查找数组 a 中与 key 匹配的最小下标。如果 key 不存在于 a 中，返回小于 key 的元素数量的负数。
     *
     * @param a 已排序数组
     * @param key 想要查找的键
     * @param fromIndex 查找的数组区间开始处（包含）
     * @param toIndex 查找的数组区间结束处（不包含）
     * @param comparator 比较器，不提供则使用元素的自然排序（需要元素是 Comparable）
     * @return
     */
    public static int search(Object[] a, Object key, int fromIndex, int toIndex, Comparator comparator) {
        Objects.requireNonNull(a);
        if (fromIndex < 0 || toIndex <= fromIndex || toIndex > a.length)
            throw new IllegalArgumentException("incorrect index: fromIndex=" + fromIndex +
                    ", toIndex=" + toIndex);

        int lo = fromIndex, hi = toIndex - 1;
        while (lo <= hi) {
            int center = lo + (hi - lo) / 2;
            if (compare(a[lo], key, comparator) == 0)
                return lo;
            else {
                int centerCmpKey = compare(a[center], key, comparator);
                if (centerCmpKey == 0) {
                    hi = center;
                } else if (centerCmpKey > 0) {
                    hi = center - 1;
                } else {
                    lo = center + 1;
                }
            }
        }

        return -lo;
    }

    public static int search(Object[] a, Object key, int fromIndex, int toIndex) {
        return search(a, key, fromIndex, toIndex, null);
    }

    public static int search(Object[] a, Object key, Comparator comparator) {
        return search(a, key, 0, a.length, comparator);
    }

    public static int search(Object[] a, Object key) {
        return search(a, key, null);
    }

    @SuppressWarnings("unchecked")
    private static int compare(Object a, Object b, Comparator comparator) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            Comparable ca = (Comparable) a;
            return ca.compareTo(b);
        }
    }
}
