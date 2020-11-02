package algs4_graph.sec4_shortest_path.src;

import util.algs.StdIn;
import util.datastructure.MyStack;
import util.io.AlgsDataIO;

/**
 * 无环加权有向图最长路径实现。
 */
public class AcyclicLP implements SP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;


    public AcyclicLP(WeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.vertexNum()];
        distTo = new double[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            distTo[v] = Double.NEGATIVE_INFINITY;
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
        SP sp = new AcyclicLP(g, s);

        System.out.println("最长路径：");
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
        return distTo[v] > Double.NEGATIVE_INFINITY;
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
            if (distTo[w] < distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }
}
