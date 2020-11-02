package algs4_graph.sec2_digraph.src;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import util.datastructure.MyStack;

/**
 * 判断有向图中是否有环。
 */
public class DigraphCycle {

    private boolean[] marked;
    private int[] edgeTo;
    // 有向环中的所有顶点（如果存在）
    private MyStack<Integer> cycle;
    // 递归调用栈上的所有顶点
    private boolean[] onStack;


    public DigraphCycle(SimpleGraph g) {
        onStack = new boolean[g.vertexNum()];
        edgeTo = new int[g.vertexNum()];
        marked = new boolean[g.vertexNum()];

        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v])
                dfs(g, v);
        }
    }


    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }


    private void dfs(SimpleGraph g, int v) {
        /*
        一旦我们找到了一条边 v->w 而 w 已经存在栈中，就找到了一个环。
        因为栈表示的是一条从 w 到 v 的有向路径，而 v->w 正好补全了这个环。
         */
        onStack[v] = true;
        marked[v] = true;
        for (Integer w : g.adj(v)) {
            if (hasCycle())
                return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            } else if (onStack[w]) {
                cycle = new MyStack<>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }
}
