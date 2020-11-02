package algs1_fundamentals.sec2_data_abstraction;


import util.algs.StdIn;
import util.algs.StdOut;
import util.algs.fundamentals.Interval1D;

/**
 * 1.2.2<br/>
 * Interval1D 的使用。从命令行接受一个整数N。从标准输入中读取N个间隔（每个间隔由一对 double 值定义），<br/>
 * 并打印所有相交的间隔对。
 */
public class E2_Interval1DTest {

    public static void printIntersecting(Interval1D[] intervals) {
        for (int i = 0; i < intervals.length; i++) {

            for (int j = i + 1; j < intervals.length; j++) {
                if (intervals[i].intersects(intervals[j])) {
                    StdOut.println(intervals[i] + " 相交于 " + intervals[j]);
                }
            }
        }
    }


    public static void main(String[] args) {
        StdOut.print("请输入间隔数量：");
        int n = StdIn.readInt();
        StdOut.println("请输入" + n + "个间隔的起点和终点：");
        Interval1D[] intervals = new Interval1D[n];
        for (int i = 0; i < n; i++) {
            intervals[i] = new Interval1D(StdIn.readDouble(), StdIn.readDouble());
        }
        printIntersecting(intervals);
    }
}
