package algs6_background.sec3_suffix_array.src;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.io.AlgsDataIO;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用后缀数组实现的功能。
 */
public class SuffixArrayUtil {

    /**
     * 查找字符串中最长重复子串。
     *
     * @param text
     * @return
     */
    public static String lrs(String text) {
        SuffixArray suffixArray = new SuffixArray(text);
        String lrs = "";
        for (int i = 1; i < text.length(); i++) {
            int len = suffixArray.lcp(i);
            if (len > lrs.length())
                lrs = suffixArray.select(i).subSequence(0, len).toString();
        }

        return lrs;
    }

    @Test
    public void testLrs() {
        String tinyTale = String.join(" ", AlgsDataIO.getTinyTale());
        Assertions.assertEquals(lrs(tinyTale), "st of times it was the ");
    }

    /**
     * 在文本中查找关键字的上下文（该字符串前后若干个字符）。
     *
     * @param text
     * @param keyword
     * @param contextNum
     * @return
     */
    public static List<String> kwic(String text, String keyword, int contextNum) {
        List<String> list = new ArrayList<>();
        SuffixArray suffixArray = new SuffixArray(text);
        for (int i = suffixArray.rank(keyword);
             i < text.length() && suffixArray.select(i).toString().startsWith(keyword);
             i++) {
            int from = Math.max(0, suffixArray.index(i) - contextNum);
            int to = Math.min(text.length() - 1, from + keyword.length() + 2 * contextNum);
            list.add(text.substring(from, to));
        }

        return list;
    }

    @Test
    public void testKwic() {
        String tale = AlgsDataIO.getTale().replaceAll("\\s+", " ");
        System.out.println(kwic(tale, "search", 15));
        System.out.println(kwic(tale, "better thing", 15));
    }
}
