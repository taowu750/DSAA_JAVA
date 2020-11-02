package algs1_fundamentals.sec1_base_programming_model;


import util.algs.StdOut;
import util.datagen.Counter;
import util.io.FormatPrint;

import java.util.Random;

/**
 * 1.1.5<br/>
 * 编写一段程序，如果double型变量x和y都严格位于0和1之间则打印true，否则打印false
 */
public class E5_CheckDouble {

    private static final double ZERO_BORDER = 1e-15;

    private static final int        D1     = 10;
    private static final int        D2     = 2;
    private static final int        LIMIT  = 1;
    private static final Random     random = new Random();
    private static final double[][] DATA   = new double[D1][D2];

    static {
        for (int i = 0; i < D1; i++) {
            for (int j = 0; j < D2; j++) {
                DATA[i][j] = random.nextDouble();
            }
        }
    }

    public static boolean checkDouble(double x, double y) {
        return checkDouble(x) && checkDouble(y);

    }

    public static boolean checkDouble(double d) {
        return d > ZERO_BORDER && d < 1.0;
    }


    public static void main(String[] args) {
        FormatPrint.printTitle("检测在0或1之间的数");
        FormatPrint.newLine();
        Counter counter = new Counter(1);
        for (double[] xy : DATA) {
            FormatPrint.printInstruction("数据" + counter.next() + ": x=" + xy[0] + ", y=" + xy[1]);
            StdOut.println("result=" + checkDouble(xy[0], xy[1]));
        }
    }
}
