package algs4_graph.sec1_undirected_graph.src;

/**
 * 双色问题。能够用两种颜色将图的所有顶点着色，使得任意一条边的两个顶点
 * 的颜色都不相同吗？这个问题也等价于：这是一幅二分图吗？
 */
public class TwoColor {

    private boolean[] marked;
    private boolean[] color;
    private boolean isTwoColorable = true;


    public TwoColor(UndirectedGraph g) {
        marked = new boolean[g.vertexNum()];
        color = new boolean[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v])
                dfs(g, v);
        }
    }


    public boolean isBipartie() {
        return isTwoColorable;
    }


    private void dfs(UndirectedGraph g, int v) {
        marked[v] = true;
        for (Integer w : g.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(g, w);
            } else if (color[w] == color[v])
                isTwoColorable = false;
        }
    }
}
