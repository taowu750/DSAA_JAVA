package algs4_graph.sec3_mst;

import algs1_fundamentals.sec5_union_find.src.UnionFindWeighted;
import algs4_graph.sec3_mst.src.Edge;
import algs4_graph.sec3_mst.src.SimpleWeighedGraph;
import util.datastructure.MyIndexPriorityQueue;
import util.datastructure.MyPriorityQueue;
import util.datastructure.MyQueue;

/**
 * 最小生成森林。开发新版本的 Prim 算法和 Kruskal 算法来计算一幅加权图
 * 的最小生成森林。图不一定是连通的，使用连通分量的方法找到每个连通分量
 * 的最小生成树。
 */
public class E22_MSF {

    public static void main(String[] args) {

    }
}


@SuppressWarnings("unchecked")
class PrimMSF {

    private CC cc;
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private MyIndexPriorityQueue<Double> pq;
    private MyQueue<Edge>[] msf;
    private double[] weights;


    public PrimMSF(SimpleWeighedGraph g) {
        cc = new CC(g);
        edgeTo = new Edge[g.vertexNum()];
        distTo = new double[g.vertexNum()];
        for (int i = 0; i < distTo.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        pq = new MyIndexPriorityQueue<>(g.vertexNum());
        msf = new MyQueue[cc.count()];
        for (int i = 0; i < msf.length; i++) {
            msf[i] = new MyQueue<>();
        }
        weights = new double[cc.count()];

        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v]) {
                distTo[v] = 0;
                pq.offer(v, 0.);
                while (!pq.isEmpty())
                    visit(g, pq.poll().index());
            }
        }

        for (int v = 0; v < edgeTo.length; v++) {
            if (edgeTo != null) {
                msf[cc.id(v)].offer(edgeTo[v]);
                weights[cc.id(v)] += distTo[v];
            }
        }
    }


    public Iterable<Edge> edges(int id) {
        return msf[id];
    }

    public double weight(int id) {
        return weights[id];
    }

    public int count() {
        return cc.count();
    }


    private void visit(SimpleWeighedGraph g,  int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);

            if (marked[w])
                continue;
            if (Double.compare(e.weight(), distTo[w]) < 0) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.set(w, e.weight());
                else
                    pq.offer(w, e.weight());
            }
        }
    }
}

@SuppressWarnings("unchecked")
class KruskalMSF {

    private CC cc;
    private MyQueue<Edge>[] msf;
    private double[] weights;


    public KruskalMSF(SimpleWeighedGraph g) {
        cc = new CC(g);
        msf = new MyQueue[cc.count()];
        for (int i = 0; i < msf.length; i++) {
            msf[i] = new MyQueue<>();
        }
        MyPriorityQueue<Edge> pq = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN);
        for (Edge e : g.edges()) {
            pq.offer(e);
        }
        UnionFindWeighted uf = new UnionFindWeighted(g.vertexNum());

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);

            if (uf.connected(v, w))
                continue;
            uf.union(v, w);
            msf[cc.id(v)].offer(e);
            weights[cc.id(v)] += e.weight();
        }
    }


    public Iterable<Edge> edges(int id) {
        return msf[id];
    }

    public double weight(int id) {
        return weights[id];
    }

    public int count() {
        return cc.count();
    }
}

class CC {

    private boolean[] marked;
    private int[] id;
    private int count;


    public CC(SimpleWeighedGraph g) {
        marked = new boolean[g.vertexNum()];
        id = new int[g.vertexNum()];

        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v]) {
                dfs(g, v);
                count++;
            }
        }
    }


    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }


    private void dfs(SimpleWeighedGraph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (Edge e : g.adj(v)) {
            if (!marked[e.other(v)])
                dfs(g, e.other(v));
        }
    }
}
