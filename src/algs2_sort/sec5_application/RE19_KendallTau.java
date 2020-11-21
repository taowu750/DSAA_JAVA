package algs2_sort.sec5_application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: 不行
/**
 * 计算两组排列之间的 kendallTau 距离。一组排列就是一组N个整数的数组，
 * 其中0 - N-1每个数都只出现一次。要算出两个排列中顺序不同的数对(逆序对)的数目。
 */
public class RE19_KendallTau {

    public static int kendallTauDemo(int[] s1, int[] s2) {
        int N = s1.length;
        int[] s2idx = new int[N];
        for (int i = 0; i < N; i++) {
            s2idx[s2[i]] = i;
        }

        int cnt = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                boolean s1PairCompare = s1[i] < s1[j];
                boolean s2PairCompare = s1PairCompare == s2idx[s1[i]] < s2idx[s1[j]];
                if (s1PairCompare != s2PairCompare) {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    @Test
    public void test_kendallTauDemo() throws Exception {
        int[] s1 = {0, 3, 4, 2, 1};
        int[] s2 = {4, 0, 3, 1, 2};

        assertEquals(kendallTauDemo(s1, s2), 3);
    }

    public static int kendallTau(int[] s1, int[] s2) {
        return 0;
    }
}
