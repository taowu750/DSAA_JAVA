package algs4_graph.sec4_shortest_path.src;

import util.algs.In;
import util.io.AlgsDataIO;

/**
 * <p>
 * 优先级限制下的并行任务调度问题的关键路径方法。
 * </p>
 * <p>
 * 每一个任务在图中用一个结点表示，我们为每一个任务添加一个结束顶点，并让任务指向结束顶点的边
 * 的权重为任务耗时。然后让结束顶点指向这个任务的所有后继任务，并让指向边的权重为 0。
 * 添加一个起点 s，让它指向所有的顶点，并且边的权重为 0；添加一个终点，让所有顶点的结束顶点指向它，
 * 并且边的权重为 0。
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
