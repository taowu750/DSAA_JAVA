package algs1_fundamentals.sec1_base_programming_model;

public class Exam {

    /**
     * 正整数转二进制字符串
     * @param i
     * @return
     */
    public static String i2bs(int i) {
        String s = "";
        int r;
        do {
            r = i & 1;
            i = i >>> 1;
            s = r + s;
        } while (i > 0);

        return s;
    }

    /**
     * 返回不大于log2N的最大整数，N也是整数。
     * @return
     */
    public static int lg(int N) {
        // log2N = x => 2^x = N
        // N >= 2^r

        int r = 0;
        while ((N = N >>> 1) > 0) {
            r += 1;
        }

        return r;
    }
}
