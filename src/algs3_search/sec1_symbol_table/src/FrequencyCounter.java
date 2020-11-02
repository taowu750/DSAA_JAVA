package algs3_search.sec1_symbol_table.src;

import org.junit.jupiter.api.Test;
import util.datastructure.*;
import util.io.AlgsDataIO;
import util.io.FormatPrint;
import util.test.SpeedTester;

/**
 * 计算单词频率。
 */
public class FrequencyCounter {

    public static String max(String[] words, SymbolTable<String, Integer> st, int minLen) {
        for (String word : words) {
            if (word.length() >= minLen) {
                Integer cnt = st.get(word);
                if (cnt == null)
                    st.put(word, 1);
                else
                    st.put(word, cnt + 1);
            }
        }

        String max = "";
        int maxCnt = 0;
        st.put(max, maxCnt);
        for (String s : st.keys()) {
            int cnt = st.get(s);
            if (cnt > maxCnt) {
                max = s;
                maxCnt = cnt;
            }
        }

        return max;
    }

    public static void testSpeed(SymbolTable<String, Integer> st, boolean isTestLargeData) {
        String[] tinyTale = AlgsDataIO.getTinyTale();
        String[] tale = AlgsDataIO.getTale();
        String[] leipzig1M = null;
        if (isTestLargeData)
            leipzig1M = AlgsDataIO.getLeipzig1M();
        int minLen = 4;

        String[] max = new String[3];
        double time = SpeedTester.testRunTime(() -> max[0] = max(tinyTale, st, minLen));
        int times = st.get(max[0]);
        st.clear();
        double time2 = SpeedTester.testRunTime(() -> max[1] = max(tale, st, minLen));
        int times2 = st.get(max[1]);
        st.clear();
        double time3 = -1;
        int times3 = 0;
        if (isTestLargeData) {
            String[] finalLeipzig1M = leipzig1M;
            time3 = SpeedTester.testRunTime(() -> max[2] = max(finalLeipzig1M, st, minLen));
            times3 = st.get(max[2]);
        }

        double finalTime = time3;
        int finalTimes = times3;
        FormatPrint.group(() -> {
            System.out.printf("tinyTale: 频率最高的单词是 \"%s\", 出现了 %d 次, 耗时 %.3fs\n",
                    max[0], times, time);
            System.out.printf("tale: 频率最高的单词是 \"%s\", 出现了 %d 次, 耗时 %.3fs\n",
                    max[1], times2, time2);
            if (isTestLargeData)
                System.out.printf("leipzig1M: 频率最高的单词是 \"%s\", 出现了 %d 次, 耗时 %.3fs\n",
                        max[2], finalTimes, finalTime);
        }, "测试 " + st.getClass().getSimpleName() + " 在单词计数程序中的性能");
    }

    @Test
    public void testSortedArrayST() throws Exception {
        // 对于 leipzig1M 耗时约 58 秒左右
        testSpeed(new SortedArrayST<>(), true);
    }

    @Test
    public void testBinaryTreeST() throws Exception {
        // 对于 leipzig1M 耗时约 30 秒左右
        testSpeed(new BinaryTreeST<>(), true);
    }

    @Test
    public void testRedBlackTreeST() throws Exception {
        // 对于 leipzig1M 耗时约 23 秒左右
        testSpeed(new RedBlackTreeST<>(), true);
    }

    @Test
    public void testHashST() throws Exception {
        // 对于 leipzig1M 耗时约 7 秒左右
        testSpeed(new HashST<>(), true);
    }
}
