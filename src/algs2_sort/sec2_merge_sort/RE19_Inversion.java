package algs2_sort.sec2_merge_sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 计算数组中倒置的数量，要求时间复杂度O(NlogN)
 */
public class RE19_Inversion {

    public static int invertNum(int[] arr) {
        return invertNum(arr, new int[arr.length], 0, arr.length - 1);
    }

    private static int invertNum(int[] arr, int[] aux, int lo, int hi) {
        if (lo < hi) {
            int mid  = lo + (hi - lo) / 2;
            int left = invertNum(arr, aux, lo, mid);
            int right = invertNum(arr, aux,mid + 1, hi);
            int center = invertMerge(arr, aux, lo, mid, hi);

            return left + right + center;
        }

        return 0;
    }

    private static int invertMerge(int[] arr, int[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1, k = lo;
        int invertNum = 0;
        while (i <= mid && j <= hi) {
            if (arr[i] <= arr[j]) {
                aux[k++] = arr[i++];
            } else {
                aux[k++] = arr[j++];
                invertNum += mid - i + 1;
            }
        }

        // 这里不需要再统计了，之前的循环里面统计过了
//        if (i <= mid) {
//            invertNum += (mid - i + 1) * (hi - mid);
//        }
        while (i <= mid) {
            aux[k++] = arr[i++];
        }
        while (j <= hi)
            aux[k++] = arr[j++];
        for (k = lo; k <= hi; k++)
            arr[k] = aux[k];

        return invertNum;
    }

    @Test
    public void test_invertNum() {
        int[] a = {3,4,0,1,2};

        assertEquals(invertNum(a), 6);
    }
}
