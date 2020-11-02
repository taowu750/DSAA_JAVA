package algs1_fundamentals.sec1_base_programming_model;


import util.algs.StdDraw;
import util.algs.StdIn;

/**
 * 1.1.32<br/>
 * 假设标准输入流中有含有一系列double值。编写一段程序，从命令行接受一个整数N，和两个double值l和r。<br/>
 * 将(l, r)分成N段并使用StdDraw画出输入流中的值落入每段数量的直方图。
 */
public class E32_Histogram {

    public static void drawHistogram(double[] data, double l, double r, int n) {
        double segmentLength = (r - l) / n;
        int[] counts = new int[n];
        int max = 0;

        for (double d : data) {
            double place = d - l;
            for (int i = 0; i < n; i++) {
                if (place <= (i + 1) * segmentLength && place < i * segmentLength) {
                    counts[i] += 1;
                    if (max < counts[i]) {
                        max = counts[i];
                    }
                    break;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            double x = (i * 1.0 + 0.5) / n;
            double y = counts[i] / (max * 2.0);
            double rw = 0.4 / n;
            StdDraw.filledRectangle(x, y, rw, y);
        }
    }


    public static void main(String[] args) {
        System.out.print("请输入浮点数数量：");
        int count = StdIn.readInt();
        System.out.print("请输入所有浮点数：");
        double[] data = new double[count];
        for (int i = 0; i < count; i++) {
            data[i] = StdIn.readDouble();
        }
        System.out.print("请输入 n, l, r：");
        int n = StdIn.readInt();
        double l = StdIn.readDouble();
        double r = StdIn.readDouble();

        drawHistogram(data, l, r, n);
    }
}
