package algs1_fundamentals;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 第一章开头的代码。
 */
public class Src_Fundamentals {

    /**
     * 欧里几德算法：计算两个非负整数 p 和 q 的最大公约数：若 q 是 0，则最大公约数为 p。
     * 否则，将 p 除以 q 得到余数 r，p 和 q 的最大公约数即为 q 和 r 的最大公约数。
     *
     * @param p
     * @param q
     * @return
     */
    public static int gcd(int p, int q) {
        if (q == 0)
            return p;

        int r = p % q;
        while (r != 0) {
            p = q;
            q = r;
            r = p % q;
        }

        // 这里需要注意的是需要返回 q 而不是 p
        return q;
    }

    public static void main(String[] args) {
        assertEquals(gcd(5, 15), 5);
    }
}
