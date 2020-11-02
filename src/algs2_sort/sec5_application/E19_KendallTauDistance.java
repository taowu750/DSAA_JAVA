package algs2_sort.sec5_application;

import algs2_sort.sec2_merge_sort.E19_Inversion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * Kendall tau 距离。在线性对数时间内计算两组排列之间的 Kendall tau 距离。
 * </p>
 * <p>
 * 一组排列就是一组 N 个整数的数组，其中 0 到 N-1 的每个数只出现一次。
 * </p>
 * <p>
 * Kendall tau 距离就是在两组排列中顺序不同的数对的数目。例如，0 3 1 6 2 5 4
 * 和 1 0 3 6 4 2 5 之间的 Kendall tau 距离是 4，因为 0-1,3-1,2-4,5-4 这 4
 * 对数字在两组排列中的相对顺序不同。
 * </p>
 */
@SuppressWarnings("unchecked")
public class E19_KendallTauDistance {

    /*
    a 和 b 中的元素相同，且无重复元素。
     */
    public int kendallTau(int[] a, int[] b) {
        int n = a.length;
        // aIndex 下标是 a 的值，值是 a 的下标。这是一个逆序数组
        int[] aIndex = new int[n];
        for (int i = 0; i < n; i++) {
            aIndex[a[i]] = i;
        }
        // bIndexInA 中，b 的值作为 aIndex 的下标，得到的 aIndex 的值作为 bIndexInA 的值
        // 也就是说，bIndexInA 存储的是 b[i] 在 a 中的下标。
        Integer[] bIndexInA = new Integer[n];
        for (int i = 0; i < n; i++) {
            bIndexInA[i] = aIndex[b[i]];
        }

        // 计算逆序
        return E19_Inversion.count(bIndexInA);
    }

    @Test
    public void testKendallTau() throws Exception {
        int[] a = {0, 3, 1, 6, 2, 5, 4};
        int[] b = {1, 0, 3, 6, 4, 2, 5};

        assertEquals(kendallTau(a, b), 4);
    }
}
