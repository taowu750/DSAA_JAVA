package algs4_graph.sec3_mst.src;

import util.datastructure.MyIndexPriorityQueue;
import util.datastructure.MyQueue;

/**
 * Prim 算法的即时版本。
 */
public class Prim implements MST {

    /*
    如果顶点 v 不在树中且至少含有一条边和树相连，那么 edgeTo[v] 是将
    v 和树相连的最短边，distTo[v] 为这条边的权重。
    所有这类顶点 v 都保存在一条索引优先队列中，索引 v 关联的值是
    distTo[v]。
     */
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private MyIndexPriorityQueue<Double> pq;
    private MyQueue<Edge> edges;
    private double weight;


    public Prim(SimpleWeighedGraph g) {
        edgeTo = new Edge[g.vertexNum()];
        distTo = new double[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        marked = new boolean[g.vertexNum()];
        pq = new MyIndexPriorityQueue<>(MyIndexPriorityQueue.Order.MIN, g.vertexNum());
        edges = new MyQueue<>();

        distTo[0] = 0;
        pq.offer(0, 0.0);
        while (!pq.isEmpty()) {
            // 将最近的顶点添加到树中
            visit(g, pq.poll().index());
        }

        for (int v = 0; v < edgeTo.length; v++) {
            if (edgeTo[v] != null) {
                edges.offer(edgeTo[v]);
                weight += distTo[v];
            }
        }
    }


    public static void main(String[] args) {
        MST.test(WeightedGraph.class, Prim.class);
    }


    @Override
    public Iterable<Edge> edges() {
        return edges;
    }

    @Override
    public double weight() {
        return weight;
    }


    private void visit(SimpleWeighedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);

            // 说明 w 已经在树中
            if (marked[w])
                continue;
            // 只保留和 w 最近的边
            if (Double.compare(e.weight(), distTo[w]) < 0) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.set(w, distTo[w]);
                else
                    pq.offer(w, distTo[w]);
            }
        }
    }
}
