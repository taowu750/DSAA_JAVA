package algs4_graph.sec3_mst.src;

import java.util.Objects;

/**
 * 加权无向图的边。
 */
public class Edge implements Comparable<Edge> {

    private int v;
    private int w;
    private double weight;


    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }


    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new IllegalArgumentException("Inconsistent edge!");
    }

    public int other() {
        return w;
    }

    // 为了删除边和Hash
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Edge))
            return false;
        Edge other = (Edge) obj;

        return (this.v == other.v && this.w == other.w) ||
                (this.v == other.w && this.w == other.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.v, this.w);
    }

    // 为了优先队列。由于 equals 和 compareTo 不匹配，将不满足 equals 的要求
    @Override
    public int compareTo(Edge o) {
        return Double.compare(weight, o.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d:%.2f", v, w, weight);
    }
}
