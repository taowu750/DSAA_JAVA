package algs4_graph.sec3_mst.src;

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

    @Override
    public int compareTo(Edge o) {
        return Double.compare(weight, o.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d:%.2f", v, w, weight);
    }
}
