package algs4_graph.sec1_undirected_graph;

import algs4_graph.sec1_undirected_graph.src.BreadthFirstPath;
import algs4_graph.sec1_undirected_graph.src.Search;
import algs4_graph.sec1_undirected_graph.src.UndirectedGraph;
import util.algs.StdIn;
import util.io.AlgsDataIO;

/**
 * <p>
 * 为 {@link BreadthFirstPath} 实现一个方法 distTo()，在常数时间内返回给定顶点
 * 的最短路径长度
 * </p>
 */
public class E13_DistTo {

    public static void main(String[] args) {
        UndirectedGraph g = new UndirectedGraph(AlgsDataIO.openTinyCG());
        System.out.print("输入源结点：");
        int s = StdIn.readInt();
        BreadthFirstPath search = new BreadthFirstPath(g, s);
        Search.printAllPath(search);

        System.out.println("\n请输入顶点：");
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            System.out.printf("%d->%d 的距离是 %d\n", s, v, search.distTo(v));
        }
    }
}
