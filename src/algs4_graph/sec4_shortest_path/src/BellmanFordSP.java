package algs4_graph.sec4_shortest_path.src;


import util.algs.In;
import util.algs.StdIn;
import util.datastructure.MyQueue;
import util.datastructure.MyStack;
import util.io.AlgsDataIO;

import java.util.Arrays;
import java.util.Queue;

/**
 * <p>
 * Bellman-Ford 算法，用于寻找有负值边的最短路径。我们将 dist[s] 设为 0，
 * 其他设为无穷大，以任意顺序放松有向图的所有边，重复 V 轮。
 * <pre>
 *     for (int pass = 0; pass < g.vertexNum(); pass++)
 *         for (int v = 0; v < g.vertexNum(); v++)
 *             relax(g, v);
 * </pre>
 * 其实，根据经验我们知道任意一轮的许多边的放松都不会成功，只有上一轮中的 distTo[] 值发生变化的
 * 顶点指出的边才能够改变其他 distTo[] 元素的值。我们使用队列记录上一轮中改变的顶点，这样就能够
 * 优化性能。
 * </p>
 * <p>
 * 它的应用需要满足以下条件：
 * <li>找出具有负权重的环</li>
 * <li>给定顶点 s，从 s 无法到达任何负权重的环。</li>
 * 负权重的环就是环中所有边权重之和为负值
 * </p>
 * <p>
 * 如果不存在从 s 可达的负权重环，算法在进行了 V - 1 轮放松操作之后结束（因为所有最短路径含有的边数
 * 都小于 V - 1）。如果存在从 s 可达的负权重环，那么队列永远不可能为空。在第 V 轮放松之后，edgeTo[]
 * 数组必然会包含一条含有一个环的路径且该环的权重是负的
 * </p>
 */
public class BellmanFordSP implements SP {

    private double[] distTo;
    private DirectedEdge[] edgeTo;
    // 正在被放松的顶点
    private Queue<Integer> queue;
    // 该顶点是否存在队列中。
    private boolean[] onQ;
    // relax() 调用次数
    private int cost;
    // 负权重的环。
    private Iterable<DirectedEdge> cycle;


    public BellmanFordSP(WeightedDigraph g, int s) {
        distTo = new double[g.vertexNum()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        edgeTo = new DirectedEdge[g.vertexNum()];
        onQ = new boolean[g.vertexNum()];
        queue = new MyQueue<>();

        distTo[s] = 0.;
        queue.offer(s);
        onQ[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.poll();
            onQ[v] = false;
            relax(g, v);
        }
    }


    public static void main(String[] args) {
//        sp();
//        checkNegativeCycle();
        arbitrage();
    }

    @SuppressWarnings("DuplicatedCode")
    public static void sp() {
        WeightedDigraph g = new WeightedDigraph(AlgsDataIO.openTinyEWDn());
        System.out.println("输入起点：");
        // 0
        int s = StdIn.readInt();
        BellmanFordSP sp = new BellmanFordSP(g, s);

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

    public static void checkNegativeCycle() {
        WeightedDigraph g = new WeightedDigraph(AlgsDataIO.openTinyEWDnc());
        System.out.print("输入起点：");
        // 0
        int s = StdIn.readInt();
        BellmanFordSP sp = new BellmanFordSP(g, s);

        System.out.println("负权重环：");
        for (DirectedEdge e : sp.negativeCycle()) {
            System.out.printf("%d->%d: %.2f  ", e.from(), e.to(), e.weight());
        }
        System.out.println();
    }

    static void arbitrage() {
        In in = AlgsDataIO.openRates();
        int V = in.readInt();
        String[] name = new String[V];
        WeightedDigraph g = new WeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            name[v] = in.readString();
            for (int w = 0; w < V; w++) {
                double rate = in.readDouble();
                g.addEdge(new DirectedEdge(v, w, -Math.log(rate)));
            }
        }

        BellmanFordSP sp = new BellmanFordSP(g, 0);
        if (sp.hasNegativeCycle()) {
            double stake = 1000.;
            for (DirectedEdge e : sp.negativeCycle()) {
                System.out.printf("%10.5f %s", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                System.out.printf(" = %10.5f %s\n", stake, name[e.to()]);
            }
        } else {
            System.out.println("No arbitrage opportunity");
        }
    }


    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        MyStack<DirectedEdge> stack = new MyStack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            stack.push(e);

        return stack;
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }


    private void relax(WeightedDigraph g, int v) {
        for (DirectedEdge edge : g.adj(v)) {
            int w = edge.to();
            if (distTo[w] > distTo[v] + edge.weight()) {
                distTo[w] = distTo[v] + edge.weight();
                edgeTo[w] = edge;
                if (!onQ[w]) {
                    queue.offer(w);
                    onQ[w] = true;
                }
            }
            // 在 V 轮之后检测是否存在负权重环
            if (cost++ % g.vertexNum() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle())
                    return;
            }
        }
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        WeightedDigraph spt = new WeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);
        }
        WeightedDigraphCycle cf = new WeightedDigraphCycle(spt);
        if (cf.hasCycle())
            cycle = cf.cycle();
    }
}
