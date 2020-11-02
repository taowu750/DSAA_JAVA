package algs4_graph.sec2_digraph.src;

/**
 * 传递闭包。用来解决顶点
 */
public class TransitiveClosure {

    private DigraphDFS[] all;


    public TransitiveClosure(Digraph g) {
        all = new DigraphDFS[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            all[v] = new DigraphDFS(g, v);
        }
    }


    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }
}
