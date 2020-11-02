package algs1_fundamentals.sec2_data_abstraction;

import util.algs.StdIn;
import util.algs.fundamentals.Counter;
import util.algs.fundamentals.Interval1D;
import util.algs.fundamentals.Interval2D;
import util.algs.fundamentals.Point2D;

/**
 * 1.2 节数据抽象中的代码
 */
public class Src_DataAbstraction {

    /**
     * 测试 Interval2D 类
     */
    public static void interval2DDemo() {
        // 书中参数：.2 .5 .5 .6 10000
        System.out.print("请输入 xlo, xhi, ylo, yhi, T：");
        double xlo = StdIn.readDouble();
        double xhi = StdIn.readDouble();
        double ylo = StdIn.readDouble();
        double yhi = StdIn.readDouble();
        int T = StdIn.readInt();

        Interval1D xInterval = new Interval1D(xlo, xhi);
        Interval1D yInterval = new Interval1D(ylo, yhi);
        Interval2D box = new Interval2D(xInterval, yInterval);
        box.draw();

        Counter counter = new Counter("hits");
        for (int i = 0; i < T; i++) {
            double x = Math.random();
            double y = Math.random();
            Point2D p = new Point2D(x, y);
            if (box.contains(p)) {
                counter.increment();
            } else {
                p.draw();
            }
        }

        System.out.println(counter);
        System.out.println(box.area());
    }

    public static void main(String[] args) {
        interval2DDemo();
    }
}
