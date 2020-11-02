package util.base;

/**
 * 含有 {@link Math} 类没有的数学方法。
 */
public class ExMath {

    public static final double log(double base, double x) {
        return Math.log(x) / Math.log(base);
    }

    public static final double ln(double x) {
        return log(2, x);
    }

    public static final long factorial(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("The parameter x must be greater than 0");
        }

        long result = 1;
        for (int i = 2; i < x; i++) {
            result *= i;
        }

        return result;
    }
}
