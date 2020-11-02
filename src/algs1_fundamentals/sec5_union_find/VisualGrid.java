package algs1_fundamentals.sec5_union_find;


import util.algs.Draw;
import util.algs.StdRandom;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 一个 N*N 的可视化网格，连接和点的颜色可以改变。<br/>
 * 当两点之间存在一条边时，称这两点之间建立了一条连接。<br/>
 * 可以向网格中添加点或连接。
 */
public class VisualGrid {

    private boolean[][] points;
    private List<Connection> connections;
    private int width;
    private int height;
    private String name;

    private Draw draw;


    public VisualGrid(String name, int width, int height) {
        this.width = width;
        this.height = height;
        this.name = name;

        points = new boolean[height][width];
        connections = new ArrayList<>(height * (width - 1) + width * (height - 1));
        draw = new Draw(name);
        draw.setXscale(0, width + 5);
        draw.setYscale(0, height + 5);
    }

    public VisualGrid(int width, int height) {
        this("VisualGrid", width, height);
    }


    public boolean addPoint(int x, int y) {
        checkXY(x, y);

        if (!points[x][y]) {
            points[x][y] = true;

            return true;
        }

        return false;
    }

    public boolean addConnection(int x1, int y1, int x2, int y2) {
        checkXY(x1, y1);
        checkXY(x2, y2);

        Connection connection = new Connection(x1, y1, x2, y2);
        if (!connections.contains(connection)) {
            connections.add(connection);

            return true;
        }

        return false;
    }

    public void show(Color pointColor, Color connectionColor) {
        draw.setPenColor(pointColor);
        draw.setPenRadius(0.01);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (points[i][j]) {
                    draw.point(i + 3, j + 3);
                }
            }
        }
        for (Connection connection : connections) {
            draw.point(connection.p1.x + 3, connection.p1.y + 3);
            draw.point(connection.p2.x + 3, connection.p2.y + 3);
        }

        draw.setPenColor(connectionColor);
        draw.setPenRadius(0.005);
        for (Connection connection : connections) {
            draw.line(connection.p1.x + 3, connection.p1.y + 3, connection.p2.x + 3, connection.p2.y + 3);
        }
    }

    public void show() {
        show(Color.RED, Color.BLUE);
    }


    @Override
    public String toString() {
        return name + ": width=" + width + ", height=" + height;
    }


    private void checkX(int x) {
        if (x < 0 || x >= width) {
            throw new IllegalArgumentException("horizontal ordinate cannot less than 0 or greater than or equal to " +
                    "width=" + width);
        }
    }

    private void checkY(int y) {
        if (y < 0 || y >= height) {
            throw new IllegalArgumentException("vertical ordinate cannot less than 0 or greater than or equal to " +
                    "height=" + height);
        }
    }

    private void checkXY(int x, int y) {
        checkX(x);
        checkY(y);
    }


    private static class Point {
        int x;
        int y;


        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o instanceof Point) {
                Point p = (Point) o;

                return Objects.equals(this.x, p.x) && Objects.equals(this.y, p.y);
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Connection {
        Point p1;
        Point p2;


        private Connection(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        private Connection(int x1, int y1, int x2, int y2) {
            p1 = new Point(x1, y1);
            p2 = new Point(x2, y2);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o instanceof Connection) {
                Connection c = (Connection) o;

                return Objects.equals(this.p1, c.p1) && Objects.equals(this.p2, c.p2);
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2);
        }
    }


    public static void main(String[] args) {
        int width = 10;
        int height = 10;
        int pointCount = 10;
        int connectionCount = 10;

        VisualGrid grid = new VisualGrid(width, height);
        for (int i = 0; i < pointCount; i++) {
            grid.addPoint(StdRandom.uniform(width), StdRandom.uniform(height));
        }
        for (int i = 0; i < connectionCount; i++) {
            grid.addConnection(StdRandom.uniform(width), StdRandom.uniform(height), StdRandom.uniform(width),
                    StdRandom.uniform(height));
        }
        grid.show();
    }
}
