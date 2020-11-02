package algs4_graph.sec4_shortest_path.src;

/**
 * 加权有向图的边。
 */
public class DirectedEdge {

    private int v;
    private int w;
    private double weight;


    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }


    public double weight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    @Override
    public String toString() {
        return String.format("%d-%d: %.2f", v, w, weight);
    }
}
