package algs4_graph.sec4_shortest_path.src;

import util.algs.In;
import util.io.AlgsDataIO;

/**
 * <p>
 * 优先级限制下的并行任务调度问题的关键路径方法。
 * </p>
 * <p>
 * 首先我们创建一幅无环加权有向图，其中包含一个起点s和一个终点t，且每个
 * 任务都对应着两个顶点（一个起始顶点和一个结束顶点）。对于每个任务都有
 * 一条从它的起始顶点指向结束顶点的，边的权重为任务所需的时间。对于每条
 * 优先级限制 v->w，添加一条从 v 的结束顶点指向 w 的起始顶点的权重为 0
 * 的边以及一条从该任务的结束顶点到终点的权重为 0 的边。这样，每个任务
 * 预计开始时间即是从起点到它的起始顶点的最长距离。
 * </p>
 */
public class CPM {

    public static void main(String[] args) {
        /*
        jobsPC.txt 中第一行是任务数，以后每一行第一个值是时耗，
        后面是必须在哪些任务之前完成
         */
        In in = AlgsDataIO.openJobsPC();
        int n = in.readInt();
        in.readLine();
        WeightedDigraph g = new WeightedDigraph(2 * n + 2);
        int s = 2 * n, t = 2 * n + 1;

        for (int i = 0; i < n; i++) {
            String[] a = in.readLine().split("\\s+");
            double duration = Double.parseDouble(a[0]);
            g.addEdge(new DirectedEdge(i, i + n, duration));
            g.addEdge(new DirectedEdge(s, i, 0.));
            g.addEdge(new DirectedEdge(i + n, t, 0.));

            for (int j = 1; j < a.length; j++) {
                int successor = Integer.parseInt(a[j]);
                g.addEdge(new DirectedEdge(i + n, successor, 0.));
            }
        }

        AcyclicLP lp = new AcyclicLP(g, s);
        System.out.println("Start times: ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%4d: %5.1f\n", i, lp.distTo(i));
        }
        System.out.printf("Finish Time: %5.1f\n", lp.distTo(t));
    }
}
