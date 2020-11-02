package algs4_graph.sec4_shortest_path.src;

import util.algs.StdIn;
import util.datastructure.MyStack;
import util.io.AlgsDataIO;

/**
 * <p>
 * 无环加权有向图的最短路径算法。综合了拓扑排序和 Dijkstra 算法。
 * 先进行拓扑排序，然后按照拓扑排序的顺序放松顶点，这样每条边 v->w
 * 只会被放松一次。
 * </p>
 * <p>
 * 使用此算法也可以很轻松的解决无环加权有向图的最长路径问题。只需要
 * 将 distTo 的初值改为 Double.NEGATIVE_INFINITY 并改变 relax()
 * 方法中不等式的方向即可。
 * </p>
 */
public class AcyclicSP implements SP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;


    public AcyclicSP(WeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.vertexNum()];
        distTo = new double[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0.;
        WeightedTopological top = new WeightedTopological(g);
        for (Integer v : top.order()) {
            relax(g, v);
        }
    }


    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        WeightedDigraph g = new WeightedDigraph(AlgsDataIO.openTinyEWDAG());
        System.out.print("请输入源点：");
        // 5
        int s = StdIn.readInt();
        SP sp = new AcyclicSP(g, s);

        System.out.println("最短路径：");
        for (int v = 0; v < g.vertexNum(); v++) {
            System.out.print(s + " to " + v);
            System.out.printf(" (%4.2f): ", sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v)) {
                System.out.print(e + "   ");
            }
            System.out.println();
        }
    }


    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        MyStack<DirectedEdge> path = new MyStack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);

        return path;
    }


    @SuppressWarnings("Duplicates")
    private void relax(WeightedDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (Double.compare(distTo[w], distTo[v] + e.weight()) > 0) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }
}


class WeightedTopological {

    private boolean[] marked;
    private MyStack<Integer> edges;


    public WeightedTopological(WeightedDigraph g) {
        marked = new boolean[g.vertexNum()];
        edges = new MyStack<>();

        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v])
                dfs(g, v);
        }
    }


    public Iterable<Integer> order() {
        return edges;
    }


    private void dfs(WeightedDigraph g, int v) {
        marked[v] = true;
        for (DirectedEdge e : g.adj(v)) {
            if (!marked[e.to()]) {
                dfs(g, e.to());
            }
        }
        edges.push(v);
    }
}
