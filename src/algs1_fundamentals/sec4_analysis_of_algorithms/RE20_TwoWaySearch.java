package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: 不行
/**
 * 在一个先增后减的双调数组里面查找是否包含指定值，要求最坏比较次数为3lgN
 */
public class RE20_TwoWaySearch {

    public boolean sdSearch(int[] arr, int key, int low, int high) {
        if (low <= high) {
            int center = (low + high) / 2;
            if (arr[center] == key)
                return true;
            if (center == 0 || center == arr.length - 1)
                return false;
            boolean moreLeft = arr[center] > arr[center - 1];
            boolean moreRight = arr[center] > arr[center + 1];
            if (arr[center] < key) {
                if (moreLeft && moreRight)
                    return sdSearch(arr, key, low, center - 1) ||
                            sdSearch(arr, key, center + 1, high);
                if (moreRight)
                    return sdSearch(arr, key, low, center - 1);
                else
                    return sdSearch(arr, key, center + 1, high);
            } else {
                return sdSearch(arr, key, low, center - 1) ||
                        sdSearch(arr, key, center + 1, high);
            }
        }

        return false;
    }

    @Test
    public void test_sdSearch() {
        int[] a = {0, 3, 5, 6, 7, 8, 11, 23, 21, 20, 16, 14, 11, 8, 3, 1};

        assertTrue(sdSearch(a, 3, 0, a.length - 1));
        assertTrue(sdSearch(a, 0, 0, a.length - 1));
        assertTrue(sdSearch(a, 1, 0, a.length - 1));
        assertTrue(sdSearch(a, 11, 0, a.length - 1));
        assertTrue(sdSearch(a, 23, 0, a.length - 1));
        assertTrue(sdSearch(a, 21, 0, a.length - 1));
        assertTrue(sdSearch(a, 14, 0, a.length - 1));
    }
}
