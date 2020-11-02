package algs1_fundamentals.sec4_analysis_of_algorithms;


import util.algs.Draw;
import util.algs.StdOut;
import util.algs.StdRandom;
import util.test.SpeedTester;

/**
 * 1.4.3<br/>
 * 修改 DoublingTest，使用 StdDraw 产生类似于正文中的标准图像和对数图像，根据需要调整比例<br/>
 * 使图像总能充满窗口大部分区域。
 */
public class E3_DoublingTest {

    public static double timeTrial(int N) {
        int MAX = 1000000;
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniform(-MAX, MAX);
        }

        return SpeedTester.testRunTime(() -> Src_AnalysisOfAlgorithms.threeSum(a));
    }

    public static void main(String[] args) {
        int MAX_N = 4000;
        Draw stdImage = new Draw("StdImage");
        Draw logImage = new Draw("LogImage");
        stdImage.setXscale(0, MAX_N);
        stdImage.setYscale(0, 30);
        stdImage.setPenRadius(0.01);
        logImage.setXscale(0, MAX_N);
        logImage.setYscale(0, Math.log(30));
        logImage.setPenRadius(0.01);

        for (int N = 250; N <= MAX_N; N += N) {
            double time = timeTrial(N);
            StdOut.printf("%7d %5.1f\n", N, time);
            stdImage.point(N, time);
            logImage.point(Math.log(N), Math.log(time));
        }
    }
}
