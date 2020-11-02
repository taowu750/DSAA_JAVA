package algs4_graph.sec4_shortest_path.src;


import util.datastructure.MyStack;

/**
 * 查找 {@link WeightedDigraph} 中是否有环。
 */
public class WeightedDigraphCycle {

    private boolean[] marked;
    private boolean[] onStack;
    private DirectedEdge[] edgeTo;
    private MyStack<DirectedEdge> cycle;


    public WeightedDigraphCycle(WeightedDigraph g) {
        marked = new boolean[g.vertexNum()];
        onStack = new boolean[g.vertexNum()];
        edgeTo = new DirectedEdge[g.vertexNum()];
        cycle = new MyStack<>();

        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v])
                dfs(g, v);
        }
    }


    public boolean hasCycle() {
        return !cycle.isEmpty();
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }


    private void dfs(WeightedDigraph g, int v) {
        if (hasCycle())
            return;
        marked[v] = true;
        onStack[v] = true;
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (!marked[w]) {
                edgeTo[w] = e;
                dfs(g, w);
            } else if (onStack[w]) {
                cycle.push(e);
                DirectedEdge x = edgeTo[e.from()];
                for (; x != null; x = edgeTo[x.from()])
                    cycle.push(x);
            }
        }
        onStack[v] = false;
    }
}
