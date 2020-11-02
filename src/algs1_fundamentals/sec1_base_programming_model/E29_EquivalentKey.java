package algs1_fundamentals.sec1_base_programming_model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * <p>
 * 等值键：编写一个 rank() 方法，它接受一个键和一个整形数组（可能存在重复键）作为
 * 参数并返回数组中小于此键的元素数量，以及一个类似的方法 count() 来返回数组中等于
 * 该键的元素数量。
 * </p>
 * <p>
 * 如果 i 和 j 分别是 rank(key, a) 和 count(key, a) 的返回值，那么 a[i..i+j-1]
 * 就是数组中所有和 key 相等的元素。
 * </p>
 */
public class E29_EquivalentKey {

    public static int rank(int key, int[] a) {
        int[] pos = binarySearch(key, a);
        int low = pos[0], high = pos[1], center = pos[2];

        if (low <= high) {
            return center;
        } else {
            return high + 1;
        }
    }

    @Test
    public void testRank() throws Exception {
        int[] a = new int[]{10, 11, 11, 12, 16, 18, 23, 29, 29, 33, 48, 54, 57, 68, 77, 77, 77, 84, 98};
        assertEquals(rank(29, a), 7);
        assertEquals(rank(30, a), 9);
        assertEquals(rank(57, a), 12);
    }

    public static int count(int key, int[] a) {
        int[] pos = binarySearch(key, a);
        int low = pos[0], high = pos[1], center = pos[2];

        if (low <= high) {
            int count = 1;
            for (int i = center + 1; i < a.length; i++) {
                if (a[i] != key) {
                    break;
                }
                count++;
            }

            return count;
        } else {
            return 0;
        }
    }

    @Test
    public void testCount() throws Exception {
        int[] a = new int[]{10, 11, 11, 12, 16, 18, 23, 29, 29, 33, 48, 54, 57, 68, 77, 77, 77, 84, 98};
        assertEquals(count(11, a), 2);
        assertEquals(count(30, a), 0);
        assertEquals(count(77, a), 3);
    }


    private static int[] binarySearch(int key, int[] a) {
        int low = 0, high = a.length - 1, center = 0;
        while (low <= high) {
            center = (low + high) / 2;
            if (key < a[center]) {
                high = center - 1;
            } else if (key > a[center]) {
                low = center + 1;
            } else {
                break;
            }
        }

        return new int[]{low, high, center};
    }
}
