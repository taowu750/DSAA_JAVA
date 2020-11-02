package algs1_fundamentals.sec1_base_programming_model;

import org.junit.jupiter.api.Test;

import static util.base.ExMath.ln;

/**
 * 编写一个递归程序计算 ln(N!) 的值。
 */
public class E20_LnNFactorial {

    public static double lnNFactorial(int N) {
        if (N == 0 || N == 1) {
            return 0;
        } else if (N == 2) {
            return 1;
        }

        return lnNFactorial(N - 1) + ln(N);
    }

    @Test
    public void testLnNFactorial() throws Exception {
        System.out.println(lnNFactorial(4));
    }
}
