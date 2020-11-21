package algs4_graph.sec4_shortest_path.src;

import util.algs.StdIn;
import util.datastructure.MyIndexPriorityQueue;
import util.datastructure.MyStack;
import util.io.AlgsDataIO;

/**
 * Dijkstra 最短路径算法。它与 Prim 算法很像，不同之处在于 Prim 算法
 * 每次添加的都是离树最近的顶点，Dijkstra 算法每次添加的都是离起点最近
 * 的顶点。
 * Dijkstra 算法无法处理负权值。
 */
public class Dijkstra implements SP {

    private DirectedEdge[] edgeTo;
    /*
    distTo 中为从 s 到 v 的路径长度
     */
    private double[] distTo;
    private MyIndexPriorityQueue<Double> pq;


    public Dijkstra(WeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.vertexNum()];
        distTo = new double[g.vertexNum()];
        pq = new MyIndexPriorityQueue<>(MyIndexPriorityQueue.Order.MIN, g.vertexNum());

        for (int v = 0; v < g.vertexNum(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[s] = 0;
        pq.offer(s,0.);
        while (!pq.isEmpty())
            relax(g, pq.poll().index());
    }


    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        WeightedDigraph g = new WeightedDigraph(AlgsDataIO.openTinyEWD());
        System.out.print("请输入源点：");
        // 0
        int s = StdIn.readInt();
        SP sp = new Dijkstra(g, s);

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
                if (pq.contains(w))
                    pq.set(w, distTo[w]);
                else
                    pq.offer(w, distTo[w]);
            }
        }
    }
}
