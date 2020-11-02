package algs2_sort.sec5_application;

import util.algs.StdDraw;
import util.algs.fundamentals.Point2D;
import util.datagen.ArrayData;
import util.datagen.RandomGenerator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>
 * 简单多边形。给定平面上的 N 个点，用它们画出一个多边形。
 * </p>
 * <p>
 * 提示：找到 y 坐标最小的点 p，再有多个最小 y 坐标的点时取 x 坐标最小者，
 * 然后其它点按照以 p 为原点的幅角大小的顺序依次连接起来。
 * </p>
 */
public class E26_SimplePolygon {

    public static void polygon(Point2D[] ps) {
        Point2D p = ps[0];
        for (int i = 1; i < ps.length; i++) {
            int cmp = Double.compare(ps[i].y(), p.y());
            if (cmp < 0 || cmp == 0 && Double.compare(ps[i].x(), p.x()) < 0)
                p = ps[i];
        }

        Point2D finalP = p;
        Arrays.sort(ps, Comparator.comparingDouble(poi -> poi.angleTo(finalP)));

        StdDraw.setPenRadius(.015);
        StdDraw.setPenColor(StdDraw.RED);
        p.draw();
        Point2D last = p;
        for (Point2D poi : ps) {
            if (poi != p) {
                StdDraw.setPenRadius(.008);
                StdDraw.setPenColor(StdDraw.BLUE);
                poi.draw();
                StdDraw.setPenRadius(.004);
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.line(poi.x(), poi.y(), last.x(), last.y());
                last = poi;
            }
        }
        StdDraw.line(last.x(), last.y(), p.x(), p.y());
    }

    public static void main(String[] args) {
        int N = 10;
        Double[] coors = ArrayData.array(Double.class,
                new RandomGenerator.Double(0., 1.), N * 2);
        Point2D[] ps = new Point2D[N];
        for (int i = 0, j = 0; i < coors.length; i += 2, j++) {
            ps[j] = new Point2D(coors[i], coors[i + 1]);
        }
        polygon(ps);
    }
}
