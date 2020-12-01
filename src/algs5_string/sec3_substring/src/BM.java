package algs5_string.sec3_substring.src;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Boyer-Moore 字符串匹配算法。它从右向左扫描模式字符串并将它和文本匹配。
 * 使用一个数组 right[] 记录字母表中每个字符在模式中最靠右的地方（不存在为 -1）。
 * 这个值表示如果字符出现在文本中且造成了一次匹配失败需要向右跳跃多远。
 * </p>
 * <p>
 * 如果从 M - 1 到 0 的所有 j，txt.charAt(i + j) 都和 pat.charAt(j) 相等，
 * 那么就找到了一个匹配。否则匹配失败，会遇到下面三种情况：
 * <ol>
 * <li>如果造成匹配失败的字符不包含在模式字符串中，将模式字符串向右移动 j + 1 个位置（即将 i 增加 j + 1）</li>
 * <li>如果造成匹配失败的字符包含在模式字符串中，那就可以使用 right[] 数组来将模式和文本对齐</li>
 * <li>如果上述方式无法增大 i，则至少要让 i + 1</li>
 * </ol>
 * </p>
 */
public class BM extends AbstractStringSearch {

    private Map<Character, Integer> right;
    private String pat;

    public BM(String pat) {
        Objects.requireNonNull(pat);
        assert pat.length() > 0;

        right = new HashMap<>();
        genRight(pat);
    }

    public static void main(String[] args) {
        test(new BM("n"));
    }

    @Override
    public int search(String pat, String txt) {
        genRight(pat);

        return search(txt);
    }

    public int search(String txt) {
        int N = txt.length(), M = pat.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                // 如果字符不匹配
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    skip = j - ri(txt.charAt(i + j));
                    // skip 可能小于 0，当 right 大于 j 时就会发生这种情况
                    if (skip < 1)
                        skip = 1;
                    break;
                }
            }
            // 找到匹配
            if (skip == 0)
                return i;
        }

        return -1;
    }

    private void genRight(String pat) {
        right.clear();
        this.pat = pat;
        char[] chars = pat.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            right.put(chars[j], j);
        }
    }

    private int ri(char c) {
        return Optional.ofNullable(right.get(c)).orElse(-1);
    }
}
