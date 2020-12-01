package algs5_string.sec3_substring.src;

import java.util.Objects;

/**
 * <p>
 * 更好的 KMP 算法是只使用一个一维的 next[] 数组，它和模式串长度相等。
 * </p>
 * <p>
 * 令 P 为模式串，next[i] 表示 P[0] ~ P[i] 这一个子串，使得 前k个字符恰等于后k个字符 的最大的k. 特别地，
 * k不能取i+1（因为这个子串一共才 i+1 个字符，自己肯定与自己相等，就没有意义了）。
 * </p>
 * <p>
 * 该算法的解释参见 https://www.zhihu.com/question/21923021/answer/1032665486
 * </p>
 */
public class BetterKMP extends AbstractStringSearch {

    private String pat;
    private int[] next;


    public BetterKMP(String pat) {
        Objects.requireNonNull(pat);
        assert pat.length() > 0;

        genNext(pat);
    }

    @Override
    public int search(String pat, String txt) {
        genNext(pat);

        return search(txt);
    }

    public int search(String txt) {
        if (txt.length() < pat.length())
            return -1;

        int txtPos = 0, patPos = 0;
        while (txtPos < txt.length()) {
            if (txt.charAt(txtPos) == pat.charAt(patPos)) {
                // 如果 txt 当前位置字符等于 pat 当前位置字符，则它们都可以向前推进
                patPos += 1;
                txtPos += 1;
            } else if (patPos > 0) {
                // 如果字符不相等，且 patPos 大于 0，那让 patPos 等于上一个 patPos 公共前后缀长度
                patPos = next[patPos - 1];
            } else {
                // patPos 等于 0，txtPos 推进
                txtPos += 1;
            }

            // 如果 patPos 等于 pat 的长度，表示找到子串位置
            if (patPos == pat.length()) {
                return txtPos - patPos;
            }
        }

        return -1;
    }

    private void genNext(String pat) {
        this.pat = pat;
        next = new int[pat.length()];
        // next[0] 一定是 0。now 等于 next[x - i]（i = 1,...,x）
        int now = 0, x = 1;

        while (x < pat.length()) {
            if (pat.charAt(now) == pat.charAt(x)) {
                // 如果 pat[now] == pat[x]，那么可以向右扩展一位。
                next[x] = now + 1;
                now += 1;
                x += 1;
            } else if (now > 0) {
                // 如果 pat[now] != pat[x]，缩小 now
                now = next[now - 1];
            } else {
                // now 已经为 0，无法再缩小，所以 next[x] 为 0
                x += 1;
            }
        }
    }
}
