package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 查找匹配的最小索引
 */
public class RE10_BinarySearchEx {

    public int binarySearch(int[] arr, int in) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int center = (low + high) / 2;
            if (arr[low] == in)
                return low;
            else if (arr[center] == in)
                high = center;
            else if (arr[center] < in)
                low = center + 1;
            else
                high = center - 1;
        }

        return -1;
    }

    @Test
    public void test_binarySearch() {
        int[] arr = {1,2,3,4,5,6,7};
        assertEquals(binarySearch(arr, 4), 3);

        arr = new int[]{1,2,3,4,5,6,7,8,9,10,11};
        assertEquals(binarySearch(arr, 6), 5);

        arr = new int[]{1,1,1,2,2,3,4,5,5,6,6,7,7,7,8,9,10,10};
        assertEquals(binarySearch(arr, 1), 0);
        assertEquals(binarySearch(arr, 2), 3);
        assertEquals(binarySearch(arr, 3), 5);
        assertEquals(binarySearch(arr, 5), 7);
        assertEquals(binarySearch(arr, 7), 11);
        assertEquals(binarySearch(arr, 10), 16);
    }
}
