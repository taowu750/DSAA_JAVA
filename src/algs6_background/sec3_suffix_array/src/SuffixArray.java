package algs6_background.sec3_suffix_array.src;

import util.datastructure.BinarySearch;
import util.datastructure.Quick3CharSequence;

import java.util.Objects;

/**
 * 一段字符串的后缀数组。
 *
 * 顾名思义，后缀数组是一个字符串的所有后缀组成的数组。长度为 N 的字符串，
 * 它的后缀数量也为 N。后缀数组已排序。
 *
 * 注意，select(rank(key)) 是后缀数组中第一个以 key 为前缀的后缀字符串。
 */
public class SuffixArray {

    private CharSequence text;
    // 使用字符序列视图类减少空间占用
    private SuffixView[] suffixes;
    private int[] suffixIndexes;

    public SuffixArray(CharSequence text) {
        Objects.requireNonNull(text);
        if (text.length() == 0)
            throw new IllegalArgumentException("text cannot be an empty string");

        suffixes = new SuffixView[text.length()];
        this.text = text;
        for (int i = 0; i < text.length(); i++) {
            suffixes[i] = new SuffixView(text, i);
        }
        suffixIndexes = Quick3CharSequence.sort(suffixes);
    }

    /**
     * 文本 text 的长度
     *
     * @return
     */
    public int length() {
        return text.length();
    }

    /**
     * 后缀数组中的第 i 个元素。i 的范围为 [0, length - 1]。
     *
     * @param i
     * @return
     */
    public CharSequence select(int i) {
        return suffixes[i];
    }

    /**
     * 第 i 个后缀在原字符串中的下标。i 的范围为 [0, length - 1]。
     *
     * @param i
     * @return
     */
    int index(int i) {
        return suffixIndexes[i];
    }

    /**
     * 第 i - 1 个后缀和第 i 个后缀的最长公共前缀长度。i 的范围为 [1, length - 1]。
     *
     * @param i
     * @return
     */
    int lcp(int i) {
        return lcp(select(i - 1), select(i));
    }

    /**
     * 小于 key 的后缀数量。
     *
     * @param key
     * @return
     */
    int rank(CharSequence key) {
        int idx = BinarySearch.search(suffixes, key);
        if (idx >= 0)
            return idx;
        else
            return -idx;
    }


    /**
     * 一个字符序列的后缀子字符序列视图类。通过存储开始下标，避免了实际的字符存储，
     * 大大减少了空间占用。
     *
     * 注意需要和 CharSequence 作比较，因为需要和 String 做比较，而 String 不能转成 SuffixView
     */
    private static class SuffixView implements CharSequence, Comparable<CharSequence> {

        private CharSequence text;
        private int startIdx;

        public SuffixView(CharSequence text, int startIdx) {
            this.text = text;
            this.startIdx = startIdx;
        }

        @Override
        public int length() {
            return text.length() - startIdx;
        }

        @Override
        public char charAt(int index) {
            return text.charAt(startIdx + index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return text.subSequence(startIdx + start, startIdx + end);
        }

        @Override
        public String toString() {
            return text.subSequence(startIdx, text.length()).toString();
        }

        @Override
        public int compareTo(CharSequence that) {
            int thisLen = length(), thatLen = that.length();
            int len = Math.min(thisLen, thatLen);
            for (int idx = 0; idx < len; idx++) {
                char thisCh = charAt(idx), thatCh = that.charAt(idx);
                if (thisCh < thatCh)
                    return -1;
                else if (thisCh > thatCh)
                    return 1;
            }

            return Integer.compare(thisLen, thatLen);
        }
    }

    private static int lcp(CharSequence s, CharSequence t) {
        int N = Math.min(s.length(), t.length());
        for (int i = 0; i < N; i++) {
            if (s.charAt(i) != t.charAt(i))
                return i;
        }

        return N;
    }
}
