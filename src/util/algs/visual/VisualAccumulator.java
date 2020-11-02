package util.algs.visual;


import util.algs.Draw;

/**
 * 以可视化的方式显示每次操作的花费已经均摊花费。
 */
public class VisualAccumulator {

    private double total;
    private double cost;
    private int N;

    private boolean isVisible;
    private Draw draw;
    private String name;


    public VisualAccumulator(String name, double xMin, double xMax, double yMin, double yMax, boolean isVisible) {
        total = 0.0;
        cost = 0.0;
        N = 0;
        this.isVisible = isVisible;
        this.name = name;

        draw = new Draw(name);
        draw.setXscale(xMin, xMax);
        draw.setYscale(yMin, yMax);
        draw.setPenRadius(0.005);
    }

    public VisualAccumulator(String name, double xMin, double xMax, double yMin, double yMax) {
        this(name, xMin, xMax, yMin, yMax, true);
    }

    public VisualAccumulator(double xMin, double xMax, double yMin, double yMax) {
        this("VisualAccumulator", xMin, xMax, yMin, yMax, true);
    }

    public VisualAccumulator(double xMin, double xMax, double yMin, double yMax, boolean isVisible) {
        this("VisualAccumulator", xMin, xMax, yMin, yMax, isVisible);
    }

    public VisualAccumulator(String name, double xMax, double yMax, boolean isVisible) {
        this(name, 0, xMax, 0, yMax, isVisible);
    }

    public VisualAccumulator(String name, double xMax, double yMax) {
        this(name, xMax, yMax, true);
    }

    public VisualAccumulator(double xMax, double yMax, boolean isVisible) {
        this("VisualAccumulator", xMax, yMax, isVisible);
    }

    public VisualAccumulator(double xMax, double yMax) {
        this("VisualAccumulator", xMax, yMax, true);
    }


    /**
     * 向计数器添加值，并计算均摊费用。
     *
     * @param value 花费
     */
    public void addDataValue(double value) {
        total += value;
        cost += value;
    }

    /**
     * 如果这个计数器是可以显示的，记本次操作为第 i 次，则它会在图上显示一个(i, value)的黑点，表示本次操作的花费。
     */
    public void showSingleCost() {
        N++;
        if (isVisible) {
            draw.setPenColor(Draw.BLACK);
            draw.point(N, cost);
        }
        cost = 0.0;
    }

    /**
     * 如果这个计数器是可以显示的，显示一个(i, total / i)的红点，表示均摊花费，i 为第 i 次操作。
     */
    public void showAverageCost() {
        if (N == 0) {
            N = 1;
        }
        if (isVisible) {
            draw.setPenColor(Draw.RED);
            draw.point(N, total / N);
        }
    }

    /**
     * 如果这个计数器是可以显示的，记本次操作为第 i 次，则它会在图上显示一个(i, value)的黑点，表示本次操作的花费；<br/>
     * 还会显示一个(i, total / i)的红点，表示均摊花费。
     */
    public void showCost() {
        N++;
        if (isVisible) {
            draw.setPenColor(Draw.BLACK);
            draw.point(N, cost);
            draw.setPenColor(Draw.RED);
            draw.point(N, total / N);
        }
        cost = 0.0;
    }

    public void clearDraw() {
        draw.clear();
    }

    /**
     * 返回均摊操作的花费。
     *
     * @return 均摊操作的花费
     */
    public double mean() {
        return total / N;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean getVisible() {
        return isVisible;
    }

    @Override
    public String toString() {
        return name + ": Mean (" + N + " values)=" + String.format("%7.5f", mean());
    }
}
