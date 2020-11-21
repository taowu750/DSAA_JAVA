package algs1_fundamentals.sec1_base_programming_model;

public class RE14_MaximumLog {

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
