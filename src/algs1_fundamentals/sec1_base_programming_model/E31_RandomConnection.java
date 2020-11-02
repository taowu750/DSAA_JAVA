package algs1_fundamentals.sec1_base_programming_model;


import util.algs.StdDraw;
import util.algs.StdIn;
import util.algs.StdRandom;
import util.algs.fundamentals.Point2D;

/**
 * 从命令行接受一个整数 N 和 double 值 p（0 到 1 之间）作为参数，在一个圆
 * 上画出大小为 0.05 且间距相等的 N 个点，然后将每对点用灰线连接。
 */
public class E31_RandomConnection {

    public static void randomConnect() {
        int N;
        double p;

        System.out.println("请输入 N 和 p(0 到 1 之间)：");
        N = StdIn.readInt();
        p = StdIn.readDouble();

        StdDraw.ellipse(0.5, 0.5, 0.5, 0.5);

        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.RED);
        // Math 的三角函数参数单位为弧度，所以要把角度化成弧度
        double angel = Math.toRadians(360. / N);
        Point2D[] point2DS = new Point2D[N];
        for (int i = 0; i < N; i++) {
            point2DS[i] = new Point2D(0.5 * Math.cos(angel * i) + 0.5,
                    0.5 * Math.sin(angel * i) + 0.5);
            point2DS[i].draw();
        }

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.GRAY);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (StdRandom.bernoulli(p)) {
                    StdDraw.line(point2DS[i].x(), point2DS[i].y(),
                            point2DS[j].x(), point2DS[j].y());
                }
            }
        }
    }


    public static void main(String[] args) {
        randomConnect();
    }
}
