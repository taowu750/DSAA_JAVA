package algs2_sort.sec3_quick_sort;

import org.junit.jupiter.api.Test;
import util.algs.StdRandom;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// TODO: 接近
/**
 * 有N个螺丝和N个螺帽，一个螺丝只和一个螺帽配对。螺丝只能和螺帽比较，两个螺丝或两个螺帽之间不能对比。
 * 请将它们快速配对。
 */
public class RE15_ScrewsAndNuts {

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
}
