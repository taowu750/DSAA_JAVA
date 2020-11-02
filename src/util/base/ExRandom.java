package util.base;

import java.util.Random;

/**
 * 增强型的 Random。
 */
public class ExRandom {

    private long seed;
    private Random random;


    public ExRandom() {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public ExRandom(long seed) {
        this.seed = seed;
        random = new Random(this.seed);
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }

    /**
     * 生成一个 [0, 1) 之间的 double 数字。
     *
     * @return
     */
    public double rand() {
        return random.nextDouble();
    }

    /**
     * 生成一个 [0, 1) 之间的 float 数字。
     *
     * @return
     */
    public float randF() {
        return random.nextFloat();
    }

    /**
     * 生成一个 [0, n) 之间的 int 数字
     *
     * @param n
     * @return
     */
    public int rand(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }
        return random.nextInt(n);
    }

    /**
     * 生成一个 [0, n) 之间的 long 数字
     *
     * @param n
     * @return
     */
    @SuppressWarnings("Duplicates")
    public long rand(long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }

        long r = random.nextLong();
        long m = n - 1;

        // power of two
        if ((n & m) == 0L) {
            return r & m;
        }

        long u = r >>> 1;
        while (u + m - (r = u % n) < 0L) {
            u = random.nextLong() >>> 1;
        }

        return r;
    }

    /**
     * 生成一个 [a, b) 之间的 int 数字。
     *
     * @param a
     * @param b
     * @return
     */
    public int rand(int a, int b) {
        if (a >= b) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }

        return a + rand(b - a);
    }

    /**
     * 生成一个 [a, b) 之间的 long 数字。
     *
     * @param a
     * @param b
     * @return
     */
    public long rand(long a, long b) {
        if (a >= b) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }

        return a + rand(b - a);
    }

    /**
     * 生成一个 [a, b) 之间的 float 数字。
     *
     * @param a
     * @param b
     * @return
     */
    public float rand(float a, float b) {
        if (Float.compare(a, b) >= 0) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }

        return a + randF() * (b - a);
    }

    /**
     * 生成一个 [a, b) 之间的 double 数字。
     *
     * @param a
     * @param b
     * @return
     */
    public double rand(double a, double b) {
        if (Double.compare(a, b) >= 0) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }

        return a + rand() * (b - a);
    }
}
