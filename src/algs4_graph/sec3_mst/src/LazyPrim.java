package algs4_graph.sec3_mst.src;

import util.datastructure.MyPriorityQueue;
import util.datastructure.MyQueue;

import java.util.Queue;

/**
 * Prim 算法实现最小生成树。
 * 新加入树中的顶点与其他已经在树中顶点之间边都失效了。
 * 这种延时实现会保留在优先队列中失效的边。
 */
public class LazyPrim implements MST {

    // 最小生成树的顶点
    private boolean[] marked;
    // 最小生成树的边
    private Queue<Edge> mst;
    // 横切边（包括失效的边）
    private MyPriorityQueue<Edge> pq;
    private double weight;


    public LazyPrim(SimpleWeighedGraph g) {
        marked = new boolean[g.vertexNum()];
        mst = new MyQueue<>();
        pq = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN);

        visit(g, 0);
        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);

            if (marked[v] && marked[w])
                continue;
            mst.offer(e);
            weight += e.weight();
            if (!marked[v])
                visit(g, v);
            if (!marked[w])
                visit(g, w);
        }
    }


    @Override
    public Iterable<Edge> edges() {
        return mst;
    }

    @Override
    public double weight() {
        return weight;
    }


    public static void main(String[] args) {
        MST.test(WeightedGraph.class, LazyPrim.class);
    }


    private void visit(SimpleWeighedGraph g, int v) {
        // 标记顶点 v 并将所有连接 v 和未标记顶点的边加入 pq
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            if (!marked[e.other(v)])
                pq.offer(e);
        }
    }
}
