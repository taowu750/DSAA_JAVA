package algs4_graph.sec1_undirected_graph.src;

import util.algs.StdIn;
import util.io.AlgsDataIO;

/**
 * 间隔的度数。描述两个顶点之间最小的顶点数量。
 */
public class DegreesOfSeparation {

    public static void main(String[] args) {
        String filename = AlgsDataIO.fileMovies();
        String delimiter = "/";
        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        SimpleGraph g = sg.g();

        System.out.print("请输入源顶点：");
        String source = StdIn.readLine();
        if (!sg.contains(source)) {
            System.out.println(source + " not in database.");
            return;
        }
        System.out.println();

        int s = sg.index(source);
        BreadthFirstPath bfs = new BreadthFirstPath(g, s);
        System.out.println("请输入标签：");
        while (StdIn.hasNextLine()) {
            String sink = StdIn.readLine();
            if (sg.contains(sink)) {
                int t = sg.index(sink);
                if (bfs.hasPathTo(t)) {
                    for (Integer v : bfs.pathTo(t)) {
                        System.out.println("\t" + sg.name(v));
                    }
                } else
                    System.out.println("Not connected");
            } else
                System.out.println("Not in database");
        }
    }
}
