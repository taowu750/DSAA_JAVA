package algs1_fundamentals.sec1_base_programming_model;

public class RE9_IntToBinStr {

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
}
