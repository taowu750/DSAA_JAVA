package util.algs.visual;


import util.algs.Draw;
import util.datagen.ArrayConverter;
import util.datagen.CountingGenerator;
import util.datagen.ArrayData;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * 根据给定的数组内容或容器，绘制棒状图，需要注意的是，元素必须为数值类型。
 */
public class VisualBarChart {

    public static final String DEFAULT_NAME = "VisualBarChart";

    private Draw draw;


    public VisualBarChart(String name, Color color) {
        draw = new Draw(name);
        draw.setPenColor(color);
    }

    public VisualBarChart(String name) {
        this(name, Color.GRAY);
    }

    public VisualBarChart(Color color) {
        this(DEFAULT_NAME, color);
    }

    public VisualBarChart() {
        this(DEFAULT_NAME, Color.BLACK);
    }


    public void show(byte[] a, byte min, byte max) {
        show(draw, ArrayConverter.to(a), min, max);
    }

    public void show(short[] a, short min, short max) {
        show(draw, ArrayConverter.to(a), min, max);
    }

    public void show(int[] a, int min, int max) {
        show(draw, ArrayConverter.to(a), min, max);
    }

    public void show(long[] a, long min, long max) {
        show(draw, ArrayConverter.to(a), min, max);
    }

    public void show(float[] a, float min, float max) {
        show(draw, ArrayConverter.to(a), min, max);
    }

    public void show(double[] a, double min, double max) {
        show(draw, ArrayConverter.to(a), min, max);
    }

    public void show(Byte[] a, byte min, byte max) {
        show(draw, a, min, max);
    }

    public void show(Short[] a, short min, short max) {
        show(draw, a, min, max);
    }

    public void show(Integer[] a, int min, int max) {
        show(draw, a, min, max);
    }

    public void show(Long[] a, long min, long max) {
        show(draw, a, min, max);
    }

    public void show(Float[] a, float min, float max) {
        show(draw, a, min, max);
    }

    public void show(Double[] a, double min, double max) {
        show(draw, a, min, max);
    }

    public void clear() {
        draw.clear();
    }


    private <T extends Number> void show(Draw draw, T[] a, T min, T max) {
        draw.setXscale(0, a.length / 2 + 1);
        draw.setYscale(min.doubleValue() - 1, max.doubleValue() + 1);

        for (int i = 0; i < a.length; i++) {
            double x = 0.5 * i;
            double y = a[i].doubleValue() / 2.0;
            double rw = 0.2;
            double rh = Math.abs(a[i].doubleValue()) / 2.0;
            draw.filledRectangle(x, y, rw, rh);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        VisualBarChart barChart = new VisualBarChart("Demo");
        int size = 20;
        int[] a = ArrayConverter.to(ArrayData.array(Integer.class, new CountingGenerator.Integer(), size));
        barChart.show(a, 0, size + 1);

        TimeUnit.SECONDS.sleep(5);
        System.exit(0);
    }
}
