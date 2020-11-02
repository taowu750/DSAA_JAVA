package algs1_fundamentals.sec2_data_abstraction;


import util.algs.StdIn;
import util.algs.StdOut;
import util.algs.fundamentals.Point2D;

/**
 * 1.2.1<br/>
 * 使用 Point2D 类，从命令行接受一个整数 N。在单位长方形中生成 N 个随机点，然后计算两点间最短距离。
 */
public class E1_Point2DTest {

    /**
     * 随机生成n个点，计算这些点之间的最短距离
     *
     * @param n 随机生成的点的数量
     * @return 最短距离
     */
    public static double shortestDis(int n) {
        Point2D[] points = new Point2D[n];

        for (int i = 0; i < n; i++) {
            points[i] = new Point2D(Math.random(), Math.random());
            points[i].draw();
        }

        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = points[i].distanceTo(points[j]);
                if (minDistance > distance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }


    public static void main(String[] args) {
        StdOut.print("输入随机生成的点的数量：");
        double minDistance = shortestDis(StdIn.readInt());
        StdOut.println("最短距离是：" + minDistance);
    }
}
