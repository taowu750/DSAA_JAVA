package algs4_graph.sec4_shortest_path.src;

/**
 * 最短路径 API。
 */
public interface SP {

    double distTo(int v);

    boolean hasPathTo(int v);

    Iterable<DirectedEdge> pathTo(int v);
}
