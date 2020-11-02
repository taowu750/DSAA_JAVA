package algs4_graph.sec3_mst.src;

/**
 * 简单的加权图
 */
public interface SimpleWeighedGraph {

    int vertexNum();

    int edgeNum();

    void addEdge(Edge edge);

    Iterable<Edge> adj(int v);

    Iterable<Edge> edges();
}
