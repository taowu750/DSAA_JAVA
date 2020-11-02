package algs4_graph.sec1_undirected_graph.src;

/**
 * 检测图中有没有环。
 */
public class Cycle {

    private boolean[] marked;
    private boolean hasCycle;


    public Cycle(UndirectedGraph g) {
        marked = new boolean[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v])
                dfs(g, v, v);
        }
    }

   public boolean hasCycle() {
        return hasCycle;
    }


    private void dfs(UndirectedGraph g, int v, int u) {
        marked[v] = true;
        for (Integer w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w, v);
            /*
            因为是无向图，所以每个顶点会遍历所有的相邻顶点，包括上一个递归中的 v。
            所有对于无环图，当遇到已经标记过的顶点时，只可能是上一个递归中的 v。
            而有环图则不会这样。
             */
            else if (w != u)
                hasCycle = true;
        }
    }
}
