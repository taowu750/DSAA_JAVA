package algs1_fundamentals.sec2_data_abstraction;


import util.algs.StdIn;
import util.algs.StdOut;
import util.algs.StdRandom;
import util.algs.fundamentals.Interval1D;
import util.algs.fundamentals.Interval2D;
import util.algs.fundamentals.Point2D;

/**
 * 1.2.3<br/>
 * Interval2D 的用例。从命令行接受参数N、min 和 max。生成N个随机的2D间隔，其宽和高均匀的分布在单位正方形中的<br/>
 * min 和 max 之间。用 StdDraw 画出他们并打印出相交的间隔对数量以及有包含关系的间隔对数量。
 */
public class E3_Interval2DTest {

    /**
     * 打印相交或者包含的间隔对数量
     *
     * @param n   间隔对数量
     * @param min 最小的宽或高的长度
     * @param max 最大的宽或高的长度
     */
    public static void printIntersectingAndContain(int n, double min, double max) {
        Interval2DWithVertex[] is = new Interval2DWithVertex[n];
        for (int i = 0; i < n; i++) {
            double width  = StdRandom.uniform(min, max);
            double height = StdRandom.uniform(min, max);
            double xStart = StdRandom.uniform();
            double yStart = StdRandom.uniform();
            Interval2D interval = new Interval2D(new Interval1D(xStart, xStart + width), new Interval1D(yStart, yStart +
                    height));
            interval.draw();
            is[i] = new Interval2DWithVertex(interval, new Point2D(xStart, yStart + height), new Point2D(xStart +
                    width, yStart));
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (is[i].interval2D.intersects(is[j].interval2D)) {
                    StdOut.println(is[i].interval2D + " 相交于 " + is[j].interval2D);
                }
                if (is[i].contains(is[j])) {
                    StdOut.println(is[i].interval2D + " 包含 " + is[j].interval2D);
                }
            }
        }
    }


    public static void main(String[] args) {
        StdOut.print("请输入间隔数：");
        int n = StdIn.readInt();
        StdOut.print("请输入最小长度：");
        double min = StdIn.readDouble();
        StdOut.print("请输入最大长度：");
        double max = StdIn.readDouble();

        printIntersectingAndContain(n, min, max);
    }
}

class Interval2DWithVertex {
    Interval2D interval2D;
    Point2D    leftTop;
    Point2D    rightDown;


    public Interval2DWithVertex(Interval2D interval2D, Point2D leftTop, Point2D rightDown) {
        this.interval2D = interval2D;
        this.leftTop = leftTop;
        this.rightDown = rightDown;
    }


    public boolean contains(Interval2DWithVertex i) {
        if (i != null) {
            if (leftTop.x() >= i.leftTop.x() || leftTop.y() <= i.leftTop.y()) {
                return false;
            }
            if (rightDown.x() <= i.rightDown.x() || rightDown.y() >= i.rightDown.y()) {
                return false;
            }

            return true;
        } else {
            throw new IllegalArgumentException("parameter cannot be null!");
        }
    }
}
