package util.datagen;


/**
 * 内置有多种重载过的方法，用来将基本类型包装器数组转化为其对应的基本类型数组
 *
 * @author wutao
 */
public class ArrayConverter {
    /**
     * 将Boolean数组转化为boolean数组
     *
     * @param in : 传递的Boolean数组
     * @return 转化好的boolean数组
     */
    public static boolean[] to(Boolean[] in) {
        boolean[] result = new boolean[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Boolean[] to(boolean[] in) {
        Boolean[] result = new Boolean[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    /**
     * 将Byte数组转化为byte数组
     *
     * @param in : 传递的Byte数组
     * @return 转化好的byte数组
     */
    public static byte[] to(Byte[] in) {
        byte[] result = new byte[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Byte[] to(byte[] in) {
        Byte[] result = new Byte[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    /**
     * 将Short数组转化为short数组
     *
     * @param in : 传递的Short数组
     * @return 转化好的short数组
     */
    public static short[] to(Short[] in) {
        short[] result = new short[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Short[] to(short[] in) {
        Short[] result = new Short[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    /**
     * 将Character数组转化为char数组
     *
     * @param in : 传递的Character数组
     * @return 转化好的char数组
     */
    public static char[] to(Character[] in) {
        char[] result = new char[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Character[] to(char[] in) {
        Character[] result = new Character[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    /**
     * 将Integer数组转化为int数组
     *
     * @param in : 传递的Integer数组
     * @return 转化好的int数组
     */
    public static int[] to(Integer[] in) {
        int[] result = new int[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Integer[] to(int[] in) {
        Integer[] result = new Integer[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    /**
     * 将Long数组转化为long数组
     *
     * @param in : 传递的Long数组
     * @return 转化好的long数组
     */
    public static long[] to(Long[] in) {
        long[] result = new long[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Long[] to(long[] in) {
        Long[] result = new Long[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    /**
     * 将Float数组转化为float数组
     *
     * @param in : 传递的Float数组
     * @return 转化好的float数组
     */
    public static float[] to(Float[] in) {
        float[] result = new float[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Float[] to(float[] in) {
        Float[] result = new Float[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    /**
     * 将Double数组转化为double数组
     *
     * @param in : 传递的Double数组
     * @return 转化好的double数组
     */
    public static double[] to(Double[] in) {
        double[] result = new double[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }

    public static Double[] to(double[] in) {
        Double[] result = new Double[in.length];

        for (int i = 0; i < in.length; i++) {
            result[i] = in[i];
        }

        return result;
    }
}
