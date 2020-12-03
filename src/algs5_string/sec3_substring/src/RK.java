package algs5_string.sec3_substring.src;

/**
 * <p>
 * Rabin-Karp 算法是一种基于散列的算法。计算模式字符串的散列值，并计算文本中所有 M 个长度的子字符串的散列值。
 * 通过比较散列值判断它们是否相等。该算法的关键在于如何高效地计算散列值。
 * </p>
 * <p>
 * 计算散列值的方法是 Horner 方法。取模操作的基本性质是，在每次运算结果后取模等价于完成所有运算再将
 * 最终结果取模。
 * </p>
 * <p>
 * 为了避免散列值相等后还要比较每个字符，我们可以将模数 Q（散列表大小）设为非常大的素数，这样冲突
 * 的几率将会非常小。
 * </p>
 */
public class RK extends AbstractStringSearch {

    private String pat;
    // 模式字符串的散列值
    private long patHash;
    private int M;
    // 散列表大小，一个很大的素数。
    private long Q = 4611686018427387847L;
    private int R = 256;
    // R^(M - 1) % Q
    private long RM;


    public RK(String pat) {
        calcHash(pat);
    }

    public static void main(String[] args) {
        test(new RK(""));
    }

    @Override
    public int search(String pat, String txt) {
        calcHash(pat);
        return search(txt);
    }

    public int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        // 一开始就匹配成功
        if (patHash == txtHash && check(txt, 0))
            return 0;

        for (int i = M; i < N; i++) {
            // 减去第一个数字，加上最后一个数字，再次检查匹配
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;

            if (patHash == txtHash && check(txt, i - M + 1))
                return i - M + 1;
        }

        return -1;
    }

    private void calcHash(String pat) {
        this.pat = pat;
        M = pat.length();
        RM = 1;
        for (int i = 1; i < M; i++) {
            RM = (RM * R) % Q;
        }
        // RM = R^(M - 1) % Q
        patHash = hash(pat, M);
    }

    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++) {
            h = (R * h + key.charAt(j)) % Q;
        }

        return h;
    }

    private boolean check(String txt, int i) {
        for (int j = 0; j < M; j++) {
            if (txt.charAt(i + j) != pat.charAt(j))
                return false;
        }

        return true;
    }


}
