package algs1_fundamentals.sec2_data_abstraction;


import util.algs.StdDraw;
import util.algs.StdIn;
import util.algs.StdOut;
import util.algs.StdRandom;

import java.awt.*;

/**
 * 这个 Counter 支持加一和减一操作，它的构造函数接收两个参数N和max，其中N知道了操作的最大次数，<br/>
 * max 指定了计数器的最大绝对值。作为副作用，用图像显示每次计数器变化后的值。
 */
public class VisualCounter {

    private static final double PEN_RADIUS = 0.01;

    private String id;

    private int maxCount;
    private int max;

    private int countValue = 0;
    private int count = 0;


    public VisualCounter(String id, int maxCount, int max) {
        this.id = id;
        this.maxCount = maxCount;
        this.max = max;

        StdDraw.setXscale(0, maxCount);
        StdDraw.setYscale(-max, max);
        StdDraw.setPenRadius(PEN_RADIUS);
        StdDraw.point(0, 0);
    }


    /**
     * 当计数次数没有超过最大次数并且计数值没有超过最大值时，进行加一操作并且把结果用红色点在图上表示出来。
     *
     * @return 计数次数
     */
    public int increment() {
        if (count < maxCount) {
            if (countValue < max) {
                count++;
                countValue++;

                StdDraw.setPenColor(Color.RED);
                StdDraw.point(count, countValue);
            }
        }

        return countValue;
    }

    /**
     * 当计数次数没有超过最大次数并且计数值的绝对值没有超过最大值时，进行减一操作并且把结果用黑色点在图上表示出来。
     *
     * @return 计数次数
     */
    public int decrement() {
        if (count < maxCount) {
            if (Math.abs(countValue) < max) {
                count++;
                countValue--;

                StdDraw.setPenColor(Color.BLACK);
                StdDraw.point(count, countValue);
            }
        }

        return countValue;
    }

    /**
     * 返回当前计数值
     *
     * @return 当前计数值
     */
    public int current() {
        return countValue;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + countValue + "]";
    }


    public static void main(String[] args) {
        StdOut.print("计数最大次数：");
        int maxCount = StdIn.readInt();
        StdOut.print("计数最大值：");
        int maxValue = StdIn.readInt();

        VisualCounter counter = new VisualCounter("test", maxCount, maxValue);
        for (int i = 0; i < maxCount; i++) {
            if (StdRandom.bernoulli(0.5)) {
                StdOut.print(counter.increment() + " ");
            } else {
                StdOut.print(counter.decrement() + " ");
            }
        }
        StdOut.println("\n" + counter);
    }
}
