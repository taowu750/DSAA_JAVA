package algs4_graph.sec3_mst.src;

import algs1_fundamentals.sec5_union_find.src.UnionFindWeighted;
import util.datastructure.MyPriorityQueue;
import util.datastructure.MyQueue;

/**
 * Kruskal 算法最小生成树。
 * 和Prim算法不同的是，Prim算法从局部出发，逐步扩展到全局；Kruskal算法总览全局，从其中找到最优解。
 * Prim算法每一步都为树添加一条边，Kruskal会连接一片森林中的两棵树。
 */
public class Kruskal implements MST {

    private MyQueue<Edge> mst;
    private double weight;


    public Kruskal(SimpleWeighedGraph g) {
        mst = new MyQueue<>();
        MyPriorityQueue<Edge> pq = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN);
        for (Edge edge : g.edges()) {
            pq.offer(edge);
        }
        UnionFindWeighted uf = new UnionFindWeighted(g.vertexNum());

        while (!pq.isEmpty() && mst.size() < g.vertexNum() - 1) {
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);

            if (uf.connected(v, w))
                continue;
            uf.union(v, w);
            mst.offer(e);
            weight += e.weight();
        }
    }


    public static void main(String[] args) {
        MST.test(WeightedGraph.class, Kruskal.class);
    }


    @Override
    public Iterable<Edge> edges() {
        return mst;
    }

    @Override
    public double weight() {
        return weight;
    }
}
