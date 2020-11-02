package algs4_graph.sec1_undirected_graph.src;

/**
 * 图接口。
 */
public interface SimpleGraph {

    int vertexNum();

    int edgeNum();

    void addEdge(int v, int w);

    boolean hasEdge(int v, int w);

    /**
     * 节点 v 的所有邻接节点。
     *
     * @param v
     * @return
     */
    Iterable<Integer> adj(int v);
}
