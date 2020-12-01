package algs5_string.sec3_substring.src;

/**
 * 显示回退的暴力算法，和暴力算法性能一样，只是实现方式不同。
 */
public class BruteBackSearch extends AbstractStringSearch {

    public static void main(String[] args) {
        test(new BruteBackSearch());
    }

    @Override
    public int search(String pat, String txt) {
        int j, M = pat.length();
        int i, N = txt.length();

        for (i = 0, j = 0; i < N && j < M; i++) {
            if (txt.charAt(i) == pat.charAt(j))
                j++;
            else {
                i -= j; // 回退本次匹配的开始位置的下一字符
                j = 0;
            }
        }

        if (j == M)
            return i - M;
        else
            return -1;
    }
}
